-- 创建足球比赛阵型表
drop table if exists football_formation_t;
CREATE TABLE football_formation_t(
    id BIGINT(19) NOT NULL   COMMENT '主键id' ,
    match_id BIGINT(19) NOT NULL   COMMENT '比赛id' ,
    confirmed TINYINT(3)    COMMENT '是否正式阵容(0/1)' ,
    home_formation VARCHAR(20) NOT NULL   COMMENT '主队阵型' ,
    away_formation VARCHAR(20) NOT NULL   COMMENT '客队阵型' ,
    home_id BIGINT(19) NOT NULL   COMMENT '主队id' ,
    away_id BIGINT(19) NOT NULL   COMMENT '客队id' ,
    create_time DATETIME NOT NULL   COMMENT '创建时间' ,
    delete_flag CHAR(1) NOT NULL   COMMENT '是否删除(1.已删除0.未删除)' ,
    PRIMARY KEY (id)
) COMMENT = '足球比赛阵型表';