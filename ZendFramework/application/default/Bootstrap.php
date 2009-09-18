<?php

/**
 * Application bootstrap
 * 
 * @uses    Zend_Application_Bootstrap_Bootstrap
 * @package QuickStart
 */
class Bootstrap extends Zend_Application_Bootstrap_Bootstrap
{
    /**
     * Bootstrap autoloader for application resources
     * 
     * @return Zend_Application_Module_Autoloader
     */
    protected function _initAutoload()
    {
        $autoloader = new Zend_Application_Module_Autoloader(array(
            'namespace' => 'Default',
            'basePath'  => dirname(__FILE__),
        ));
        return $autoloader;
    }
    /**
     * Bootstrap the Routers
     * http://monzee.wordpress.com/2009/05/21/migrating-applications-to-1-8/
     * @return void
     */
    protected function _initRouters()
    {
    	$front = $this->bootstrap('frontController')->getResource('frontController');
        $router = $front->getRouter();
        
        $route = new Zend_Controller_Router_Route(
                    'plsdispatch/:packagename/*',
                        array(
                          'module'     => 'admin',
                          'controller' => 'index',
                          'action'     => 'index'
                         )
                     );
        
        $router->addRoute('plsdispatcher', $route);
    }
    /**
     * Bootstrap the view helpers
     * 
     * @return void
     */
    protected function _initViewHelpers()
    {
        //$this->bootstrap('view');
        $view = $this->bootstrap('view')->getResource('view');
        $view->doctype('XHTML1_STRICT');
        $view->headMeta()->appendHttpEquiv('Content-Type', 'text/html;charset=utf-8');
        $view->headTitle()->setSeparator(' - ');
        $view->headTitle('Zend Framework Demo');
    }
}
