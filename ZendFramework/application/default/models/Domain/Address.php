<?php
class Model_Domain_Address extends GcLib_Domain_Abstract {
	protected $_data = array( 'id' => null
	                        , 'countrycode' => null
	                        , 'city' => null
	                        , 'street' => null
	                        , 'housenumber' => null
	                        , 'zipcode' => null
	                        , 'county' => null
	                        );

	public function validate() {
		foreach ($this->getDirtyAttributes() as $attribute => $isDirty) {
			$method = 'validate'.ucfirst($attribute);
			if ($isDirty && method_exists(get_class($this),$method)) {
				$this->$method();
			}
		}
		
	}
	
	protected function validateZipcode() {
		//Dutch zipcode pattern: ^[0-9]{4}\s{0,1}[a-zA-Z]{2}$
		$validator = new Zend_Validate_Regex(array('pattern' => '/^[0-9]{4}\s{0,1}[a-zA-Z]{2}$/'));
		if ($validator->isValid($this->zipcode)) {
			$this->setValid("zipcode");
		} else {
			$error = new GcLib_Domain_Exception("zipcode","Invalid zipcode format.",-10000);
		    $this->setError($error);
		}
	}
}