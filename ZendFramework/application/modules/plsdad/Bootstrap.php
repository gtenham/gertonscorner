<?php
class Plsdad_Bootstrap extends Zend_Application_Module_Bootstrap {
	/**
     * Bootstrap DB adapter
     * 
     * @return void
     */
    protected function _initDb() {
    	$resource = $this->bootstrap('frontcontroller')->getPluginResource('db');
    	$adapter = $resource->getDbAdapter();
    	Zend_Registry::set('OracleAdapter', $adapter);
    }
    /**
     * Bootstrap SystemParams adapter
     * 
     * @return void
     */
    protected function _initSystemParams() {
       $resource = $this->bootstrap('frontcontroller')->getPluginResource('systemparams');
       $requestheaderparams = $resource->getRequestHeaders();
       Zend_Registry::set('SystemReqHeader', $requestheaderparams);
    }
}