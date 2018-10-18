select pill_desc as pillDesc 
from t_md_remind
where is_eating = '0' and box_id = #{goalBox}