DROP VIEW IF EXISTS `foot_detail_data_v`;
CREATE OR  REPLACE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `foot_detail_data_v` AS

 select * ,
555 testxx,
 `teamname_byid`(`football_match_t`.`home_id`) AS `zhudui`,
 `teamname_byid`(`football_match_t`.`away_id`) AS `kedui`,
 teamlogo(`football_match_t`.`home_id`) AS `zhudui_logo`,
 teamlogo(`football_match_t`.`away_id`) AS `kedui_logo`,
 `saishiName`(`football_match_t`.`match_event_id`) AS `saishiName`,-(0.5) AS `panlu`,
1   as `zhudui_score`,6 as `kedui_score`,
 `score_byteamidMatchid`(`football_match_t`.`id`,`football_match_t`.`home_id`,1) AS `zhudui_score22`,
 `score_byteamidMatchid`(`football_match_t`.`id`,`football_match_t`.`away_id`,2) AS `kedui_score22`,

1 half_court_score_zhudui,2 half_court_score_kedui

 from `football_match_t`;