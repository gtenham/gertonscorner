<?php
namespace models\dao;

use models\domain\Todo as Todo;
use services\exceptions\NotFoundException as NotFoundException;
use services\exceptions\NoContentException as NoContentException;

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
    	oci_bind_by_name($stmt, ':id', $id, 200);
    	$results = oci_execute($stmt, OCI_DEFAULT);
    	$data = array();
    	
    	if ($results) {
    		$row = oci_fetch_assoc($stmt);
    		if ($row != false) {
	    		foreach($this->_todoMapper as $dbcol => $attr) {
	    			if (array_key_exists($dbcol, $row)) {
	    			   $data[$attr] = $row[$dbcol];
	    			}
	    		}
    		} else {
    			throw new NotFoundException();
    		}
    	}
    	oci_free_statement($stmt);
		oci_close($conn);
		
    	$todo = new Todo($data);
    	return $todo;
	}
	
	/**
	 * Save todo data
	 * 
	 * @param Todo $todo
	 * @return Todo
	 */
	public function save(Todo $todo) {
		$success = true;
		$conn = $this->_daogateway->getConnection();
		$guid = "lower(regexp_replace(sys_guid(),'(.{8})(.{4})(.{4})(.{4})(.{12})', '\\1-\\2-\\3-\\4-\\5'))";
		if ($todo->id == null) {
			// Insert new todo
			$stmt = oci_parse($conn, "insert into todos (id, done, sorting, startdate, description ) 
									  values (" . $guid . ", :done, :sorting, :startdate, :description) 
									  returning id into :id");
			oci_bind_by_name($stmt, ':id', $todo->id,200);
    		oci_bind_by_name($stmt, ':done', $todo->done,1);
    		oci_bind_by_name($stmt, ':sorting', $todo->order,3);
    		oci_bind_by_name($stmt, ':startdate', $todo->startDate);
			oci_bind_by_name($stmt, ':description', $todo->description, 2000);
			$results = oci_execute($stmt, OCI_DEFAULT);
		} else {
			// Update existing Todo
			$stmt = oci_parse($conn, "update todos 
									  set done = :done
									  , sorting = :sorting
									  , startdate = :startdate
									  , description = :description 
									  where id = :id");
			oci_bind_by_name($stmt, ':id', $todo->id,200);
    		oci_bind_by_name($stmt, ':done', $todo->done,1);
    		oci_bind_by_name($stmt, ':sorting', $todo->order,3);
    		oci_bind_by_name($stmt, ':startdate', $todo->startDate);
			oci_bind_by_name($stmt, ':description', $todo->description, 2000);
			$results = oci_execute($stmt, OCI_DEFAULT);
		}
		
		$todo->validate();
		if ($success && $todo->isValid()) {
			oci_commit($conn);
		} else {
			oci_rollback($conn);
		}
    	oci_free_statement($stmt);
		oci_close($conn);
		return $todo;
	}
	
	/**
	 * Remove todos with ids in array
	 * @param array $ids
	 */
	public function destroy(array $ids) {
		$success = true;
		$conn = $this->_daogateway->getConnection();
    	$stmt = oci_parse($conn, 'delete from todos where id = :id');
    	oci_bind_by_name($stmt, ':id', $id, 200);
    	
		foreach ($ids as $id) {
		    $results = oci_execute($stmt, OCI_DEFAULT);
		    if (!$results) {
		    	$success = false;
		    	break;
		    }
		}
		if ($success) {
			oci_commit($conn);
		} else {
			oci_rollback($conn);
		}
    	oci_free_statement($stmt);
		oci_close($conn);
		
		throw new NoContentException();
	}
	
	/**
	 * Generates a new Global UUID using oracle sys_guid() function
	 * @return string guid
	 */
	public function generateGuid() {
		$guid = "";
		$conn = $this->_daogateway->getConnection();
		$stmt = oci_parse($conn, "select lower(regexp_replace(sys_guid(),'(.{8})(.{4})(.{4})(.{4})(.{12})', '\\1-\\2-\\3-\\4-\\5')) guid from dual");
    	
		$results = oci_execute($stmt, OCI_DEFAULT);
    	
    	if ($results) {
    		$row = oci_fetch_assoc($stmt);
    		if ($row != false) {
    			$guid = $row['GUID'];
    		} 
    	}
    	oci_free_statement($stmt);
		oci_close($conn);
		
    	return $guid;
	}
}