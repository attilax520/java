-- 创建足球比赛伤停情况表
drop table if exists football_injury_t;
CREATE TABLE football_injury_t(
    id BIGINT(19) NOT NULL   COMMENT '主键id' ,
    match_id BIGINT(19) NOT NULL   COMMENT '比赛id' ,
    team_type TINYINT(3) NOT NULL   COMMENT '1:主队；2:客队' ,
    player_id BIGINT(19) NOT NULL   COMMENT '球员id' ,
    player_name VARCHAR(100)    COMMENT '球员名称' ,
    logo VARCHAR(150)    COMMENT '球员logo' ,
    position VARCHAR(100)    COMMENT '球员位置' ,
    reason VARCHAR(100)    COMMENT '伤停原因' ,
    missed_matches INT(10) NOT NULL   COMMENT '影响场次' ,
    start_time BIGINT(19) NOT NULL   COMMENT '开始时间' ,
    end_time BIGINT(19) NOT NULL   COMMENT '归队时间' ,
    type TINYINT(3) NOT NULL   COMMENT '0-未知 1-受伤 2-停赛' ,
    create_time DATETIME NOT NULL   COMMENT '创建时间' ,
    delete_flag CHAR(1) NOT NULL   COMMENT '是否删除(1.已删除0.未删除)' ,
    PRIMARY KEY (id)
) COMMENT = '足球比赛伤停情况表';