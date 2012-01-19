<?php
namespace models\dao;

use models\domain\Todo as Todo;
use services\exceptions\NotFoundException as NotFoundException;

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
    			throw new NotFoundException('Todo not found');
    		}
    	}
    	oci_free_statement($stmt);
		oci_close($conn);
		
    	$todo = new Todo($data);
    	return $todo;
	}
	
	public function save(Todo $todo) {
		/*
		 * select lower(regexp_replace(sys_guid(),'(.{8})(.{4})(.{4})(.{4})(.{12})', '\1-\2-\3-\4-\5')) guid from dual;
		 */
		$newid = null;
		$success = true;
		$conn = $this->_daogateway->getConnection();
		$guid = "lower(regexp_replace(sys_guid(),'(.{8})(.{4})(.{4})(.{4})(.{12})', '\\1-\\2-\\3-\\4-\\5'))";
		if ($todo->id == null) {
			// Insert new todo
			$stmt = oci_parse($conn, "insert into todos (id, done, sorting, startdate, description ) 
									  values (" . $guid . ", :done, :sorting, :startdate, :description) 
									  returning id into :id");
			oci_bind_by_name($stmt, ':id', $newid,40);
    		oci_bind_by_name($stmt, ':done', $todo->done,1);
    		oci_bind_by_name($stmt, ':sorting', $todo->order,3);
    		oci_bind_by_name($stmt, ':startdate', $todo->startDate);
			oci_bind_by_name($stmt, ':description', $todo->description, 2000);
			$results = oci_execute($stmt, OCI_DEFAULT);
		}
		
		if ($success) {
			oci_commit($conn);
		} else {
			oci_rollback($conn);
		}
    	oci_free_statement($stmt);
		oci_close($conn);
		return $newid;
	}
	
	public function destroy(array $ids) {
		$success = true;
		$conn = $this->_daogateway->getConnection();
    	$stmt = oci_parse($conn, 'delete from todos where id = :id');
    	oci_bind_by_name($stmt, ':id', $id);
    	
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
	}
}