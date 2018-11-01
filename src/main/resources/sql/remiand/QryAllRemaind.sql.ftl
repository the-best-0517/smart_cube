select * from(
select * from(
select 
user_id,is_eating,is_delete,
box_id as  boxId,
remind_time as remindTime,
remind_id as remindId
from t_md_remind 
) a
where user_id = 123 and is_eating = 0 and is_delete = 0
order by remindTime
) b
group by boxId,remindTime