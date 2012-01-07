<?php
namespace GcLib\Dao;

class FileGateway extends Gateway {
	
	protected $_path;
	
	public function __construct($options) {
		$this->_options = $options;
        $this->_location = $this->getPath();
	}
	
	public function getPath() {
	   if (!$this->_path) {
	   	   $this->_path = $this->_options["path"];
	   	}
		return $this->_path;
	}
	
	public function setPath($path) {
		$this->_path = $path;
	}
}