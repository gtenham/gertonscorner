<?php

class Admin_IndexController extends Zend_Controller_Action
{

    public function init()
    {
        /* Initialize action controller here */
    	$this->_helper->layout()->setLayout('admin_layout');
    }

    public function indexAction()
    {
        // action body
        $this->view->title = 'Admin';
        $this->view->headTitle($this->view->title, 'PREPEND');
    }

}

