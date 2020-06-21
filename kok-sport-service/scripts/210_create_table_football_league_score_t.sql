-- 创建足球联赛积分表
drop table if exists football_league_score_t;
CREATE TABLE football_league_score_t(
    id BIGINT(19) NOT NULL   COMMENT '主键id' ,
    match_id BIGINT(19) NOT NULL   COMMENT '比赛id' ,
    season_id BIGINT(19) NOT NULL   COMMENT '赛季id' ,
    stage_id BIGINT(19) NOT NULL   COMMENT '阶段id' ,
    season VARCHAR(100) NOT NULL   COMMENT '赛季名称' ,
    event_name VARCHAR(100) NOT NULL   COMMENT '联赛名称' ,
    team_type TINYINT(3) NOT NULL   COMMENT '1,主队; 2,客队' ,
    data_type VARCHAR(20)    COMMENT '数据类型(all/home/away)' ,
    position INT(10)    COMMENT '位置' ,
    pts INT(10)    COMMENT '积分' ,
    played INT(10)    COMMENT '已赛场数' ,
    won INT(10)    COMMENT '胜场数' ,
    drawn INT(10)    COMMENT '平场数' ,
    lost INT(10)    COMMENT '失败场数' ,
    goals INT(10)    COMMENT '进球数' ,
    away_goals INT(10)    COMMENT '客场进球数' ,
    against INT(10)    COMMENT '失球数' ,
    diff INT(10)    COMMENT '净胜球数' ,
    team_id BIGINT(19)    COMMENT '球队id' ,
    create_time DATETIME NOT NULL   COMMENT '创建时间' ,
    delete_flag CHAR(1) NOT NULL   COMMENT '是否删除(1.已删除0.未删除)' ,
    PRIMARY KEY (id)
) COMMENT = '足球联赛积分表';