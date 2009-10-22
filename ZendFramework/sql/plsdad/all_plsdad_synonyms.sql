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
-- This file should be run as a database user with DBA role!
-- Always provide a schema name when referencing database objects
-- like this: <schema>.<objectname>
--
prompt Creating private synonyms for phpapp user
prompt
create or replace synonym phpapp.plsdad_demo for plsdad.plsdad_demo;