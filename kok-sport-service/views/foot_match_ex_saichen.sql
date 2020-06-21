DROP VIEW
IF EXISTS `foot_match_ex_saichen`;
        CREATE
        OR
        REPLACE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `foot_match_ex_saichen` AS
        SELECT
                *
        FROM
                `dev_kok_sport`.`foot_match_v_all`
        WHERE
                ((
                                 `match_status` = 1)
                OR      (
                                `match_status` BETWEEN 9 AND     13));