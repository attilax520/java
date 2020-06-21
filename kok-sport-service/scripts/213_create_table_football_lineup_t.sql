-- 创建足球比赛阵容表
drop table if exists football_lineup_t;
CREATE TABLE football_lineup_t(
    id BIGINT UNSIGNED(20) NOT NULL   COMMENT '比赛id' ,
    match_id BIGINT(19) NOT NULL   COMMENT '比赛id' ,
    team_id VARCHAR(20) NOT NULL   COMMENT '球队id' ,
    team_type BIGINT(19) NOT NULL   COMMENT '球队类型(home/away)' ,
    player_id BIGINT(19) NOT NULL   COMMENT '球员id' ,
    first TINYINT(3) NOT NULL   COMMENT '是否首发，1-是 0-否' ,
    position VARCHAR(20)    COMMENT '球员位置' ,
    position_x VARCHAR(20)    COMMENT '阵容x坐标 总共100' ,
    position_y VARCHAR(20)    COMMENT '阵容y坐标 总共100' ,
    rating VARCHAR(20)    COMMENT '评分' ,
    create_time DATETIME NOT NULL   COMMENT '创建时间' ,
    delete_flag CHAR(1) NOT NULL   COMMENT '是否删除(1.已删除0.未删除)' ,
    PRIMARY KEY (id)
) COMMENT = '足球比赛阵容表';