select pill_desc as blurDesc
from t_md_pill
where pill_desc like concat('%',#{pillDesc},'%')