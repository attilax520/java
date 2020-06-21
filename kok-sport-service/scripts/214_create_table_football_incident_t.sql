-- 创建比赛发生事件表
drop table if exists football_incident_t;
CREATE TABLE football_incident_t(
    id BIGINT(19) NOT NULL   COMMENT '主键id' ,
    match_id BIGINT(19)    COMMENT '比赛id' ,
    type TINYINT(3) NOT NULL   COMMENT '事件类型，详见状态码->足球技术统计' ,
    position INT(10) NOT NULL   COMMENT '事件发生方,0-中立 1,主队 2,客队' ,
    time BIGINT(19)    COMMENT '时间(分钟)' ,
    player_id BIGINT(19)    COMMENT '事件相关球员id,可能为空' ,
    player_name VARCHAR(100)    COMMENT '事件相关球员名称,可能为空' ,
    assist1_id BIGINT(19)    COMMENT '进球时,助攻球员1 id,可能为空' ,
    assist1_name VARCHAR(100)    COMMENT '进球时,助攻球员1名称,可能为空' ,
    in_player_id BIGINT(19)    COMMENT '换人时,换上球员id,可能为空' ,
    in_player_name VARCHAR(100)    COMMENT '换人时,换上球员名称,可能为空' ,
    out_player_id BIGINT(19)    COMMENT '换人时,换下球员id ,可能为空' ,
    out_player_name VARCHAR(100)    COMMENT '换人时,换下球员名称,可能为空' ,
    home_score INT(10)    COMMENT '进球时,主队比分,可能没有' ,
    away_score INT(10)    COMMENT '进球时,客队比分,可能没有' ,
    delete_flag CHAR(1) NOT NULL   COMMENT '是否删除(1.已删除0.未删除)' ,
    create_time DATETIME NOT NULL   COMMENT '创建时间' ,
    PRIMARY KEY (id)
) COMMENT = '比赛发生事件表';