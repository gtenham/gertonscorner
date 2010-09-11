<?php
class Model_Dao_UserDAO {
	protected $_daogateway;
		                        
	public function __construct($daogateway) {
		$this->_daogateway = $daogateway;
	}
	
	public function removeCurrentUser() {
        $usersession = $this->_daogateway->getSession();
    	$usersession->currentuser = null;
	}
	
	public function setCurrentUser(Model_Domain_User $user) {
    	$usersession = $this->_daogateway->getSession();
    	$user->userid = Zend_Session::getId();
    	$usersession->currentuser = $user;
    	return $user;
	}
	
    public function getCurrentUser() {
    	$usersession = $this->_daogateway->getSession();
    	if (!isset($usersession->currentuser)) {
    	   return new Model_Domain_User();
    	} 
    	return $usersession->currentuser;
	}
}