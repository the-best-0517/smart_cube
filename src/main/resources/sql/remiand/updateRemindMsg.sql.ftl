update t_md_remind
set remind_time  = #{remindEdit},
box_id = #{remindBox}
where remind_id = #{remindId}