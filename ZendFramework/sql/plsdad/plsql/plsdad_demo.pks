create or replace package plsdad_demo
as

/** 
*  Demo for plsdad-php
*  <br/>
*  $Revision$<br/>    
*  $LastChangedDate$<br/>
*@headcom
*/

/* ==================================================================
   This work is licensed under the 
   Creative Commons Attribution 3.0 Netherlands License. 
   To view a copy of this license, visit 
   http://creativecommons.org/licenses/by/3.0/nl/ 
   or send a letter to Creative Commons, 171 Second Street, Suite 300, 
   San Francisco, California, 94105, USA.

   $Revision$
   $LastChangedBy$
   $LastChangedDate$
====================================================================*/
--

/** 
* get the revision number of the package body
*
* @return subversion revision number
*/
function getRevision
return varchar2;

/**
* Wrapper execute procedure
*
* @param response containing the response data send to php
*/
procedure execute
(         response  in out clob nocopy
);

end plsdad_demo;
/
show errors