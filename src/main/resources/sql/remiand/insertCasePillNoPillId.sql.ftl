insert into 
t_md_case_pill_no (
case_id,
pill_desc,
create_time,
update_time	,
dose
)values(
	#{caseId},#{pillDesc},now(),now(),#{dose}
)
