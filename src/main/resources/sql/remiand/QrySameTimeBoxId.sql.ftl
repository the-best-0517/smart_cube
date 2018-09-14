select box_id as boxId
from t_md_remind
where user_id = #{userId}  and remind_time = #{rt}