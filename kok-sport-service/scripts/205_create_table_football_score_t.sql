drop table if exists football_score_t;
CREATE TABLE football_score_t(
    id BIGINT(19) NOT NULL   COMMENT '主键id' ,
    match_id BIGINT(19) NOT NULL   COMMENT '比赛id' ,
    team_id BIGINT(19) NOT NULL   COMMENT '球队id' ,
    team_type TINYINT(3) NOT NULL   COMMENT '1:主队1,  2:客队' ,
    league_ranking INT(10) NOT NULL   COMMENT '联赛排名，无排名为空' ,
    regular_time_score INT(10) NOT NULL   COMMENT '常规时间比分(含补时)' ,
    half_court_score INT(10) NOT NULL   COMMENT '半场比分(中场前为0)' ,
    score INT(10) NOT NULL   COMMENT '比分' ,
    red_card INT(10) NOT NULL   COMMENT '红牌' ,
    yellow_card INT(10) NOT NULL   COMMENT '黄牌' ,
    corner_kick INT(10) NOT NULL   COMMENT '角球，-1表示没有角球数据' ,
    overtime_score INT(10)    COMMENT '加时比分(含常规时间比分),加时赛才有' ,
    point_score INT(10)    COMMENT '点球大战比分(不含常规时间比分),点球大战才有' ,
    remark VARCHAR(500) NOT NULL   COMMENT '备注信息' ,
    create_time DATETIME NOT NULL   COMMENT '创建时间' ,
    delete_flag CHAR(1) NOT NULL   COMMENT '是否删除(1.已删除0.未删除)' ,
    PRIMARY KEY (id)
) COMMENT = '足球比赛得分表';