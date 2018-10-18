update t_md_remind
set remind_time = #{remindTime}
where user_id = #{userId} and box_id = #{boxId}