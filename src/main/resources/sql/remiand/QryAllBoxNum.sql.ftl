select bi.BoxType , 
bt.square_number as squareNumber
from t_box_Info bi
left join t_box_type bt on bt.box_type_id = bi.BoxType
where bi.BoxSerial = #{boxSerial}