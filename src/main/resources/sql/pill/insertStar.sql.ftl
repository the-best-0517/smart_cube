insert into t_pill_star(
pill_id,
user_id,
feel_star,
create_time,
update_time	
)values(
	#{pillId},
	#{userId},
	#{starText},
	now(),
	now()
)