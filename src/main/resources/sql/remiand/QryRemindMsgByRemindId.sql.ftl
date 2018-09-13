select 
box_id as boxId,
remind_time as remindTime 
from t_md_remind 
where remind_id = #{remindId}