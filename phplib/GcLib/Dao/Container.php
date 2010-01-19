<?php
/**
 * DAO Container configuration class
 * 
 * Use the following xml-structure as config:
 * 
 *    <?xml version='1.0' encoding='UTF-8'?>
 *    <container>
 *       <manager id="oracle">
 *          <gateway class="GcLib_Dao_OracleGateway">
 *             <property name="database">//localhost/XE</property>
 *             <property name="username">scott</property>
 *             <property name="password">tiger</property>
 *          </gateway>
 *          <daos>
 *             <dao name="address" class="Model_Dao_AddressDAO"/>
 *          </daos>
 *       </manager>
 *    </container>
 * 
 * PHP example:
 * $daocontainer = new GcLib_Dao_Container($config);
 * $addressdao = $daocontainer->getManager('oracle')->getDao('address');
 * 
 * $addressdao contains an instance of Model_Dao_AddressDAO
 * 
 * @author gtenham
 *
 */
class GcLib_Dao_Container {
	protected $_config;
	protected $_daos = array();
	protected $_gateway;
	
    public function __construct($config) {
		$this->_config = $config;
	}
	
	public function getManager($id) {
		// Load config options
		$this->loadDaos($id);
		$this->loadGateway($id);
		// Create new Dao Factory
		$daofactory = new GcLib_Dao_Factory();
		$daofactory->setDaos($this->_daos);
		$daofactory->setGateway($this->_gateway);
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
	
	private function loadGateway($id) {
		$xml = new SimpleXMLElement($this->_config);
		$gateway = $xml->xpath('//manager[@id=\''.$id.'\']/gateway');
		$gatewayclass = (string) $gateway[0]['class'];
		$options = array();
		
		foreach ($xml->xpath('//manager[@id=\''.$id.'\']/gateway/property') as $property) {
			$key = (string) $property['name'];
	    	$val = (string) $property;
			$options[$key] = $val;
		}
		$this->_gateway = new $gatewayclass($options);
	}
}