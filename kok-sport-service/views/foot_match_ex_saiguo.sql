DROP VIEW
IF EXISTS `foot_match_ex_saiguo`;
        CREATE
        OR
        REPLACE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `foot_match_ex_saiguo` AS
        SELECT
                 *,date_from_unixtime(match_time)  match_time_date
        FROM
               foot_match_v_all
        WHERE
                (
                         `match_status` =8);