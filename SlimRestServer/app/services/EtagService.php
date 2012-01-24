<?php
namespace services;

use GcLib\Xml\XMLResource as XMLResource;
use GcLib\Dao\Container as Container;

/**
 * Etag service class
 * 
 * @package services
 * @author Gerton
 *
 */
class EtagService {
	
	private $xmlconfig;
	private $daoContainer;
	private $etagDAO;
	
	/**
	 * EtagService default constructor
	 */
	public function __construct() {
		$this->xmlconfig = &XMLResource::load(APPLICATION_PATH.'/configs/dao-config.xml');
    	$this->daoContainer = new Container($this->xmlconfig);
    	$this->etagDAO = $this->daoContainer->getManager('memcache-server')->getDao('etag');
	}
	
	public function get($key) {
		return $this->etagDAO->get($key);
	}
	
	public function change($key) {
		return $this->etagDAO->increment($key);
	}
	
	public function remove($key) {
		return $this->etagDAO->destroy($key);
	}
}