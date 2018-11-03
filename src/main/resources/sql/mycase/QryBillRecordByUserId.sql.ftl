select * from(
select 
cp.case_id,
cp.pill_id as pillId,
cp.create_time,
cp.update_time,
p.pill_desc as pillDesc,
count(cp.dose) as dose
from t_md_case_pill cp
left join t_md_pill p on p.pill_id = cp.pill_id
where cp.case_id = #{caseId}
) a
group by a.pillId