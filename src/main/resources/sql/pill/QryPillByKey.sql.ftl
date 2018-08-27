select
	pill_id as pillId,
	 pill_desc as  pillDesc,
	 pill_instruction as pillInstruction	
from t_md_pill
where pill_desc like concat('%',#{key},'%')