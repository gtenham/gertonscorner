<?php
namespace models\dao;

/**
 * Etag Data Access Object class
 * 
 * @package models\dao
 * @author Gerton
 *
 */
class EtagDAO {
	protected $_daogateway;
	
	/**
	 * Public constructor Etag Data Access Object
	 * 
	 * @param $daogateway
	 */
	public function __construct($daogateway) {
		$this->_daogateway = $daogateway;
	}
	
	public function get($key) {
		$conn = $this->_daogateway->getConnection();
		if (!$conn->get($key)) {
			$conn->set($key,1);
			return 1;
		}
		return $conn->get($key);
	}
	
	public function increment($key) {
		$conn = $this->_daogateway->getConnection();
		if (!$conn->get($key)) {
			$conn->set($key,1);
			return 1;
		}
		return $conn->increment($key, 1);
	}
	
	public function destroy($key) {
		$conn = $this->_daogateway->getConnection();
		$conn->delete($key);
	}
}