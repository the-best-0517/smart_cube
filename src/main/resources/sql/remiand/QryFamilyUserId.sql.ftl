select u.phone,
l.true_name as trueName,
l.user_id as userId
from t_user u
left join t_md_linkman l on u.phone = l.phone
where u.user_id = #{userId}