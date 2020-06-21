DELIMITER $$

DROP FUNCTION IF EXISTS `scoreOf_half_court_score` $$
CREATE DEFINER=`root`@`%` FUNCTION `scoreOf_half_court_score`(matchid int,teamid int,teamtype int) RETURNS int(11)
BEGIN

select half_court_score into @sc from football_score_t where team_id=teamid and match_id=matchid and team_type=teamtype limit 1;
return @sc;

END $$

DELIMITER ;