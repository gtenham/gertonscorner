<?php
class Model_Domain_File extends GcLib_Domain_Abstract {
	protected $_data = array( 'filename' => null
	                        , 'filedata' => null
	                        );
    
	public function validate() {
		foreach ($this->getDirtyAttributes() as $attribute => $isDirty) {
			$method = 'validate'.ucfirst($attribute);
			if ($isDirty && method_exists(get_class($this),$method)) {
				$this->$method();
			}
		}
		
	}
}