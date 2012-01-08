<?php
namespace models\domain;

use GcLib\Domain\AbstractModel as AbstractModel;

/**
 * Todo domain entity
 *  
 * @author Gerton
 *
 */
class Todo extends AbstractModel {
	
	protected $_data = array( 'id' => null
	                        , 'done' => null
	                        , 'order' => null
	                        , 'description' => null
	                        , 'startDate' => null
	                        );
	                        
	/**
	 * Validate Todo object
	 */
	public function validate() {
		foreach ($this->getDirtyAttributes() as $attribute => $isDirty) {
			$method = 'validate'.ucfirst($attribute);
			if ($isDirty && method_exists(get_class($this),$method)) {
				$this->$method();
			}
		}
		
	}
}