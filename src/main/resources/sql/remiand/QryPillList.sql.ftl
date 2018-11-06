select pill_id as pillId,
	   pill_desc as pillDesc 
from t_md_remind
where user_id = #{userId} and is_eating = '1' and is_delete = '0'