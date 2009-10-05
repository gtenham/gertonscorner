<?php
/**
 * Plsdad index controller.
 * 
 * @uses Zend_Controller_Action
 *
 */
class Messagebroker_AmfController extends Zend_Controller_Action
{
    public function init()
    {
        /* Initialize action controller here */
    	$this->_helper->viewRenderer->setNoRender(true);
    	$this->_helper->layout->disableLayout();
    }

    public function indexAction()
    {
        // action body
        $server = new Zend_Amf_Server();
		$server->addDirectory(APPLICATION_PATH . '/modules/messagebroker/services/');
		//$server->setClassMap("FileVO", "FileVO");
		echo($server->handle());
    }
}

