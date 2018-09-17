select 
rp.pill_id as pillId,
rp.pill_desc as pillDesc,
rp.dose as dose
from t_re_pill rp
where user_id = #{userId}