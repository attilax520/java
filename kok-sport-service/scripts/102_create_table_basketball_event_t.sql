drop table if exists basketball_event_t;
CREATE TABLE basketball_event_t(
    id BIGINT(19) NOT NULL   COMMENT '赛事id' ,
    area_id BIGINT(19) NOT NULL   COMMENT '地区id' ,
    country_id BIGINT(19) NOT NULL   COMMENT '国家id' ,
    match_type TINYINT(3) NOT NULL   COMMENT '赛事类型 1-常规赛 2-季后赛 3-季前赛 4-全明星 5-杯赛' ,
    name_zh VARCHAR(100)    COMMENT '中文' ,
    short_name_zh VARCHAR(100)    COMMENT '中文缩写' ,
    name_zht VARCHAR(100)    COMMENT '粤语' ,
    short_name_zht VARCHAR(100)    COMMENT '粤语缩写' ,
    name_en VARCHAR(100)    COMMENT '英文' ,
    short_name_en VARCHAR(100)    COMMENT '英文缩写' ,
    match_logo VARCHAR(100)    COMMENT '赛事logo' ,
    delete_flag CHAR(1) NOT NULL   COMMENT '是否删除(1.已删除0.未删除)' ,
    create_time DATETIME NOT NULL   COMMENT '创建时间' ,
    PRIMARY KEY (id)
) COMMENT = '篮球赛事表';