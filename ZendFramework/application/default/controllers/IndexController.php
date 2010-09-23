<?php

/**
 * IndexController is the default controller for this application
 * 
 * Notice that we do not have to require 'Zend/Controller/Action.php', this
 * is because our application is using "autoloading" in the bootstrap.
 *
 * @see http://framework.zend.com/manual/en/zend.loader.html#zend.loader.load.autoload
 */
class IndexController extends Zend_Controller_Action 
{
    public function init()
    {
        /* Initialize action controller here */
    	// Override the default layout "page" with "frontpage"
    	$this->_helper->layout()->setLayout('frontpage');
    }
    /**
     * The "index" action is the default action for all controllers -- the 
     * landing page of the site.
     *
     * Assuming the default route and default router, this action is dispatched 
     * via the following urls:
     *   /
     *   /index/
     *   /index/index
     *
     * @return void
     */
    public function indexAction() {
    	Zend_Registry::get('Zend_Log')->info("Informational log");
        $this->view->title = "Congratulations!";
    }
    
    public function infoAction() 
    {
        $this->_helper->layout()->disableLayout();
    }
}
