<?php
class Model_Dao_FileDAO {
	protected $_daogateway;
		                        
	public function __construct($daogateway) {
		$this->_daogateway = $daogateway;
		$this->_daogateway->setPath(APPLICATION_PATH . '/../tmp/');
	}
	
	public function saveFile( Model_Domain_File $file ) {
		$path = $this->_daogateway->getPath();
		file_put_contents( $path . $file->filename, $file->filedata);
        return 'File: ' . $file->filename .' Uploaded ';
	}
}