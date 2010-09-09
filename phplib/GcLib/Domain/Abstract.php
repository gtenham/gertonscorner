<?php
/**
 * GcLib_Domain_Abstract - Gertons corner library (GcLib) for php 
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
 * @version    $Id$
 */
abstract class GcLib_Domain_Abstract {
	protected $_data = array();
	protected $_dirtyAttributes = array();
	protected $_errors = array();
	
    public function __construct(array $data = array()){
    	$this->_errors = array();
       	$this->populate($data);
    }
	
    public function setData(array $data) {
    	$this->_errors = array();
    	$this->populate($data);
    }
    
    public function __set($attribute, $value){
       if (array_key_exists($attribute, $this->_data)) {
          if ($value !== $this->_data[$attribute]) {
          	 $this->_dirtyAttributes[$attribute] = true;
       	  } else {
       	  	 unset($this->_dirtyAttributes[$attribute]);
       	  }
          $this->_data[$attribute] = $value;
          //$this->validate($attribute,$value);
       }
    }
	
    public function &__get($attribute){
       if (array_key_exists($attribute, $this->_data)) {
          return $this->_data[$attribute];
       } else {
          throw new Exception("Invalid attribute – $attribute");
       }
    }
  
    public function __isset($attribute){
       if (array_key_exists($attribute, $this->_data)) {
          return isset($this->_data[$attribute]);
       } else {
         return false;
       }
    }

    public function __unset($attribute){
       if (array_key_exists($attribute, $this->_data)) {
          unset($this->_data[$attribute]);
       } else {
          return null;
       }
    }  
    
	public function toArray(){
       return $this->_data;
    }

    public function isValid($attribute=null) {
    	if ($attribute != null) {
	    	foreach ($this->_errors as $key => $val) {
	    		if ($val->getAttribute() === $attribute) {
	    			return false;
	    		}
	       	}
    	}
    	return (count($this->_errors) == 0);
    }
    
    public function setError($error) {
    	$this->_errors[] = $error;
    }
    
    public function getErrors($attribute=null) {
    	if ($attribute != null) {
    		$errors = array();
	    	foreach ($this->_errors as $key => $val) {
	    		if ($val->getAttribute() === $attribute) {
	    			$errors[] = $val;
	    		}
	       	}
	       	return $errors;
    	}
       	return $this->_errors;
    }
    
    public function getErrorMessage($attribute=null) {
    	if ($attribute != null) {
    		foreach ($this->_errors as $key => $val) {
	    		if ($val->getAttribute() === $attribute) {
	    			return $val->getErrorMessage();
	    		}
	       	}
    	}
    	return $this->_errors[0]->getErrorMessage();
    }
    
    public function getAllErrorMessages($attribute=null) {
    	$errors = array();
    	foreach ($this->_errors as $key => $val) {
	    	if ($attribute != null && $val->getAttribute() === $attribute) {
	    		$errors[] = $val->getErrorMessage();
	    	} else if ($attribute == null) {
	    		$errors[] = $val->getErrorMessage();
	    	}
	    }
    	return $errors;
    }
    public function getDirtyAttributes() {
    	return $this->_dirtyAttributes;
    }
    
    protected function _convert($data){
       if (is_array($data)) {
          return $data;
       } else if (is_object($data)) {
          return (array) $data;           
       } else {
          throw new Exception("Data must be an datasource, array or object");
       }
    }
    
    protected function populate($data){
       if (is_object($data)) {
          $data = $data->toArray();
       }
       if (!is_array($data)) {
          throw new Exception('Initial data must be an array or object');
       }
       foreach ($data as $key => $val) {
          $this->__set($key, $val);
       }
       return $this;
    }
    
    abstract function validate();
}