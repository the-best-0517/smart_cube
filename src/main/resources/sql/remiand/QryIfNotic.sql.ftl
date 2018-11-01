select *
from t_md_inform
where user_id = #{userId} and is_delete = '0';