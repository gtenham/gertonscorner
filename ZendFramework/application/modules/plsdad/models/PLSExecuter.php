<?php
class Plsdad_Model_PLSExecuter extends Plsdad_Model_OracleDB {
	
	/**
    * Run the package on the Oracle server
    *
    * @return string
    */
	function run($packagename) {
		try {
		   $conn = $this->getOCIConnection();
		   $parse = oci_parse($conn, "begin :retval := 'today is: '||to_char(sysdate); end;");
		   oci_bind_by_name($parse, ':retval', $r, 20);
           $result = oci_execute($parse);
		   if (!$result) {
           	  $error = oci_error($parse);
           	  // Throw a OraException with oci_error
           	  throw new GcLib_Db_OraException($error);
           }
		} catch (GcLib_Db_OraException $e) {
			echo $e->getCode().' - '. $e->getMessage();
		}
	    catch (Exception $e) {
			echo $e->getCode().' - '. $e->getMessage();
		}
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