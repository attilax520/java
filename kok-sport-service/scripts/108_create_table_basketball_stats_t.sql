
-- 创建篮球比赛统计表
drop table if exists basketball_stats_t;
CREATE TABLE basketball_stats_t(
    id BIGINT(19) NOT NULL   COMMENT '主键id' ,
    match_id BIGINT(19) NOT NULL   COMMENT '比赛id' ,
    stats_type TINYINT(3) NOT NULL   COMMENT '统计类型' ,
    home_data INT(10)    COMMENT '主队数值' ,
    away_data INT(10)    COMMENT '客队队数值' ,
    create_time DATETIME NOT NULL   COMMENT '创建时间' ,
    delete_flag CHAR(1) NOT NULL   COMMENT '是否删除(1.已删除0.未删除)' ,
    PRIMARY KEY (id)
) COMMENT = '篮球比赛统计表';