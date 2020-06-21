-- 创建足球球队球员表
drop table if exists football_team_player_t;
CREATE TABLE football_team_player_t(
    id BIGINT(19) NOT NULL   COMMENT '球员id' ,
    match_id BIGINT(19) NOT NULL   COMMENT '比赛id' ,
    team_id BIGINT(19)    COMMENT '球队id' ,
    name_zh VARCHAR(100)    COMMENT '球员中文名称' ,
    name_zht VARCHAR(100)    COMMENT '球员粤语名称' ,
    name_en VARCHAR(100)    COMMENT '球员英文名称' ,
    shirt_number INT(10)    COMMENT '球衣号' ,
    logo VARCHAR(100)    COMMENT 'logo 前缀：https://cdn.sportnanoapi.com/basketball/player/' ,
    position VARCHAR(100)    COMMENT '球员位置,F前锋 M中锋 D后卫 G守门员,其他为未知' ,
    rating VARCHAR(100)    COMMENT '评分' ,
    delete_flag CHAR(1) NOT NULL   COMMENT '是否删除(1.已删除0.未删除)' ,
    create_time DATETIME NOT NULL   COMMENT '创建时间' ,
    PRIMARY KEY (id)
) COMMENT = '足球球队球员表';