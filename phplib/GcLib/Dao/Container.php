<?php
class GcLib_Dao_Container {
	protected $_config;
	protected $_daos = array();
	
    public function __construct($config) {
		$this->_config = $config;
	}
	
	public function getManager($id) {
		$this->loadDaos($id);
		$daofactory = new GcLib_Dao_Factory();
		$daofactory->setDaos($this->_daos);
		return $daofactory;
	}
	
	private function loadDaos($id) {
		$xml = new SimpleXMLElement($this->_config);
	    foreach ($xml->xpath('//manager[@id=\''.$id.'\']/daos/dao') as $dao) {
	    	$key = (string) $dao['name'];
	    	$val = (string) $dao['class'];
            $this->_daos[$key] = $val;
        }
	}
}