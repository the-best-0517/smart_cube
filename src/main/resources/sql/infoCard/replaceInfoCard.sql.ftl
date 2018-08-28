replace into t_md_infocard(
user_id,
true_name,
sex,
birthday,
blood_type,
pass_ills,
allergy_bill,
emergency_num,
create_time,
update_time
	
)values(
	#{userId},
	#{name},
	#{sex},
	#{birthday},
	#{bloodType},
	#{passIlls},
	#{allergyBill},
	#{emergencyNum},
	now(),now()
)