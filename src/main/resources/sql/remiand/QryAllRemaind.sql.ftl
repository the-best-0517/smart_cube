select distinct
box_id as  boxId,
remind_time as remindTime
#remind_id as remindId
from t_md_remind 
where user_id = 123
and is_eating = 0 and is_delete = 0
