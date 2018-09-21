select 
mi.user_id as userId,
mi.box_serial as boxSerial,
mi.box_num as boxNum,
bi.BoxType as boxType,
bt.square_number as squareNumber
from t_mybox_info mi
left join t_box_Info bi on bi.BoxSerial = mi.box_serial and mi.user_id = #{userId}
left join t_box_type bt on bi.BoxType = bt.box_type_id