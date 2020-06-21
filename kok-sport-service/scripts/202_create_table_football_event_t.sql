drop table if exists football_event_t;
CREATE TABLE football_event_t(
    id BIGINT(19) NOT NULL   COMMENT '赛事id' ,
    season_id BIGINT(19) NOT NULL   COMMENT '赛季id' ,
    area_id BIGINT(19) NOT NULL   COMMENT '地区id' ,
    country_id BIGINT(19) NOT NULL   COMMENT '国家id' ,
    type TINYINT(3) NOT NULL   COMMENT '赛事类型 0-未知 1-联赛 2-杯赛 3-友谊赛' ,
    level TINYINT(3) NOT NULL   COMMENT '忽略，兼容使用' ,
    name_zh VARCHAR(100)    COMMENT '中文' ,
    short_name_zh VARCHAR(100)    COMMENT '中文缩写' ,
    name_zht VARCHAR(100)    COMMENT '粤语' ,
    short_name_zht VARCHAR(100)    COMMENT '粤语缩写' ,
    name_en VARCHAR(100)    COMMENT '英文' ,
    short_name_en VARCHAR(100)    COMMENT '英文缩写' ,
    match_logo VARCHAR(100)    COMMENT '赛事logo，前缀：http://cdn.sportnanoapi.com/football/competition/' ,
    delete_flag CHAR(1) NOT NULL   COMMENT '是否删除(1.已删除0.未删除)' ,
    create_time DATETIME NOT NULL   COMMENT '创建时间' ,
    PRIMARY KEY (id)
) COMMENT = '足球赛事表';