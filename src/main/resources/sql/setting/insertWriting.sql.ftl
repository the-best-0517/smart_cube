replace into t_md_writings(
writing_id,
writing_title,
writing_desc,
create_time,
update_time

)values(
	#{writingId},
	#{writingTitle},
	#{writingDesc},
	now(),now()
)