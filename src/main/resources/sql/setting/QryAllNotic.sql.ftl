select 
id as id,
notic_id as noticId,
notic_title as noticTitle,
notic_desc as noticDesc,
create_time as createTime
from t_md_notic
order by update_time desc
limit 3
