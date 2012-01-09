<?php
namespace services;

use GcLib\Xml\XMLResource as XMLResource;
use GcLib\Dao\Container as Container;

class TodoService {
	
	private $xmlconfig;
	private $daoContainer;
	private $todoDAO;
	
	public function __construct() {
		$this->xmlconfig = &XMLResource::load(APPLICATION_PATH.'/configs/dao-config.xml');
    	$this->daoContainer = new Container($this->xmlconfig);
    	$this->todoDAO = $this->daoContainer->getManager('slim-rest-server')->getDao('todo');
	}
	
	public function getTodoById($id) {
		$todo = $this->todoDAO->findTodoById($id);
		$errors['errors'] = $todo->getErrors();
		return array_merge($todo->toArray(),$errors);
	}
}