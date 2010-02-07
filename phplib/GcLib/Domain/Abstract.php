<?php
abstract class GcLib_Domain_Abstract {
	protected $_data = array();
	
    public function __construct(array $Data = array()){
       $this->populate($Data);
    }
	
    public function __set($Attribute, $Value){
       if (array_key_exists($Attribute, $this->_data)) {
          $this->_data[$Attribute] = $Value;
       }
    }
	
    public function &__get($Attribute){
       if (array_key_exists($Attribute, $this->_data)) {
          return $this->_data[$Attribute];
       } else {
          throw new Exception("Invalid attribute – $Attribute");
       }
    }
  
    public function __isset($Attribute){
       if (array_key_exists($Attribute, $this->_data)) {
          return isset($this->_data[$Attribute]);
       } else {
         return false;
       }
    }

    public function __unset($Attribute){
       if (array_key_exists($Attribute, $this->_data)) {
          unset($this->_data[$Attribute]);
       } else {
          return null;
       }
    }  
    
	public function toArray(){
       return $this->_data;
    }

    protected function _convert($Data){
       if (is_array($Data)) {
          return $Data;
       } else if (is_object($Data)) {
          return (array) $Data;           
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