select user_name as userName,
	   phone as phone,
	   breakfast,lunch,dinner,
	   head_img_path as headImgPath,
	   qr_code as qrCode
from t_user
where user_id = #{userId}