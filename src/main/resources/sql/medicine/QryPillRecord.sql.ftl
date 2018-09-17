select 
pill_id as pillId,
pill_desc as pillDesc,
dose 
from t_md_remind
where box_id = #{boxId} and user_id = #{userId} and is_eating = '0'