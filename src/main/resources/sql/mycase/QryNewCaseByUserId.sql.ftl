select 
bill_case_id  as caseId,
bill_case_hospital as caseHospital,
bill_case_desc as  caseDesc,
create_time as caseTime
from t_md_bill_case
where user_id = #{userId} and bill_case_id like #{caseId}
order by update_time desc
limit 1