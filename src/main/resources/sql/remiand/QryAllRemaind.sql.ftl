select * from(
select 
user_id,is_eating,is_delete,
box_id as  boxId,
remind_time as remindTime,
remind_id as remindId
from t_md_remind 
group by box_id,remind_time
) a
where user_id = #{userId}
and is_eating = 0 and is_delete = 0
