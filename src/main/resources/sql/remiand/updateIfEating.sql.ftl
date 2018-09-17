update t_md_remind
set 
box_id = #{boxId},
update_time = now()
where box_id = #{boxId} and user_id = #{userId}