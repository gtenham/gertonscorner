<?php
namespace models\dao;

use models\domain\Todo as Todo;

/**
 * Todo Data Access Object class
 * 
 * @package models\dao
 * @author Gerton
 *
 */
class TodoDAO {
	protected $_daogateway;
	protected $_todoMapper = array('ID'=>'id'
	                              ,'DONE'=>'done'
	                              ,'SORTING'=>'order'
	                              ,'DESCRIPTION'=>'description'
	                              ,'STARTDATE'=>'startDate');
	/**
	 * Public constructor Todo Data Access Object
	 * 
	 * @param $daogateway
	 */
	public function __construct($daogateway) {
		$this->_daogateway = $daogateway;
	}
	
	/**
	 * Find all todos ordered by sorting attribute.
	 * 
	 * @return array based on \models\domain\Todo array representation
	 */
	public function find() {
		$todos = array();
		$conn = $this->_daogateway->getConnection();
    	$stmt = oci_parse($conn, 'select * from todos order by sorting');
    	oci_set_prefetch($stmt,100);
    	$results = oci_execute($stmt, OCI_DEFAULT);
    	if ($results) {
    		while ($row = oci_fetch_assoc($stmt)) {
    			foreach($this->_todoMapper as $dbcol => $attr) {
	    			if (array_key_exists($dbcol, $row)) {
	    			   $data[$attr] = $row[$dbcol];
	    			}
	    		}
	    		$todo = new Todo($data);
	    		array_push($todos, $todo->toArray());
    		}
    	}
    	oci_free_statement($stmt);
		oci_close($conn);
		
		return $todos;
	}
	
	/**
	 * Find single Todo record identified by given id.
	 * 
	 * @param string $id
	 * @return \models\domain\Todo
	 */
	public function findTodoById($id) {
		$conn = $this->_daogateway->getConnection();
    	$stmt = oci_parse($conn, 'select * from todos where id = :id');
    	oci_bind_by_name($stmt, ':id', $id);
    	$results = oci_execute($stmt, OCI_DEFAULT);
    	if ($results) {
    		$row = oci_fetch_assoc($stmt);
    		foreach($this->_todoMapper as $dbcol => $attr) {
    			if (array_key_exists($dbcol, $row)) {
    			   $data[$attr] = $row[$dbcol];
    			}
    		}
    	}
    	oci_free_statement($stmt);
		oci_close($conn);
		
    	$todo = new Todo($data);
    	return $todo;
	}
}