insert into t_md_case_img(
	bill_case_id,
	img_path,
	create_time,
	update_time
)values(
	#{caseId},
	#{imgPath},
	now(),
	now()
)