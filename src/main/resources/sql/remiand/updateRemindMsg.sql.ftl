update t_md_remind
set remind_time  = #{remindEdit}
where box_id = #{boxId} and remind_time = #{remindTime}