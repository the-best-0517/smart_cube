select
 id as Id ,
 writing_title as title 
from t_md_writings
where article_type= #{type}
AND date(update_time) = curdate()
limit 5


