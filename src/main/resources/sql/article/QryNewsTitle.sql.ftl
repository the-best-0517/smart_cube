select
 id as Id ,
 writing_title as title 
from t_md_writings
where article_type='news'
order by update_time desc
limit 5


