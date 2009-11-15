<?php
/**
 * Plsdad Resource plugin class for system parameters
 * 
 * @uses       Zend_Application_Resource_ResourceAbstract
 * @package    Plsdad_Resource
 * @version    $Id$
 */
class Plsdad_Resource_Systemparams extends Zend_Application_Resource_ResourceAbstract {

	private $options;
	
	public function init() {
	}
	/**
	 * Get request headers parameter values
	 * 
	 * @return array
	 */
	public function getRequestHeaders() {
		$this->options = $this->getOptions();
		return $this->options['reqheader'];
	}
	
}
?>