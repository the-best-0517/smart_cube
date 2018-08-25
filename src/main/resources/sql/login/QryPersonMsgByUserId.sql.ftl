select user_name as userName,
	   phone as phone,
	   breakfast,lunch,dinner
from t_user
where user_id = #{userId}