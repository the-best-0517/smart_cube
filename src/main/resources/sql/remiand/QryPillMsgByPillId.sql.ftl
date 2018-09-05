select pill_id as pillId,pill_desc as pillDesc,
instructions,
where_eating ,
pill_instruction as pillInstruction,
imgBase64 as pillImg,
d.name as whereEating
from t_md_pill
left join t_dict d on d.code = where_eating and cata_code = 'where_eating'
where pill_id = #{pillId}