select id as id,
user_id as userId,
create_time,
update_time as updateTime,
concat(create_time," : ",inform_desc) as informDesc
from t_md_inform
where user_id = #{userId}
order by create_time desc
limit 15