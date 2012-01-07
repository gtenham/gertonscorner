<?php
namespace GcLib\Dao;

class ZendSessionGateway extends Gateway {
	
	protected $_ns;
	
    public function __construct($options) {
		$this->_options = $options;
        $this->_ns = $this->getSession();
	}
	
    public function getSession() {
	   if (!$this->_ns) {
	   	   $this->_ns = new Zend_Session_Namespace($this->_options["namespacename"]);
	   	}
		return $this->_ns;
	}
}