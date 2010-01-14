<?php
class GcLib_Dao_Factory {
	protected $_daos = array();
	
    public function __construct() {
		
	}
	
	public function setDaos($daos) {
		$this->_daos = $daos;
	}
	
	public function getDao($id, $daogateway) {
	   if (array_key_exists($id, $this->_daos)) {
          $dao = new $this->_daos[$id]($daogateway);
          return $dao;
       } else {
          throw new Exception("Invalid dao – $id");
       }
	}
	
	public function getDaos() {
		return $this->_daos;
	}
}