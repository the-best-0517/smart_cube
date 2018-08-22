insert into t_user(
	user_id,
	user_name,
	pwd,
	phone,
	create_time,
	update_time
)values(
	#{userId},
	#{userName},
	#{pwd},
	#{phone},
	now(),
	now()
)