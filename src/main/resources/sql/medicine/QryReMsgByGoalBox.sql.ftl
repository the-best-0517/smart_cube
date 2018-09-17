select 
pill_id as pillId,
pill_desc as pillDesc,
box_id as boxId,
remind_time as remindTime,
dose as dose
from t_md_remind
where box_id = #{goalBox} and user_id = #{userId} and is_eating = '0'