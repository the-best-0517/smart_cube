select 
r.pill_id as pillId,
p.pill_desc as pillDesc,
r.dose as dose
from t_md_remind r
left join t_md_pill p on p.pill_id = r.pill_id
where r.box_id = 4
and r.is_eating = 0 and r.is_delete = 0