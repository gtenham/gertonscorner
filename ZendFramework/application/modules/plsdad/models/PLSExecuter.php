<?php
class Plsdad_Model_PLSExecuter extends Plsdad_Model_OracleDB {
	
	private $packagename;
	private $response;
	
	/**
    * Setter for database packagename
    *
    */
	public function setPackage($package) {
		$this->packagename = $package;
	}
	/**
    * Return the response string returned by the database package
    *
    * @return string
    */
	public function getResponse() {
		return $this->response;
	}
	/**
    * Run the package on the Oracle server
    *
    * @return string
    */
	public function run() {
		try {
		   $conn = $this->getOCIConnection();
		   $parse = oci_parse($conn, "begin $this->packagename.execute(:RESPONSE); end;");
		   $lob = oci_new_descriptor($conn, OCI_D_LOB);
		   // Bind variables
		   oci_bind_by_name($parse, ':RESPONSE', $lob, -1, OCI_B_CLOB);
		   $result = oci_execute($parse);
		   // Read the lob-data
		   if (is_object($lob)) { // protect against a NULL LOB
              $this->response = $lob->load();
              $lob->free();
           }
		   if (!$result) {
           	  $error = oci_error($parse);
           	  // Throw a OraException with oci_error
           	  throw new GcLib_Db_OraException($error);
           }
		} catch (GcLib_Db_OraException $e) {
			echo $e->getCode().' - '. $e->getMessage();
		} catch (Exception $e) {
			echo $e->getCode().' - '. $e->getMessage();
		}
	}
}
?>