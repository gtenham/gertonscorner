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
	
	public function validateUserid() {
		if ($this->userid !== null) {
			$this->setValid("userid");
		} else {
			$error = new GcLib_Domain_Exception("userid","Authentication failed.",-10000);
		    $this->setError($error);
		}
	}
	
	public function toString() {
		return $this->firstname." ".$this->lastname;
	}
}