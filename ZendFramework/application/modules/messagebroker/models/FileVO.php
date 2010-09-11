<?php
class Messagebroker_Model_FileVO extends Model_Domain_File {
   /*
    * Return Remote Actionscript Mapping class
    * use [RemoteClass(alias="FileVO")] within your actionscript class
    */
   public function getASClassName() {
        return 'FileVO';
    }
}
?>