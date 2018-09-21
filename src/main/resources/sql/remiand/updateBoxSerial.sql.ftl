update t_mybox_info
set box_serial = #{boxSerial}
box_num = #{boxNum}
where user_id = #{userId} and box_serial = #{boxSerial}
