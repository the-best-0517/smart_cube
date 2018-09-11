select 
link_id as linkId,
true_name as trueName,
phone,
relation
from t_md_linkman
where user_id = #{userId}