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
    private $pls;
    
    /*
     * Initialisation
     */
    public function init()
    {
        /* Initialize action controller here */
    	$this->_helper->viewRenderer->setNoRender(true);
    	$this->_helper->layout->disableLayout();
    	// Get a database adapter resource
    	$this->pls = new Plsdad_Model_PLSExecuter();
    }

    /*
     * The plsdad module index action method
     */
    public function indexAction()
    {
        // action body
        Zend_Registry::get('Zend_Mod_Log')->info("module plsdad log");
        
        $request = $this->getRequest();
        $method = $request->getMethod();
        
        $this->pls->setPackage($this->_getParam('packagename'));
        $this->pls->run();
        
        switch ($method) {
           case 'HEAD':
             $this->_forward('head');
             break;
           default:
             $content = 'Plsdad is called for package: ' . $this->_getParam('packagename') . "\n";
             $content .= $this->pls->getResponse();
        
             $this->getResponse()
                ->setHeader('Content-Type', 'text/plain')
                ->setHeader('X-Custom-Head', 'HelloWorld')
                ->appendBody($content);
        }
    }

    public function headAction() {
    	$this->getResponse()
          ->setHeader('Content-Type', 'text/plain')
          ->setHeader('X-Custom-Head', 'Just the header');
    }
}

