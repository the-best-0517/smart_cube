update t_md_remind
set 
is_eating = '1',
update_time = now()
where box_id = #{boxId} and user_id = #{userId}