drop table if exists match_season_t;
CREATE TABLE match_season_t(
    id BIGINT(19) NOT NULL   COMMENT '赛季id' ,
    sport_type INT(10) NOT NULL   COMMENT '1-足球，2-篮球' ,
    season VARCHAR(20) NOT NULL   COMMENT '赛季' ,
    season_year INT(10) NOT NULL   COMMENT '赛季年份' ,
    name_zh VARCHAR(100)    COMMENT '中文' ,
    short_name_zh VARCHAR(100)    COMMENT '中文缩写' ,
    name_zht VARCHAR(100)    COMMENT '粤语' ,
    short_name_zht VARCHAR(100)    COMMENT '粤语缩写' ,
    name_en VARCHAR(100)    COMMENT '英文' ,
    short_name_en VARCHAR(100)    COMMENT '英文缩写' ,
    delete_flag CHAR(1) NOT NULL   COMMENT '是否删除(1.已删除0.未删除)' ,
    create_time DATETIME NOT NULL   COMMENT '创建时间' ,
    PRIMARY KEY (id)
) COMMENT = '赛季信息表';