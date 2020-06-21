drop table if exists basketball_match_t;
CREATE TABLE basketball_match_t(
    id BIGINT(19) NOT NULL   COMMENT '比赛id' ,
    match_event_id BIGINT(19)    COMMENT '赛事id' ,
    season_id BIGINT(19)    COMMENT '赛季id' ,
    stages_id BIGINT(19)    COMMENT '队段id' ,
    home_id BIGINT(19)    COMMENT '主队id' ,
    away_id BIGINT(19)    COMMENT '客队id' ,
    match_type TINYINT(3)    COMMENT '比赛类型 1-常规赛 2-季后赛 3-季前赛 4-全明星 5-杯赛' ,
    total_sections INT(10)    COMMENT '比赛总节数' ,
    match_status TINYINT(3)    COMMENT '比赛状态' ,
    match_time BIGINT(19)    COMMENT '比赛时间' ,
    current_total_seconds INT(10)    COMMENT '当前总秒钟数，显示转换成 mm:ss' ,
    match_detail VARCHAR(100)    COMMENT '比赛详细说明' ,
    compatible VARCHAR(100)    COMMENT '兼容,请忽略' ,
    animation INT(10)    COMMENT '是否有动画' ,
    intelligence INT(10)    COMMENT '是否有情报' ,
    home_data VARCHAR(150)    COMMENT '主队数据' ,
    away_data VARCHAR(150)    COMMENT '客队数据' ,
    create_time DATETIME    COMMENT '创建时间' ,
    delete_flag CHAR(1)    COMMENT '是否删除(1.已删除0.未删除)' ,
    PRIMARY KEY (id)
) COMMENT = '篮球比赛信息表';