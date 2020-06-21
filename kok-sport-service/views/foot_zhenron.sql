DROP VIEW
IF EXISTS `foot_zhenron`;
        CREATE
        OR
        REPLACE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `foot_zhenron` AS
        SELECT
               *,
                '萨尔茨堡红牛'                                                                          AS `zhudui`        ,
                '马特斯堡'                                                                            AS `kedui`         ,
                'https://cdn.sportnanoapi.com/football/team/d8ddbbdf082b5c469b4e1f9e998690dd.png' AS `zhudui_logo`   ,
                'https://cdn.sportnanoapi.com/football/team/1a2f6e7b3750b444d8e39122eada179f.png' AS `kedui_logo`
        FROM
                `football_formation_t`;