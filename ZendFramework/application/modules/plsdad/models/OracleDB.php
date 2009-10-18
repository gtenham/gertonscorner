<?php
abstract Class Plsdad_Model_OracleDB {
	/**
    * @var Zend_Db $db
    */
    protected $db = null;
    
    function __construct() {
       
       $this->db = Zend_Registry::get('OracleAdapter');;
       $this->db->query("alter session set NLS_NUMERIC_CHARACTERS = ',.'");
       $this->db->query("alter session set NLS_DATE_FORMAT = 'dd-mm-yyyy hh24:mi:ss'");
    }

    function getOCIConnection() {
    	return $this->db->getConnection();
    }
}
?>