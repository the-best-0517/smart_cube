update t_md_remind
set is_delete = '1'
where user_id = #{userId} and is_eating = '1' and is_delete = '0'