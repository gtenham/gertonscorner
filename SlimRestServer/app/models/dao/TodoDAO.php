<?php
namespace models\dao;

use models\domain\Todo as Todo;

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
    	$todo = new Todo($data);
    	return $todo;
	}
}