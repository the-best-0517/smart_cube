insert into t_md_bill_case
(
user_id,
bill_case_id,
create_time,
update_time,
bill_case_hospital,
bill_case_desc

)values(
	#{userId},#{caseId},now(),now(),"",""
)