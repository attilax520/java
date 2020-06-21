
-- 创建篮球球员表
drop table if exists basketball_team_player_t;
CREATE TABLE basketball_team_player_t(
    id BIGINT(19) NOT NULL   COMMENT '球员id' ,
    team_id BIGINT(19)    COMMENT '球队id' ,
    name_zh VARCHAR(100)    COMMENT '球员中文名称' ,
    name_zht VARCHAR(100)    COMMENT '球员粤语名称' ,
    name_en VARCHAR(100)    COMMENT '球员英文名称' ,
    ball_number INT(10)    COMMENT '球衣号' ,
    logo VARCHAR(100)    COMMENT 'logo 前缀：https://cdn.sportnanoapi.com/basketball/player/' ,
    create_time DATETIME NOT NULL   COMMENT '创建时间' ,
    delete_flag CHAR(1) NOT NULL   COMMENT '是否删除(1.已删除0.未删除)' ,
    PRIMARY KEY (id)
) COMMENT = '篮球球员表';