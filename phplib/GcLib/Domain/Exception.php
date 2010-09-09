<?php
/**
 * GcLib_Domain_Exception - Gertons corner library (GcLib) for php 
 *
 * Copyright (c) 2010 Gerton ten Ham
 * Examples and documentation at: http://gertonscorner.wordpress.com
 *
 * Dual licensed under the MIT and GPL licenses:
 *   http://www.opensource.org/licenses/mit-license.php
 *   http://www.gnu.org/licenses/gpl.html
 *
 * @category   GcLib
 * @package    GcLib_Domain
 * @copyright  Copyright (c) 2010 Gerton ten Ham
 * @version    $Id:$
 */
Class GcLib_Domain_Exception extends Exception {
	protected $_attribute;
	protected $_code;
	protected $_message;
	protected $_prefix;
	
	/*
	 * Override the default Exception constructor
	 * 
	 */
	public function __construct($attribute, $message, $code=null, $prefix=null) {
		$this->_attribute = $attribute;
		$this->_code = $code;
		$this->_message = $message;
		$this->_prefix = $prefix;
		parent::__construct($message, $code);
	}
	
	public function getAttribute() {
		return $this->_attribute;
	}
	
	public function getErrorMessage() {
		return $this->_message;
	}
	
	public function getErrorCode() {
		return $this->_prefix . $this->_code;
	}
}