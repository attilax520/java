DROP VIEW
IF EXISTS `foot_match_ex`;
        CREATE
        OR
        REPLACE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `foot_match_ex` AS

select `m`.`id` AS `id`,`m`.`match_id` AS `match_id`,`m`.`team_id` AS `team_id`,`m`.`team_type` AS `team_type`,`m`.`league_ranking` AS `league_ranking`,`m`.`regular_time_score` AS `regular_time_score`,`m`.`half_court_score` AS `half_court_score`,`m`.`score` AS `score`,`m`.`red_card` AS `red_card`,`m`.`yellow_card` AS `yellow_card`,`m`.`corner_kick` AS `corner_kick`,`m`.`overtime_score` AS `overtime_score`,`m`.`point_score` AS `point_score`,`m`.`remark` AS `remark`,`m`.`create_time` AS `create_time`,`m`.`delete_flag` AS `delete_flag`,`m`.`teamname_zhudui` AS `teamname_zhudui`,`m`.`event_saishi` AS `event_saishi`,`m`.`fav` AS `fav`,`m`.`match_status` AS `match_status`,`m`.`kedui_bifen` AS `kedui_bifen`,`m`.`kedui` AS `kedui`,`m`.`match_status1` AS `match_status1`,`m`.`match_time` AS `match_time`,
 `od`.`home_odds` AS `home_odds`,`od`.`tie_odds` AS `tie_odds`,`od`.`away_odds` AS `away_odds`,
 `odEu`.`home_odds` AS `home_odds2`,`odEu`.`tie_odds` AS `tie_odds2`,`odEu`.`away_odds` AS `away_odds2`,

mt.video ,mt.animation,kdbf.league_ranking league_ranking_kedui,
kdbf.half_court_score half_court_score_kedui,kdbf.red_card red_card_kedui,kdbf.yellow_card yellow_card_kedui,
kdbf.corner_kick corner_kick_kedui,kdbf.point_score point_score_kedui,
zhudui.logo, kedui.logo logo_kedui,
env.pressure ,env.temperature ,env.wind,env. humidity,env. weather,
'https://live.iixxix.cn/player.php?stream_name=sd-2-3553294' play_url
  from ((`dev_kok_sport`.`foot_bifen` `m`
  left join (select  * from  football_odds_t `o` where   odds_type = 1 ) `od` on((`m`.`match_id` = `od`.`match_id`)))
  left join (select * from `dev_kok_sport`.`football_odds_t` `o` where ( (`o`.`odds_type` = 3)) ) `odEu` on((`m`.`match_id` = `odEu`.`match_id`)))
left join football_match_t mt on m.match_id=mt.id

left join ( select * from football_score_t kd where kd.match_id=match_id and team_type=2 limit 1) kdbf
on kdbf.match_id=m.match_id

left join ( select * from football_team_t   ) zhudui  on zhudui.id=m.team_id
left join ( select * from football_team_t  ) kedui on kedui.id=m.team_id
left join ( select * from  football_environment_t ) env on env.id=m.match_id