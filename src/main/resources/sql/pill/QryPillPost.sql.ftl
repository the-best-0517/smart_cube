select
pp.user_id,
u.user_name as userName,
pp.post,
pp.create_time as createTime
from t_pill_post pp
left join t_user u on u.user_id = pp.user_id
where pp.user_id = #{userId} and pp.pill_id = #{pillId}