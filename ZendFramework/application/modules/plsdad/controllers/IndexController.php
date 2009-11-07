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
    	$db = Zend_Registry::get('OracleAdapter');
    	$this->pls = new GcLib_Db_PLSExecuter( $db->getConnection() );
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
        // Obtain user parameters
        $params = $request->getParams();
        $params['Cache-Control'] = (String)$request->getHeader('Cache-Control');
        $params['Accept-Charset'] = $request->getHeader('Accept-Charset');
        $params['Method'] = $method;
        
        $this->pls->setPackage($this->_getParam('packagename'));
        $this->pls->setRequestHeaders(array_filter($params));
        $this->pls->run();
        
        switch ($method) {
           case 'HEAD':
             foreach ($this->pls->getResponseHeaders() as $key => $value) {
             	$this->getResponse()->setHeader($key,$value);
             }
             break;
           default:
             $content = $this->pls->getResponse();
             foreach ($this->pls->getResponseHeaders() as $key => $value) {
             	$this->getResponse()->setHeader($key,$value);
             }
             $this->getResponse()->appendBody($content);
        }
    }
}

