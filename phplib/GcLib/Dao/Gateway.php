<?php
class GcLib_Dao_Gateway {
	protected $_config;
	
    public function __construct($config) {
		$this->_config = $config;
	}
}