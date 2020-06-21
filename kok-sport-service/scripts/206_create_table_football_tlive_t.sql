drop table if exists football_tlive_t;
CREATE TABLE football_tlive_t(
    id BIGINT(19) NOT NULL   COMMENT '主键id' ,
    match_id BIGINT(19) NOT NULL   COMMENT '比赛id' ,
    main TINYINT(3) NOT NULL   COMMENT '是否重要事件,1-是,0-否' ,
    data VARCHAR(200) NOT NULL   COMMENT '直播数据' ,
    position INT(10) NOT NULL   COMMENT '事件发生方,0-中立 1,主队 2,客队' ,
    type TINYINT(3) NOT NULL   COMMENT '事件类型' ,
    time BIGINT(19) NOT NULL   COMMENT '事件时间' ,
    create_time DATETIME NOT NULL   COMMENT '创建时间' ,
    delete_flag CHAR(1) NOT NULL   COMMENT '是否删除(1.已删除0.未删除)' ,
    PRIMARY KEY (id)
) COMMENT = '足球比赛文字直播表';