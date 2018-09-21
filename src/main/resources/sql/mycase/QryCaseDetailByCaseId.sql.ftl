select 
bc.bill_case_id as caseId,
bc.bill_case_hospital as hospital,
bc.bill_case_desc as season,
bc.create_time as visitDate,
ci.img_path as imgPath
from t_md_bill_case bc
left join t_md_case_img ci on ci.bill_case_id = bc.bill_case_id
where bc.user_id = #{userId} and bc.bill_case_id = #{caseId}