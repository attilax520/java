-- 创建比赛球员事件表
drop table if exists football_player_incident_t;
CREATE TABLE football_player_incident_t(
    id BIGINT(19) NOT NULL   COMMENT '主键id' ,
    match_id BIGINT(19)    COMMENT '比赛id' ,
    type TINYINT(3) NOT NULL   COMMENT '事件类型' ,
    time BIGINT(19)    COMMENT '事件发生时间（含加时时间）' ,
    minute BIGINT(19)    COMMENT '事件发生时比赛分钟数' ,
    addtime BIGINT(19)    COMMENT '加时时间' ,
    belong INT(10) NOT NULL   COMMENT '发生方,0-中立 1,主队 2,客队' ,
    text VARCHAR(300)    COMMENT '文本描述' ,
    player_id BIGINT(19)    COMMENT '事件相关球员id,可能为空' ,
    player_name VARCHAR(120)    COMMENT '球员名字' ,
    player_type VARCHAR(20)    COMMENT '球员类型(player-相关球员 assist1-助攻球员1 assist2-助攻球员2 in_player 换上球员 out_player-换下球员)' ,
    home_score INT(10)    COMMENT '进球时,主队比分,可能没有' ,
    away_score INT(10)    COMMENT '进球时,客队比分,可能没有' ,
    delete_flag CHAR(1) NOT NULL   COMMENT '是否删除(1.已删除0.未删除)' ,
    create_time DATETIME NOT NULL   COMMENT '创建时间' ,
    PRIMARY KEY (id)
) COMMENT = '足球比赛球员事件表';