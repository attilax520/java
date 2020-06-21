
-- 创建篮球比赛文字直播表
drop table if exists basketball_tlive_t;
CREATE TABLE basketball_tlive_t(
    id BIGINT(19) NOT NULL   COMMENT '主键id' ,
    match_id BIGINT(19) NOT NULL   COMMENT '比赛id' ,
    section INT(10) NOT NULL   COMMENT '所属小节' ,
    time BIGINT(19) NOT NULL   COMMENT '事件时间' ,
    data VARCHAR(200)    COMMENT '文字直播数据' ,
    create_time DATETIME NOT NULL   COMMENT '创建时间' ,
    delete_flag CHAR(1) NOT NULL   COMMENT '是否删除(1.已删除0.未删除)' ,
    PRIMARY KEY (id)
) COMMENT = '篮球比赛文字直播表';