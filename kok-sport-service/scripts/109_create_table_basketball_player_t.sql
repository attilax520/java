
-- 创建篮球比赛阵容表
drop table if exists basketball_player_t;
CREATE TABLE basketball_player_t(
    id BIGINT(19) NOT NULL   COMMENT '主键id' ,
    match_id BIGINT(19) NOT NULL   COMMENT '比赛ID' ,
    team_type INT(10)    COMMENT '1-主队 2-客队' ,
    player_id BIGINT(19)    COMMENT '球员id' ,
    player_name_zh VARCHAR(32)    COMMENT '球员中文名称' ,
    player_name_zht VARCHAR(32)    COMMENT '球员粤语名称' ,
    player_name_en VARCHAR(32)    COMMENT '球员英语名称' ,
    player_no VARCHAR(32)    COMMENT '球员球衣号' ,
    player_header VARCHAR(300)    COMMENT '球员头像' ,
    player_data VARCHAR(300)    COMMENT '球员数据' ,
    create_time DATETIME    COMMENT '创建时间' ,
    delete_flag CHAR(1)    COMMENT '是否删除(1.已删除0.未删除)' ,
    PRIMARY KEY (id)
) COMMENT = '篮球比赛阵容表 ';;