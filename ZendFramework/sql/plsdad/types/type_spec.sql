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
create or replace type namevalue_type as object(
    m_name     varchar2(512)
   ,m_values   clob  
);
/

create or replace type namevalues_type as table of namevalue_type;
/

create or replace type namevalue_parameter_type as object(
    m_parameters namevalues_type
    
   ,member function get_value(p_name  varchar2)       return clob
   ,member function value_exists_for(p_name varchar2) return pls_integer
   ,member function json_encode() return clob
   
   ,member procedure json_decode(jsondata clob)
   ,member procedure add_value(p_name varchar2, p_value clob)
   ,member procedure xmlcontenttype
   ,member procedure htmlcontenttype
   ,member procedure textcontenttype
   ,member procedure contenttype(p_value clob)
   ,member procedure redirect(p_location varchar2)
);
/