DROP VIEW
IF EXISTS `foot_match_exV2`;
        CREATE
        OR
        REPLACE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `foot_match_exV2` AS
        SELECT
                `m`.`id`             AS `id`            ,
                `m`.`match_event_id` AS `match_event_id`,
                `e`.`name_zh`        AS `saishi`        ,
                `m`.`match_status`   AS `match_status`  ,
                `m`.`match_time`     AS `match_time`    ,
                `m`.`tee_time`       AS `tee_time`      ,
                `m`.`home_id`        AS `home_id`       ,
                `m`.`away_id`        AS `away_id`       ,
                `m`.`match_detail`   AS `match_detail`  ,
                `m`.`which_round`    AS `which_round`   ,
                `m`.`neutral_site`   AS `neutral_site`  ,
                `m`.`animation`      AS `animation`     ,
                `m`.`intelligence`   AS `intelligence`  ,
                `m`.`squad`          AS `squad`         ,
                `m`.`video`          AS `video`         ,
                `m`.`create_time`    AS `create_time`   ,
                `m`.`delete_flag`    AS `delete_flag`   ,
                `e`.`name_zh`        AS `name_zh`       ,
                `m`.`fav`            AS `fav`
        FROM
                (`football_match_t` `m`
        JOIN
                `football_event_t` `e`)
        WHERE
                (
                        `m`.`match_event_id` = `e`.`id`);