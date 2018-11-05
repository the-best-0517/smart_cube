select
 id as Id ,
 writing_title as title 
from t_md_writings
where article_type= #{type}
AND  unix_timestamp(now())-unix_timestamp(update_time)<43200000
limit 5


