insert into 
t_md_case_pill (
case_id,
pill_id,
create_time,
update_time	,
dose
)values(
	#{caseId},#{pillId},now(),now(),#{dose}
)
