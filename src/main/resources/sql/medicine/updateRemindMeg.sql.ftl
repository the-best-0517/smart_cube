update t_md_remind
set  box_id = #{goalBox},
	 update_time = now()
where user_id = #{userId} and pill_desc = #{pillDesc} and box_id = #{boxId} 