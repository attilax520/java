DELIMITER $$

DROP FUNCTION IF EXISTS `scoreOf_league_ranking` $$
CREATE DEFINER=`root`@`%` FUNCTION `scoreOf_league_ranking`(matchid int,teamid int,teamtype int) RETURNS int(11)
BEGIN

select league_ranking into @sc from football_score_t where team_id=teamid and match_id=matchid and team_type=teamtype limit 1;
return @sc;

END $$

DELIMITER ;