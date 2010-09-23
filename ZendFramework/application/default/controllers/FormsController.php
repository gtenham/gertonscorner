<?php
class FormsController extends Zend_Controller_Action 
{
    public function init() {
        /* Initialize action controller here */
    	$xmlconfig = &GcLib_Xml_XMLResource::load(APPLICATION_PATH .'/configs/dao-config.xml');
    	$daocontainer = new GcLib_Dao_Container($xmlconfig);
        $this->addressdao = $daocontainer->getManager('usersession')->getDao('address');
    }
    
    public function indexAction() {
    	$this->view->title = "Forms and Validation";
    	$request = $this->getRequest();
        $method = $request->getMethod();
        // Obtain user parameters
        $params = $request->getParams();
        
        switch ($method) {
           case 'POST':
           	 $myaddress = $this->addressdao->getAddress();
           	 $myaddress->setData($params);
           	 $myaddress->validate();
           	 
           	 $this->addressdao->setAddress($myaddress);
             break;
           default:
           	 $myaddress = $this->addressdao->getAddress();
        }
        
        $this->view->address = $myaddress;
    }
}
