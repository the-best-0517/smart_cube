select 
bill_case_id  as caseId,
bill_case_hospital as caseHospital,
bill_case_desc as  caseDesc,
create_time as caseTime
from t_md_pill_case
where user_id = #{userId}