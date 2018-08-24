insert into t_md_remind(

remind_id,
user_id,
pill_id,
box_id,
remind_time,
is_eating,
is_delete,
dose,
create_time,
update_time	
	
)values(
#{remindId},
#{userId},
#{pillId},
#{boxId},
#{remindTime},
0,0,#{dose},now(),now()
)