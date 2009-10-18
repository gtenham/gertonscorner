<?php
class Plsdad_Model_PLSExecuter extends Plsdad_Model_OracleDB {
	
	/**
    * Run the package on the Oracle server
    *
    * @return string
    */
	function run($packagename) {
		$conn = $this->getOCIConnection();
        $stmt = oci_parse($conn, "begin :retval := 'today is: '||to_char(sysdate); end;");
        oci_bind_by_name($stmt, ':retval', $r, 20);
        oci_execute($stmt);
        return $r;
	}
	
    /**
    * return the current sysdate of the oracle server
    *
    * @return string
    */
    function getSysDate() {
       $res = $this->db->fetchRow("SELECT sysdate FROM dual");
       return $res['SYSDATE'];
    }
}
?>