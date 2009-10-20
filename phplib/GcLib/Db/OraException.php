<?php
Class GcLib_Db_OraException extends Exception {
	public function __construct($ocierror) {
		$code = $ocierror['code'];
		$message = $ocierror['message'];
		parent::__construct($message, $code);
	}
}
?>