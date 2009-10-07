<?php
class Admin_IndexController extends Zend_Controller_Action
{

    public function init()
    {
        /* Initialize action controller here */
    	$myuser = new Model_User();
    	$myuser->userid = "gertonth";
    	$myuser->username = "Gerton ten Ham";
    	
    	$this->view->loggedInUser = $myuser;
    }

    public function indexAction()
    {
        // action body
        
        $this->view->title = 'Admin module';
        $this->view->headTitle($this->view->title, 'PREPEND');
        
        
    }

}