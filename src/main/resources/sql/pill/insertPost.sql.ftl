insert into t_pill_post(
pill_id,
user_id,
post,
create_time,
update_time	
)values(
	#{pillId},
	#{userId},
	#{feelText},
	now(),now()
)