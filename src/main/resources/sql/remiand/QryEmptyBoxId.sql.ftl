select box_id 
from t_md_remind
where is_eating = '0' and is_delete = '0' and user_id = #{userId}