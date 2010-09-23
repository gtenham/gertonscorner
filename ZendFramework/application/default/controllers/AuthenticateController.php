<?php
class AuthenticateController extends Zend_Controller_Action 
{
    public function init()
    {
    	
    	/* Initialize action controller here */
    	$this->_helper->viewRenderer->setNoRender(true);
    	$this->_helper->layout->disableLayout();
    	
    	$xmlconfig = &GcLib_Xml_XMLResource::load(APPLICATION_PATH .'/configs/dao-config.xml');
    	$daocontainer = new GcLib_Dao_Container($xmlconfig);
        $this->userdao = $daocontainer->getManager('usersession')->getDao('currentUser');
    	$this->r = Zend_Controller_Action_HelperBroker::getStaticHelper('redirector');
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
    	
    }
    
    public function loginAction() {
    	$request = $this->getRequest();
        // Obtain user parameters
        $params = $request->getParams();
        $redirectParam = $params['redirect'];
        $currentUser = $this->userdao->getCurrentUser();
        $currentUser->setData($params);
        $currentUser->validate();
        
        if ($currentUser->isValid()) {
        	$currentUser->firstname = "John";
        	$currentUser->lastname = "Doe";
        	$currentUser->email = "j.doe@example.com";
        	$this->userdao->setCurrentUser($currentUser);
        	$this->r->gotoUrl($redirectParam)->redirectAndExist();
        }
        
    }
    
	public function logoutAction() {
		$this->userdao->removeCurrentUser();
		$this->r->gotoUrl('/')->redirectAndExist();
    }
    
    public function rememberAction() {
    	$this->userdao->rememberMe();
    	$this->r->gotoUrl($redirectParam)->redirectAndExist();
    }
}