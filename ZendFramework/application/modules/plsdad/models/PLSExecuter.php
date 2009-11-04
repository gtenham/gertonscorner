<?php
class Plsdad_Model_PLSExecuter extends Plsdad_Model_OracleDB {
	
	private $packagename;
	private $response;
	private $reqHeaders;
	private $respHeaders;
	
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
		   $parse = oci_parse($conn, "begin $this->packagename.execute(:header, :body); end;");
		   $headlob = oci_new_descriptor($conn, OCI_D_LOB);
	       $bodylob = oci_new_descriptor($conn, OCI_D_LOB);
		   // Bind variables
		   oci_bind_by_name($parse, ':header', $headlob, -1, OCI_B_CLOB);
		   oci_bind_by_name($parse, ':body', $bodylob, -1, OCI_B_CLOB);
		   // Encode reguest header array to json encoded string
		   // and write to temporary head clob
		   $headlob->writeTemporary(json_encode(array_map(utf8_encode,$this->reqHeaders)), OCI_TEMP_CLOB);
		   // Execute statement in no-auto-commit mode
		   $result = oci_execute($parse, OCI_DEFAULT);
		   // Read the body lob-data
		   if (is_object($bodylob)) { // protect against a NULL LOB
              $this->response = $bodylob->load();
              $bodylob->free();
           }
           // Read the head lob-data containing json data and
           // decode back to array.
           if (is_object($headlob)) { // protect against a NULL LOB
              $this->respHeaders = json_decode($headlob->load(), true);
              $headlob->free();
           }
           // Free temporary head clob
	       $headlob->close();
	       //Raise exception when database execution error has occurred
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