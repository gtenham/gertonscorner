<?php
namespace services\exceptions;

class NotFoundException extends \Exception {
	
	const statusCode = 404;
	private $responseData;
	
	public function __construct($message = null) {
        // make sure everything is assigned properly
        parent::__construct($message, self::statusCode, null);
    }
    
    public function __toString() {
    	return self::statusCode;
    }
}