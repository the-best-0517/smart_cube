update into t_user
set 
user_name = #{userName},
phone = #{phone},
breakfast = #{breakfast},
lunch = #{lunch},
dinner = #{dinner}
where user_id = #{userId}	