select 
box_serial as boxSerial,
box_num as boxNum
from t_mybox_info
where user_id = #{userId} and box_serial = #{boxSerial}