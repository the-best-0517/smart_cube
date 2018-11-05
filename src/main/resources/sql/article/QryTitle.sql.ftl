select 
	id as Id	,
	writing_title	as title,
	unix_timestamp(now()) - unix_timestamp(update_time),
	unix_timestamp(now()),
	unix_timestamp(update_time)
from t_md_writings 
where  unix_timestamp(now()) - unix_timestamp(update_time)<3600
AND article_type=#{type};