<?php
abstract class GcLib_Dao_Gateway {
	protected $_options;
	
    public function __construct($options = null) {
		$this->_options = $options;
	}
	
	public function setOptions($options) {
		$this->_options = $options;
	}
	
   public function getOptions() {
		return $this->_options;
	}
}