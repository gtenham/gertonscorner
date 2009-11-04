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
create or replace type body namevalue_parameter_type is
    member function value_exists_for(p_name varchar2) 
	return pls_integer 
	is
        l_target_parameter varchar2(512) := lower(p_name);
    begin
        if m_parameters.count = 0 then
            return -1;
        else
            for i in m_parameters.first..m_parameters.last loop
                if( lower(m_parameters(i).m_name) = l_target_parameter ) then
                    return i;
                end if;
            end loop;
            return -1;
        end if;
    end;
    
    member function get_value(p_name varchar2) 
	return clob 
	is
        l_index            pls_integer   := value_exists_for(p_name);
    begin
        if l_index != -1 then
            return m_parameters(l_index).m_values;
        else
            return null;
        end if;
    end;
    
    member procedure add_value(p_name varchar2, p_value clob) 
	is
        l_index pls_integer := value_exists_for(p_name);
    begin
        if l_index != -1 then
            m_parameters(l_index).m_values := p_value;
        else
            m_parameters.extend(1);
            m_parameters(m_parameters.count) := namevalue_type(p_name, p_value);
        end if;
    end;
	
	member function json_encode 
	return clob 
	is
	   l_jsondata  clob := '{';
	   l_paramname varchar2(520);
	begin
	   if m_parameters.count = 0 
	   then
          l_jsondata := '{}';     
       else
          for i in m_parameters.first..m_parameters.last 
		  loop
		     l_paramname := '"'||m_parameters(i).m_name||'":"';
             dbms_lob.writeappend(l_jsondata, length(l_paramname), l_paramname);
			 dbms_lob.append(l_jsondata, m_parameters(i).m_values);
			 dbms_lob.writeappend(l_jsondata, 2, '",');
          end loop;
		  dbms_lob.trim(l_jsondata, dbms_lob.getlength(l_jsondata)-1);
		  dbms_lob.writeappend(l_jsondata, 1, '}');
       end if;
	   return l_jsondata;
	end json_encode;
	
    member procedure json_decode(jsondata clob) 
	is
	   l_paramname   varchar2(512);
       l_jsondata    clob := jsondata;
       l_paramvalue  clob := ' ';
       l_clobdata    clob:= ' ';
       l_tmp         clob:= ' ';
   
       l_valuelength number;
       n             number;
       l_length      number;
    begin
       dbms_lob.trim(l_jsondata,dbms_lob.getlength(l_jsondata)-1);
       dbms_lob.writeappend(l_jsondata,1,',');
       l_length := dbms_lob.getlength(l_jsondata);
       loop
          exit when l_jsondata is null or dbms_lob.instr(l_jsondata,',' ) = 0;
          n := dbms_lob.instr(l_jsondata,',' );
          -- Ready for name-value pair
          dbms_lob.copy(l_clobdata, l_jsondata,n-2,1,1);
          l_valuelength := (dbms_lob.getlength(l_clobdata)-dbms_lob.instr(l_clobdata,':"' ))-1;
          l_paramname := dbms_lob.substr(l_clobdata, dbms_lob.instr(l_clobdata,':"' ),1);
          l_paramname := ltrim(ltrim(rtrim(l_paramname,'":'),'{'),'"');
          dbms_lob.copy(l_paramvalue, l_clobdata,l_valuelength,1,dbms_lob.instr(l_clobdata,':"' )+2);
          -- Add namevalue pair
		  add_value(l_paramname, l_paramvalue);
          
	      if (l_length-n) > 0
          then
             dbms_lob.copy(l_tmp,l_jsondata,l_length-n,1,n+1);
          end if;
          l_jsondata := l_tmp;
          l_length := dbms_lob.getlength(l_jsondata);
          -- Reset working clobs
          l_clobdata := ' ';
          l_tmp := ' ';
          l_paramvalue := ' ';
       end loop;
   
    end json_decode;
	
    member procedure shouldredirect 
	is
    begin
	    add_value('HTTP_RESPONSE_CODE','302');
	end;
	
	member procedure xmlcontenttype 
	is
	begin
		add_value('Content-Type','text/xml');
    end;
    
    member procedure htmlcontenttype 
	is
    begin
        add_value('Content-Type','text/html');
    end;
    
    member procedure textcontenttype 
	is
    begin
        add_value('Content-Type','text/plain');
    end;    
    
    member procedure contenttype(p_value clob) 
	is
    begin
	    add_value('Content-Type',p_value);
    end;
end;
/
