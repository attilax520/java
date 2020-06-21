DROP VIEW
IF EXISTS `football_tlive_v`;
        CREATE
        OR
        REPLACE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `football_tlive_v` AS
        SELECT
                `football_tlive_t`.`id`                                                           AS `id`         ,
                `football_tlive_t`.`match_id`                                                     AS `match_id`   ,
                `football_tlive_t`.`main`                                                         AS `main`       ,
                `football_tlive_t`.`data`                                                         AS `data`       ,
                `football_tlive_t`.`position`                                                     AS `position`   ,
                `football_tlive_t`.`type`                                                         AS `type`       ,
                `football_tlive_t`.`time`                                                         AS `time`       ,
                `football_tlive_t`.`create_time`                                                  AS `create_time`,
                `football_tlive_t`.`delete_flag`                                                  AS `delete_flag`,
                '萨尔茨堡红牛'                                                                          AS `zhudui`     ,
                '马特斯堡'                                                                            AS `kedui`      ,
                'https://cdn.sportnanoapi.com/football/team/d8ddbbdf082b5c469b4e1f9e998690dd.png' AS `zhudui_logo`,
                'https://cdn.sportnanoapi.com/football/team/1a2f6e7b3750b444d8e39122eada179f.png' AS `kedui_logo`,
                1 AS `red_card`,2 AS `yellow_card`,3 AS `corner_kick`,4 AS `red_card_kedui`,5 AS `yellow_card_kedui`,
                6 AS `corner_kick_kedui`,5 AS `zhudui_kongqiulv`,6 AS `kedui_kongqiulv`,3 AS `zhudui_shemen`,
                7 AS `kedui_shemen`,2 AS `zhudui_shezhen`,8 AS `kedui_shezhen`,60 AS `zhudui_jingong`,
                40 AS `kedui_jingong`,40 AS `zhudui_weixianjingon`,80 AS `kedui_weixianjingon`,
m.saishi,3 zhudiu_bifen,9 kedui_bifen
        FROM
                `football_tlive_t`
       left join foot_match_exV2 m on m.id= football_tlive_t.match_id
