<?php
/**
 * Custom Resource plugin class for logging
 * 
 * @uses       Zend_Application_Resource_ResourceAbstract
 * @package    Custom_Resource
 * @version    $Id$
 */
class Custom_Resource_Logger extends Zend_Application_Resource_ResourceAbstract {
   
   protected $_log;
	
   public function init() {
      return $this->getLog();
   }
 
   public function getLog() {
      if (null === $this->_log) {
         $options = $this->getOptions();
         $output = strtolower($options['output']);
         $registry = $options['registry'];
         // Change log output
         if ( $output === "firebug") {
            $writer = new Zend_Log_Writer_Firebug();
         } else {
         	$file = str_replace('%d', date('Y-m-d'), $options['file']);
            $writer = new Zend_Log_Writer_Stream($file, 'a+');
         }
           
         $log = new Zend_Log($writer);
         
         // Only log messages equal or below given priority
         $priority = (int)$options['priority'];
         $filter = new Zend_Log_Filter_Priority($priority);
         $log->addFilter($filter);
         
         // Add log instance to the global registry
         // fetch the logger using Zend_Registry::get('[registry-option]')
         // within your controllers, models etc          
         Zend_Registry::set($registry, $log);
         $this->_log = $log;
      }
      return $this->_log;
   }
}