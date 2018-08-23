insert into t_user_role(
	user_id,
	role_id,
	create_time,
	update_time
)values(
	#{userId},
	#{roleId},
	now(),
	now()
)