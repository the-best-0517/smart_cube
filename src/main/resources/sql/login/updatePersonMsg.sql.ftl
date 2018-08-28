update t_user
set 
user_name = #{userName},
phone = #{phone},
breakfast = #{breakfast},
lunch = #{lunch},
dinner = #{dinner},
update_time = now()

where user_id = #{userId}	