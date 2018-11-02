select 
notic_id as id,
notic_title as title,
notic_desc as text, 
update_time as time
from t_md_notic
where notic_id  = #{id}