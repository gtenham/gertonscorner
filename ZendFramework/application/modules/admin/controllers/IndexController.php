<?php
class Admin_IndexController extends Zend_Controller_Action
{

    public function init() {
        /* Initialize action controller here */
    	$xmlconfig = &GcLib_Xml_XMLResource::load(APPLICATION_PATH .'/configs/dao-config.xml');
    	$daocontainer = new GcLib_Dao_Container($xmlconfig);
        $this->userdao = $daocontainer->getManager('usersession')->getDao('currentUser');
    	
    }

    public function indexAction()
    {
        // action body
        Zend_Registry::get('Zend_Mod_Log')->info("module Admin log");
        $this->view->title = 'Admin module';
        $this->view->headTitle($this->view->title, 'PREPEND');
        
        $myuser = $this->userdao->getCurrentUser();
        //$myuser->userid = "jdoe";
    	//$myuser->firstname = "John";
    	//$myuser->lastname = "Doe";
    	
        $myuser->validate();
        
        if ($myuser->isValid()) {
        	$this->userdao->setCurrentUser($myuser);
        	$this->view->loggedInUser = $myuser;
        }
    }

}