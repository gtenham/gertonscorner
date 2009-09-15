<?php

class RemoteFile { 
/** 
* Upload files! 
* 
* @param  $file as a FileVO Object
* @return string
**/ 
   public function upload($file) { 
      $data = $file->filedata;
      file_put_contents( APPLICATION_PATH . '/../tmp/' . $file->filename, $data);
      return 'File: ' . $file->filename .' Uploaded '; 
   } 
} 
?>