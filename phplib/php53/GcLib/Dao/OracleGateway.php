<?php
namespace GcLib\Dao;

class OracleGateway extends Gateway {
	protected $_conn;
	
    public function __construct($options) {
		$this->_options = $options;
        $this->_conn = $this->getConnection();
	}
	
	public function getConnection() {
	   if (!$this->_conn) {
	   	   if (stripos($this->_options["database"],":pooled")) {
	   	       $this->_conn = oci_pconnect( $this->_options["username"]
			                             , $this->_options["password"]
			                             , $this->_options["database"]
			                             );
	   	   } else {
		   	   $this->_conn = oci_connect( $this->_options["username"]
			                             , $this->_options["password"]
			                             , $this->_options["database"]
			                             );
	   	   }
	   	}
		return $this->_conn;
	}
	
	public function __destruct() {
		if ($this->_conn) {
		   oci_close($this->_conn);
		}
	}
}