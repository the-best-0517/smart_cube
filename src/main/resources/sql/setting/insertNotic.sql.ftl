insert into t_md_notic(
notic_id 
notic_title
notic_desc
create_time 
update_time
)values(
	#{noticId},
	#{noticTitle},
	#{noticDesc},
	now(),now()
)