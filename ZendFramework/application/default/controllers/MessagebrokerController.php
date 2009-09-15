<?php
class MessagebrokerController extends Zend_Controller_Action {
 
	public function init()	{
		$this->_helper->viewRenderer->setNoRender(true);
	}
 
	public function amfAction()	{
		$server = new Zend_Amf_Server();
		$server->addDirectory(APPLICATION_PATH . '/default/services/');
		$server->setClassMap("FileVO", "FileVO");
		echo($server->handle());
	}
}