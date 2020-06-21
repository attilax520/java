-- 创建足球进球分布表
drop table if exists football_distribution_t;
CREATE TABLE football_distribution_t(
    id BIGINT(19) NOT NULL   COMMENT '主键id' ,
    match_id BIGINT(19)    COMMENT '比赛id' ,
    team_id BIGINT(19) NOT NULL   COMMENT '团队id' ,
    team_type TINYINT(3) NOT NULL   COMMENT '1:主队1,  2:客队' ,
    site_type VARCHAR(20)    COMMENT '场次类型(all/home/away)' ,
    matches INT(10) NOT NULL   COMMENT '比赛场次' ,
    type TINYINT(3) NOT NULL   COMMENT '1,进球|scored;2,失球|conceded' ,
    ball_number INT(10)    COMMENT '球个数' ,
    percentage DECIMAL(32,10)    COMMENT '百分比' ,
    start_time INT(10)    COMMENT '开始时间' ,
    end_time INT(10)    COMMENT '结束时间' ,
    delete_flag CHAR(1) NOT NULL   COMMENT '是否删除(1.已删除0.未删除)' ,
    create_time DATETIME NOT NULL   COMMENT '创建时间' ,
    PRIMARY KEY (id)
) COMMENT = '足球进球分布表';