select id as id,
inform_id as informId,
user_id as userId,
inform_desc as informDesc,
create_time as createTime,
update_time as updateTime
from t_md_inform
where user_id = #{userId}