<?php
/**
 * Plsdad index controller.
 * 
 * @uses Zend_Controller_Action
 *
 */
class Plsdad_IndexController extends Zend_Controller_Action
{
    private $content;
    private $request;
    
    public function init()
    {
        /* Initialize action controller here */
    	$this->_helper->viewRenderer->setNoRender(true);
    	$this->_helper->layout->disableLayout();
    }

    public function indexAction()
    {
        // action body
        $request = $this->getRequest();
        $method = $request->getMethod();
        
        switch ($method) {
           case 'POST':
             $this->_forward('post');
             break;
           case 'PUT':
             $this->_forward('put');
             break;
           case 'DELETE':
             $this->_forward('delete');
             break;
           case 'HEAD':
             $this->_forward('head');
             break;
           default:
             $this->_forward('get');
        }
    }

    public function getAction() {
    	Zend_Registry::get('Zend_Log')->info("module plsdad log");
    	$content = 'Plsdad is called for package: ' . $this->_getParam('packagename');
        
        $this->getResponse()
             ->setHeader('Content-Type', 'text/plain')
             ->setHeader('X-Custom-Head', 'HelloWorld')
             ->appendBody($content);
    }
    
    public function postAction() {
    	
    }
    
    public function putAction() {
    	
    }
    
    public function deleteAction() {
    	
    }
    
    public function headAction() {
    	
    }
}

