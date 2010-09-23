<?php
class Model_Dao_AddressDAO {
	protected $_daogateway;
		                        
	public function __construct($daogateway) {
		$this->_daogateway = $daogateway;
	}
	
	public function setAddress(Model_Domain_Address $address) {
    	$ordersession = $this->_daogateway->getSession();
    	$address->id = $address->id = Zend_Session::getId();
    	$ordersession->address = $address;
    	return $address;
	}
	
    public function removeAddress() {
        $ordersession = $this->_daogateway->getSession();
    	$ordersession->address = null;
	}
	
    public function getAddress() {
    	$ordersession = $this->_daogateway->getSession();
    	if (!isset($ordersession->address)) {
    	   return new Model_Domain_Address();
    	} 
    	return $ordersession->address;
	}
}