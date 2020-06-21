
-- 创建篮球盘口指数表
drop table if exists basketball_odds_t;
CREATE TABLE basketball_odds_t(
    id BIGINT(19) NOT NULL   COMMENT '主键id' ,
    company_id BIGINT(19)    COMMENT '公司id' ,
    matche_id BIGINT(19)    COMMENT '比赛id' ,
    odds_type TINYINT(3)    COMMENT '指数类型:1,asia-亚盘; 2,bs-大小球;3,eu-欧赔' ,
    change_time BIGINT(19)    COMMENT '变化时间' ,
    happen_time VARCHAR(20)    COMMENT '比赛进行时间' ,
    match_status TINYINT(3)    COMMENT '比赛状态' ,
    home_odds DECIMAL(32,10)    COMMENT '主赔率' ,
    tie_odds DECIMAL(32,10)    COMMENT '盘口/和局赔率' ,
    away_odds DECIMAL(32,10)    COMMENT '客赔率' ,
    lock_flag TINYINT(3)    COMMENT '是否封盘 0-否,1-是' ,
    real_time_score VARCHAR(20)    COMMENT '实时比分' ,
    create_time DATETIME    COMMENT '创建时间' ,
    delete_flag CHAR(1)    COMMENT '是否删除(1.已删除0.未删除)' ,
    PRIMARY KEY (id)
) COMMENT = '篮球盘口指数表';