-- 创建篮球比赛得分表
drop table if exists basketball_score_t;
CREATE TABLE basketball_score_t(
  id BIGINT(19) NOT NULL   COMMENT '主键id' ,
  match_id BIGINT(19)    COMMENT '比赛id' ,
  team_id BIGINT(19)    COMMENT '球队id' ,
  team_type TINYINT(3)    COMMENT ' 1-主队 2-客队' ,
  match_status TINYINT(3)    COMMENT '比赛状态' ,
  time BIGINT(19)    COMMENT '实时时间' ,
  first_section_scores INT(10)    COMMENT '第1节分数' ,
  second_section_scores INT(10)    COMMENT '第2节分数' ,
  third_section_scores INT(10)    COMMENT '第3节分数' ,
  fourth_section_scores INT(10)    COMMENT '第4节分数' ,
  overtime_scores INT(10)    COMMENT '加时分数' ,
  create_time DATETIME    COMMENT '创建时间' ,
  delete_flag CHAR(1)    COMMENT '是否删除(1.已删除0.未删除)' ,
  PRIMARY KEY (id)
) COMMENT = '篮球比赛得分表';