select *,teamname_byid(home_id) zhudui,teamname_byid(away_id) kedui,
teamlogo(home_id) zhudui_logo,

teamlogo(away_id) kedui_logo
 from football_match_t
 
 
 
 select

0.33 `home_odds`, 0.33 `tie_odds`,0.33 AS `away_odds`,
'max' AS `type`,0.92 AS `home_odds_chupan`,0.5 AS `tie_odds_chupan`,0.88 AS away_odds_chupan;

select * from sys_views;


insert into sys_views set outterSubq=' select * from foot_odds_tongji ',name='football_odds_t_ex'


insert into sys_views set outterSubq='( select * from foot_odds_tongji ) as odds_tongji ',name='football_odds_t_ex',subq
;
update sys_views set subquerys='(select 2 id ) as subObj ';


update sys_views set  outterSubq='( select * from foot_odds_tongji ) as odds_tongji '


select *,teamname_byid(home_id) zhudui,teamname_byid(away_id) kedui,
teamlogo(home_id) zhudui_logo,

teamlogo(away_id) kedui_logo,
saishiName(match_event_id) saishiName, -0.5 panlu,
score_byteamidMatchid(id,home_id,1) zhudui_score,

score_byteamidMatchid(id,away_id,2) kedui_score

 from football_match_t
where id=3380166
 ;

select name_zh from football_event_t where id=1;
select * from football_score_t;

select * from foot_detail_data_v;

select * from football_team_t;
select *, json_extract(scoreObj_byTeamMatch(10000,3380166,1),'$.red_card') as scobj from football_match_t where id=3380166
ft join football_team_t t  t on home_id
;
 select m.*,sc.team_id zhudui_teamid,sc_kd.team_id kedui_teamid from football_match_t m
 left join football_score_t  sc on m.id=sc.match_id and sc.team_type=1
 left join football_score_t  sc_kd on m.id=sc.match_id and sc_kd.team_type=2

where m.id=3380166 and m.home_id=sc.team_id