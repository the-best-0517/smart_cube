select 
r.pill_id as pillId,
r.pill_desc as pillDesc,
r.dose as dose
from t_md_remind r

where r.remind_time = #{remindTime} and r.box_id = #{boxId}
and r.is_eating = 0 and r.is_delete = 0