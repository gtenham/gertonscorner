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
   $LastChangedDate$
====================================================================*/
--
-- Private declarations
PACKAGENAME   constant varchar2( 30 ) := 'plsdad_demo';
REVISIONLABEL constant varchar2( 50 ) := '$Revision$';

-- Initialisation namevalues table type
initvalue namevalues_type := namevalues_type();

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
(         header_data    in out nocopy clob
,         body_data      in out nocopy clob
)
as
   t_req_params   namevalue_parameter_type := namevalue_parameter_type(initvalue);
   t_resp_params  namevalue_parameter_type := namevalue_parameter_type(initvalue);
begin
   -- Initialise the request headers into namevalue_parameter_type
   t_req_params.json_decode(header_data);
   
   -- Add some reponse headers
   t_resp_params.add_value('X-Custom-Head', 'HelloWorld');
   t_resp_params.textcontenttype;
   
   -- Return the out data
   header_data := t_resp_params.json_encode;
   body_data := 'today is: '||to_char(sysdate, 'DD Month YYYY')||' (called from ['||
               PACKAGENAME||'])';
end execute;

end plsdad_demo;
/
show errors