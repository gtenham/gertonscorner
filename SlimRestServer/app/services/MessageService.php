<?php
namespace services;

/**
 * Message service class
 * 
 * @package services
 * @author Gerton
 *
 */
class MessageService {
	
	private $connection;
	
	/**
	 * MessageService default constructor
	 */
	public function __construct() {
		$this->connection = new \amqphp\Stomp('tcp://localhost:61613');
	}
	
	public function send($action, $message) {
		$headers['content-type'] = 'application/json';
		$this->connection->connect('guest','guest');
		try {
			$msg = '{"action":"'.$action.'","message":"'.$message.'"}';
			$this->connection->send('/queue/todos', $msg, $headers);
		} catch(\Exception $e) {
			//echo $e->getMessage();
		}
		$this->connection->disconnect();
	}
	
	
}