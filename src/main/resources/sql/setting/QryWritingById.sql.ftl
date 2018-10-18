select 
writing_title as title,
writing_desc as text,
update_time as time
from t_md_writing
where id  = #{id}