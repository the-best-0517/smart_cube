select 
l.link_id as linkId,
l.true_name as trueName,
l.phone,
l.relation,
u.user_id as userId
from t_md_linkman l
left join t_user u on u.phone = l.phone
where l.user_id = #{userId}