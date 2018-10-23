select remind_time as remindTime,is_eating as isEating,pill_desc as pillDesc,
left(remind_time,4) as year,subString(remind_time,6,5) as date,right(remind_time,5) as time
from t_md_remind
where user_id=#{userId} 