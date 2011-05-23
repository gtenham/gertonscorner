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
		
		$this->removeErrors("zipcode");
		$localValid = false;
		
		// Check on empty value
		$validator = new Zend_Validate_NotEmpty();
		$localValid = $validator->isValid($this->zipcode);
		if (!$validator->isValid($this->zipcode)) {
			$error = new GcLib_Domain_Exception("zipcode","Zipcode is required and can not be empty.",-10000);
		    $this->setError($error);
		} 
		
		//Dutch zipcode pattern: ^[0-9]{4}\s{0,1}[a-zA-Z]{2}$
		$validator = new Zend_Validate_Regex(array('pattern' => '/^[0-9]{4}\s{0,1}[a-zA-Z]{2}$/'));
		$localValid = $validator->isValid($this->zipcode);
		if (!$validator->isValid($this->zipcode)) {
			$error = new GcLib_Domain_Exception("zipcode","Invalid zipcode format.",-10001);
		    $this->setError($error);
		} 
		
		if ($localValid) {
			$this->setValid("zipcode");
		}
	}
}