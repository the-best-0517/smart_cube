REPLACE into t_mybox_info(
user_id,
box_serial,
box_num
)values(
	#{userId},
	#{boxSerial},
	#{boxNum}
)
