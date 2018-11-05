select 
	id as Id	,
	writing_title	as title
from t_md_writings 
where  unix_timestamp(now())-unix_timestamp(update_time)<3600000
AND article_type=#{type};
order by update_time desc
limit 2
