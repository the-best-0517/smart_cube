select * from(
select 
id , 
pill_id as pillId ,
illness,
count(*) as assess,
'good' as type
from t_pill_star
where  feel_star = '3' or feel_star = '4' 
group by illness
union
select 
id , 
pill_id as pillId ,
illness,
count(*) as assess,
'common' as type
from t_pill_star
where  feel_star = '1' or feel_star = '2' 
group by illness
union
select 
id , 
pill_id as pillId ,
illness,
count(*) as assess,
'bad' as type
from t_pill_star
where  feel_star = '0'
group by illness
) a
where pillId = #{pillId}