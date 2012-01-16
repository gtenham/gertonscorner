<?php
namespace services;

use GcLib\Xml\XMLResource as XMLResource;
use GcLib\Dao\Container as Container;

/**
 * Todo service class
 * 
 * @package services
 * @author Gerton
 *
 */
class TodoService {
	
	private $xmlconfig;
	private $daoContainer;
	private $todoDAO;
	
	/**
	 * TodoService default constructor
	 */
	public function __construct() {
		$this->xmlconfig = &XMLResource::load(APPLICATION_PATH.'/configs/dao-config.xml');
    	$this->daoContainer = new Container($this->xmlconfig);
    	$this->todoDAO = $this->daoContainer->getManager('slim-rest-server')->getDao('todo');
	}
	
	/**
	 * Get all available todos ordered by sorting property
	 * 
	 * @return array of todos:
	 */
	public function getTodos() {
		return $this->todoDAO->find();
	}
	
	/**
	 * Get single todo item identified by given id.
	 * 
	 * @param string $id
	 * @return array with todo data:
	 */
	public function getTodoById($id) {
		$todo = $this->todoDAO->findTodoById($id);
		$errors['errors'] = $todo->getErrors();
		return array_merge($todo->toArray(),$errors);
	}
}