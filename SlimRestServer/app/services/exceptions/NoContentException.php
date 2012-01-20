<?php
namespace services\exceptions;

class NoContentException extends \Exception {
	
	const statusCode = 204;
	private $responseData;
	
	public function __construct($message = null) {
        // make sure everything is assigned properly
        parent::__construct($message, self::statusCode, null);
    }
    
    public function __toString() {
    	return self::statusCode;
    }
}