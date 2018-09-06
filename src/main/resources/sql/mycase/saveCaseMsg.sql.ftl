insert into t_md_bill_case(
user_id,
bill_case_id,
bill_case_hospital,
bill_case_desc,
create_time,
update_time
	
)values(
	#{userId},
	#{caseId},
	#{hospital},
	#{season},
	#{visitDate},
	now()
)