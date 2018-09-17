update t_re_pill
set dose = #{dose},
update_time = now()
where user_id = #{userId} and pill_id = #{pillId}