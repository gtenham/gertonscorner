<?php
class GcLib_Dao_OracleGateway extends GcLib_Dao_Gateway {
	protected $_conn;
	
    public function __construct($options) {
		$this->_options = $options;
        $this->_conn = $this->getConnection();
	}
	
	public function getConnection() {
	   if (!$this->_conn) {
	   	   $this->_conn = oci_connect( $this->_options["username"]
		                             , $this->_options["password"]
		                             , $this->_options["database"]
		                             );
	   	}
		return $this->_conn;
	}
	
	public function __destruct() {
		if ($this->_conn) {
		   oci_close($this->_conn);
		}
	}
}