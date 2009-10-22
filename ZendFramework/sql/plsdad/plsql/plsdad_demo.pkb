create or replace package body plsdad_demo
as

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
-- Private declarations
PACKAGENAME   constant varchar2( 30 ) := 'plsdad_demo';
REVISIONLABEL constant varchar2( 50 ) := '$Revision$';

function getRevision
return varchar2
is
begin

   return substr(REVISIONLABEL, 11, instr(REVISIONLABEL,'$',2)-11 );

end getRevision;

--
-- EXECUTE
--
procedure execute
(         response  in out clob nocopy
)
as
begin
   response := 'today is: '||to_char(sysdate, 'DD Month YYYY')||' (called from ['||
               PACKAGENAME||']');
end execute;

end plsdad_demo;
/
show errors