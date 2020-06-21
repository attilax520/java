drop table if exists football_team_t;
CREATE TABLE football_team_t(
    id BIGINT(19) NOT NULL   COMMENT '球队id' ,
    name_zh VARCHAR(100)    COMMENT '中文' ,
    short_name_zh VARCHAR(100)    COMMENT '中文缩写' ,
    name_zht VARCHAR(100)    COMMENT '粤语' ,
    short_name_zht VARCHAR(100)    COMMENT '粤语缩写' ,
    name_en VARCHAR(100)    COMMENT '英文' ,
    short_name_en VARCHAR(100)    COMMENT '英文' ,
    logo VARCHAR(100)    COMMENT '球队logo，可能为空' ,
    found VARCHAR(20)    COMMENT '球队成立日期' ,
    web_site VARCHAR(200)    COMMENT '球队官网' ,
    national INT(10)    COMMENT '是否国家队 1,是，0否' ,
    country_logo VARCHAR(100)    COMMENT '国家队logo，国家队才有' ,
    create_time DATETIME NOT NULL   COMMENT '创建时间' ,
    delete_flag CHAR(1) NOT NULL   COMMENT '是否删除(1.已删除0.未删除)' ,
    PRIMARY KEY (id)
) COMMENT = '足球球队表';