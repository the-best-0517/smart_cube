select 
pill_id as pillId,
pill_desc as pillDesc,
dose ,
remind_time as remindTime
from t_md_remind
where box_id = #{boxId} and user_id = #{userId} and is_eating = '0'