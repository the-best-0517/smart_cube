select 
bill_case_id as caseId,
img_path as imgPath
from t_md_case_img
where bill_case_id  = #{caseId}