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
}