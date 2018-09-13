select 
remind_id as remindId,
user_id as userId,
pill_desc as pillDesc,
box_id as boxId,
remind_time remindTime,
is_delete as isDelete,
dose as dose
from t_md_remind
where remind_time = #{before} and user_id = #{userId} and is_eating = '0'