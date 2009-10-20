<?php
Class GcLib_Db_OraException extends Exception {
	/*
	 * Override the default Exception constructor
	 * 
	 * @param $ocierror oci_error type error
	 */
	public function __construct($ocierror) {
		$code = $ocierror['code'];
		$message = $ocierror['message'];
		parent::__construct($message, $code);
	}
}
?>