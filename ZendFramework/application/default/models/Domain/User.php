<?php
class Model_Domain_User extends GcLib_Domain_Abstract {
	protected $_data = array( 'userid' => null
	                        , 'password' => null
	                        , 'firstname' => null
	                        , 'lastname' => null
	                        , 'telephone' => null
	                        , 'email' => null
	                        );
    
	public function validate() {
		foreach ($this->getDirtyAttributes() as $attribute => $isDirty) {
			$method = 'validate'.ucfirst($attribute);
			if ($isDirty && method_exists(get_class($this),$method)) {
				$this->$method();
			}
		}
		
	}
	
	public function toString() {
		return $this->firstname." ".$this->lastname;
	}
}