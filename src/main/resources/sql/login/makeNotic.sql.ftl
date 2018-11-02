insert into t_md_inform(
user_id,
inform_desc,
is_delete,
create_time,
update_time
	
)values(
	#{userId},#{informDesc},'0',now(),now()
)