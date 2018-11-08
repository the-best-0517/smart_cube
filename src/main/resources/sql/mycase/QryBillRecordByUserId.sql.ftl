select * ,sum(d) as dose from(
select 
cp.case_id,
cp.create_time,
cp.update_time,
p.pill_desc as pillDesc,
r.pill_id as pillId,
r.dose as d
from t_md_case_pill cp
left join t_md_pill p on p.pill_id = cp.pill_id
left join t_md_remind r on r.case_id = cp.case_id
where cp.case_id = #{caseId}
) a
group by a.pillId

union 

select * ,sum(d) as dose from(
select 
cp.case_id,
cp.create_time,
cp.update_time,
cp.pill_desc as pillDesc,
r.dose as d
from t_md_case_pill_no  cp
left join t_md_remind r on r.case_id = cp.case_id
where cp.case_id =#{caseId}
) a
group by a.pillDesc