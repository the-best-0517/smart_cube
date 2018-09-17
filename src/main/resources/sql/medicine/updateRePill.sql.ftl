update t_md_remind
set dose = #{dose},
update_time = now()
where user_id = #{userId} and pill_id = #{pillId} and box_id = #{boxId}