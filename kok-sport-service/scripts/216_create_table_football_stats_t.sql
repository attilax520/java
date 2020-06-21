-- 创建足球比赛统计表
drop table if exists football_stats_t;
CREATE TABLE football_stats_t(
    id BIGINT(19) NOT NULL   COMMENT '主键id' ,
    match_id BIGINT(19) NOT NULL   COMMENT '比赛id' ,
    type TINYINT(3) NOT NULL   COMMENT '类型，详见状态码->足球技术统计' ,
    home INT(10) NOT NULL   COMMENT '主队值' ,
    away INT(10) NOT NULL   COMMENT '客队值' ,
    create_time DATETIME NOT NULL   COMMENT '创建时间' ,
    delete_flag CHAR(1) NOT NULL   COMMENT '是否删除(1.已删除0.未删除)' ,
    PRIMARY KEY (id)
) COMMENT = '足球比赛统计表';