select l.phone,
	u.user_id as userId,
	u.phone as phone
from t_md_linkman l
left join t_user u on u.phone = l.phone
where l.user_id = #{userId}