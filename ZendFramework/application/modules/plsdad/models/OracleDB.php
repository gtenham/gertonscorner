<?php
abstract Class Plsdad_Model_OracleDB {
	/**
    * @var Zend_Db $db
    */
    protected $db = null;
    
    function __construct() {
       
       $this->db = Zend_Registry::get('OracleAdapter');;
    }

    /*
     * Returning the OCI database connection
     * 
     * @return oci-connection
     */
    public function getOCIConnection() {
    	return $this->db->getConnection();
    }
}
?>