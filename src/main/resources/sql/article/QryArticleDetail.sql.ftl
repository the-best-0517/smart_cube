select
 writing_id as writingId ,
 writing_title as writingTitle ,
 writing_desc as writingDesc
from t_md_writings
where  writing_id=#{articleId}


