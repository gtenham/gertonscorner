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
------------------------------------------------
-- Main Install file for Plsdad database code --
------------------------------------------------

spool install-plsdad.log

prompt enter enviroment substitution parameters if not already set!
prompt
accept l_sid_tns    char prompt 'Database connection tns alias                : > '
accept l_usr_dba    char prompt 'DBA schema name (default system)             : > ' default 'SYSTEM'
accept l_pwd_dba    char prompt 'DBA schema Password                          : > ' hide

prompt Connect as &l_usr_dba ...
connect &l_usr_dba/&l_pwd_dba@&l_sid_tns

create user phpapp identified by phpapp default tablespace users temporary tablespace temp;                        
grant create session to phpapp;
grant alter session to phpapp;  

create user plsdad identified by plsdad default tablespace users temporary tablespace temp;
grant connect to plsdad;
grant resource to plsdad;

prompt Connect as plsdad ...
connect plsdad/plsdad@&l_sid_tns

prompt
prompt Creating Plsdad package specs
prompt =============================
prompt
@@all_plsdad_pks.sql

prompt
prompt Creating Plsdad package bodies
prompt ==============================
prompt
@@all_plsdad_pkb.sql

prompt Connect as &l_usr_dba ...
connect &l_usr_dba/&l_pwd_dba@&l_sid_tns

prompt
prompt Granting privileges
prompt ==============================
prompt
@@all_plsdad_grants.sql

prompt
prompt Creating private synonyms
prompt ==============================
prompt
@@all_plsdad_synonyms.sql

disconnect

spool off