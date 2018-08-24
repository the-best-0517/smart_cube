select pill_id as pillId,pill_desc as pillDesc,instructions
from t_md_pill
where pill_id = #{pillId}