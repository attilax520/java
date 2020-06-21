drop table if exists match_stream_t;
CREATE TABLE match_stream_t(
  id BIGINT(19) NOT NULL   COMMENT '视频源id' ,
  match_id BIGINT(19)    COMMENT '比赛id' ,
  sport_type INT(10) NOT NULL   COMMENT '1-足球，2-篮球' ,
  match_time BIGINT(19) NOT NULL   COMMENT '比赛时间' ,
  comp VARCHAR(250) NOT NULL   COMMENT '赛事（联赛名称）' ,
  home VARCHAR(100) NOT NULL   COMMENT '主队' ,
  away VARCHAR(100) NOT NULL   COMMENT '客队' ,
  match_status TINYINT(3) NOT NULL   COMMENT '比赛状态' ,
  live_status INT(10) NOT NULL   COMMENT '视频状态，1-有，0-无' ,
  play_url VARCHAR(200) NOT NULL   COMMENT '视频播放链接' ,
  create_time DATETIME NOT NULL   COMMENT '创建时间' ,
  delete_flag CHAR(1) NOT NULL   COMMENT '是否删除(1.已删除0.未删除)' ,
  PRIMARY KEY (id)
) COMMENT = '比赛视频源表';