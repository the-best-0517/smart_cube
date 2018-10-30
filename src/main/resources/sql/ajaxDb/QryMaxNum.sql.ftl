select box_num as boxNum,
box_serial as boxSerial
from t_mybox_info
where user_id = #{userId}
order by id desc
limit 0,1