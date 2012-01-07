<?php
namespace GcLib\Dao;

class MongoDbGateway  extends Gateway {
	protected $_conn;
	protected $_servername;
	protected $_connectoptions;
	
    public function __construct($options) {
		$this->_options = $options;
		$this->_connectoptions = $options;
		unset($this->_connectoptions["servername"]);
		$this->_conn = $this->getConnection();
	}
	
	public function getConnection() {
	   if (!$this->_conn) {
	   	   $this->_servername = $this->_options["servername"];
	   	   $this->_conn = new Mongo($this->_servername,$this->_connectoptions);
	   	}
		return $this->_conn;
	}
}