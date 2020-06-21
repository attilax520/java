CREATE VIEW `dev_kok_sport`.`foot_odds_tongji` AS
  select

'max' AS `type`, 0.33 `home_odds`, 0.33 `tie_odds`,0.33 AS `away_odds`,
0.92 AS `home_odds_chupan`,0.5 AS `tie_odds_chupan`,0.88 AS away_odds_chupan

union

select

'min' AS `type`, 0.33 `home_odds`, 0.33 `tie_odds`,0.33 AS `away_odds`,
0.92 AS `home_odds_chupan`,0.5 AS `tie_odds_chupan`,0.88 AS away_odds_chupan

union
select

'avg' AS `type`, 0.33 `home_odds`, 0.33 `tie_odds`,0.33 AS `away_odds`,
0.92 AS `home_odds_chupan`,0.5 AS `tie_odds_chupan`,0.88 AS away_odds_chupan