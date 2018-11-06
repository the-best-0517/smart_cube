
SELECT  
 writing_id as writingId ,
 writing_title as title 
FROM t_md_writings AS t1 JOIN (SELECT ROUND(RAND() * ((SELECT MAX(id) FROM t_md_writings)-(SELECT MIN(id) FROM t_md_writings))+(SELECT MIN(id) FROM t_md_writings)) AS id) AS t2
WHERE t1.id >= t2.id AND article_type= #{type}
#AND date(update_time) = curdate()
AND unix_timestamp(now()) - unix_timestamp(update_time)<604800
Order by t1.id
limit 10