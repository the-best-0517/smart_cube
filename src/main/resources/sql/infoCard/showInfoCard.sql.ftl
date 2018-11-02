SELECT
	true_name AS name,
	td.name AS sex,
	birthday,
	blood_type AS bloodType,
	pass_ills AS passIlls,
	allergy_bill AS allergyBill,
	emergency_num AS emergencyNum 
FROM
	t_md_infocard tmi
	LEFT JOIN t_dict td ON tmi.sex = td.CODE AND td.cata_code = 'sex' 
WHERE
	user_id = #{userId}