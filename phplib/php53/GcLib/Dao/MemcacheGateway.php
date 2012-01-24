<?php
namespace GcLib\Dao;

class MemcacheGateway extends Gateway {
	protected $_memcache;
	
    public function __construct($options) {
		$this->_options = $options;
		$this->_memcache = $this->getConnection();
	}
	
	public function getConnection() {
	   if (!$this->_memcache) {
	   	   $this->_memcache = new \Memcache; // instantiating memcache extension class
           $this->_memcache->connect($this->_options["host"],$this->_options["port"]);
	   	}
		return $this->_memcache;
	}
	
	public function __destruct() {
		if ($this->_memcache) {
		   $this->_memcache->close();
		}
	}
}