select 
mi.box_serial as boxSerial,
bi.BoxType as boxType,
bt.square_number as sNum
from t_mybox_info  mi
left join t_box_Info bi on bi.BoxSerial = mi.box_serial
left join t_box_type bt on bt.box_type_id = bi.BoxType
where mi.user_id = #{userId}