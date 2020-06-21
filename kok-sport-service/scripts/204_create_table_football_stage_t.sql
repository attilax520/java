-- 创建足球比赛阶段表
drop table if exists football_stage_t;
CREATE TABLE football_stage_t(
    id BIGINT(19) NOT NULL   COMMENT '阶段id' ,
    mode INT(10)    COMMENT '比赛方式, 1-积分 2-淘汰' ,
    group_count INT(10)    COMMENT '总分组数,0表示没有分组' ,
    round_count INT(10)    COMMENT '总轮数,0表示没有轮次' ,
    name_zh VARCHAR(100)    COMMENT '中文' ,
    name_zht VARCHAR(100)    COMMENT '粤语' ,
    name_en VARCHAR(100)    COMMENT '英文' ,
    create_time DATETIME NOT NULL   COMMENT '创建时间' ,
    delete_flag CHAR(1) NOT NULL   COMMENT '是否删除(1.已删除0.未删除)' ,
    PRIMARY KEY (id)
) COMMENT = '足球比赛阶段表';