-- 创建足球比赛环境表
drop table if exists football_environment_t;
CREATE TABLE football_environment_t(
    id BIGINT(19) NOT NULL   COMMENT '比赛id' ,
    match_id BIGINT(19) NOT NULL   COMMENT '比赛id' ,
    pressure VARCHAR(20) NOT NULL   COMMENT '气压' ,
    temperature VARCHAR(20) NOT NULL   COMMENT '温度' ,
    wind VARCHAR(20) NOT NULL   COMMENT '风速' ,
    humidity VARCHAR(20) NOT NULL   COMMENT '湿度' ,
    weather_id BIGINT(19) NOT NULL   COMMENT '天气id' ,
    weather VARCHAR(20) NOT NULL   COMMENT '天气' ,
    weather_image VARCHAR(200) NOT NULL   COMMENT '天气logo' ,
    create_time DATETIME NOT NULL   COMMENT '创建时间' ,
    delete_flag CHAR(1) NOT NULL   COMMENT '是否删除(1.已删除0.未删除)' ,
    PRIMARY KEY (id)
) COMMENT = '足球比赛环境表';