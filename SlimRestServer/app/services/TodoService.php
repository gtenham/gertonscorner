<?php
namespace services;

use GcLib\Xml\XMLResource as XMLResource;
use GcLib\Dao\Container as Container;
use models\domain\Todo as Todo;

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
		$todolist['todos'] = $this->todoDAO->find();
		return $todolist;
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
	
	public function removeTodo($id) {
		$this->todoDAO->destroy(array($id));
	}
	
	public function addTodo($data) {
		$todo = new Todo($data);
		//$todo->validate();
		var_dump($this->todoDAO->save($todo));
	}
}