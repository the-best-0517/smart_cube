select 
id as id,
writing_id as writingId, 
writing_title as title,
writing_desc as text,
update_time as time
from t_md_writings
where id  = #{id}