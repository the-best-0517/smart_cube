update t_md_remind
set remind_time  = #{remindEdit},
box_id = #{remindBox}
where box_id = #{boxId} and remind_time = #{remindTime}