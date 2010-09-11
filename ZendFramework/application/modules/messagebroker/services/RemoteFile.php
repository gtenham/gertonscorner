<?php

class RemoteFile {
	 
	protected $xmlconfig;
	
	/** 
	* Upload files! 
	* 
	* @param  $file as a Messagebroker_Model_FileVO Object
	* @return string
	**/ 
   public function upload($file) { 
   	  $this->xmlconfig = &GcLib_Xml_XMLResource::load(APPLICATION_PATH .'/configs/dao-config.xml');
   	  $daocontainer = new GcLib_Dao_Container($this->xmlconfig);
   	  $filedao = $daocontainer->getManager('files')->getDao('file');
   	  return $filedao->saveFile($file);
   	  /*
      $data = $file->filedata;
      file_put_contents( APPLICATION_PATH . '/../tmp/' . $file->filename, $data);
      return 'File: ' . $file->filename .' Uploaded '; 
      */
   } 
} 
?>