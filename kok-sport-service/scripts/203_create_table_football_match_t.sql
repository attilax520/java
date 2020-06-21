drop table if exists football_match_t;
CREATE TABLE football_match_t(
    id BIGINT(19) NOT NULL   COMMENT '比赛id' ,
    match_event_id BIGINT(19) NOT NULL   COMMENT '赛事id' ,
    match_status TINYINT(3) NOT NULL   COMMENT '足球状态码' ,
    match_time BIGINT(19) NOT NULL   COMMENT '比赛时间' ,
    tee_time BIGINT(19) NOT NULL   COMMENT '开球时间，可能是上/下半场开球时间' ,
    home_id BIGINT(19) NOT NULL   COMMENT '主队id' ,
    away_id BIGINT(19) NOT NULL   COMMENT '客队id' ,
    match_detail VARCHAR(100) NOT NULL   COMMENT '比赛详细说明，包括加时、点球、中立场、首回合、40分钟赛事 等' ,
    which_round INT(10)    COMMENT '第几轮' ,
    neutral_site INT(10) NOT NULL   COMMENT '是否中立场，1-是 0-否' ,
    animation INT(10) NOT NULL   COMMENT '是否有动画，未购买客户请忽略（1有，0,没有)' ,
    intelligence INT(10) NOT NULL   COMMENT '是否有情报，未购买客户请忽略（1有，0,没有)' ,
    squad INT(10) NOT NULL   COMMENT '是否有阵容，未购买客户请忽略（1有，0,没有)' ,
    video INT(10) NOT NULL   COMMENT '是否有视频，未购买客户请忽略（1有，0,没有)' ,
    create_time DATETIME NOT NULL   COMMENT '创建时间' ,
    delete_flag CHAR(1) NOT NULL   COMMENT '是否删除(1.已删除0.未删除)' ,
    PRIMARY KEY (id)
) COMMENT = '足球比赛表';