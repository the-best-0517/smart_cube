select
	id as id,
	pill_id as pillId,
	 pill_desc as  pillDesc,
	 pill_instruction as pillInstruction,
	 instructions as instructions,
	 img_path as pillImgPath,
	update_time as updateTime
from t_md_pill
where pill_desc like concat('%',#{key},'%') or pill_state like concat('%',#{key},'%')