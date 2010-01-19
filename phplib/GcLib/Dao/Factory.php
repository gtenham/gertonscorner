<?php
class GcLib_Dao_Factory {
	protected $_daos = array();
	protected $_gateway;
	
    public function __construct() {
		
	}
	
	public function setDaos($daos) {
		$this->_daos = $daos;
	}
	
    public function setGateway($gateway) {
		$this->_gateway = $gateway;
	}
	
	public function getDao($id) {
	   if (array_key_exists($id, $this->_daos)) {
          $dao = new $this->_daos[$id]($this->_gateway);
          return $dao;
       } else {
          throw new Exception("Invalid dao – $id");
       }
	}
	
	public function getDaos() {
		return $this->_daos;
	}
	
    public function getGateway() {
		return $this->_gateway;
	}
}