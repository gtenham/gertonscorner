<?php

class RemoteFile { 
/** 
* Upload files! 
* 
* @param  $file as a Messagebroker_Model_FileVO Object
* @return string
**/ 
   public function upload(Messagebroker_Model_FileVO $file) { 
      $data = $file->filedata;
      file_put_contents( APPLICATION_PATH . '/../tmp/' . $file->filename, $data);
      return 'File: ' . $file->filename .' Uploaded '; 
   } 
} 
?>