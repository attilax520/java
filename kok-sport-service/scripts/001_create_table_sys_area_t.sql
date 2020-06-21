
drop table if exists sys_area_t;
CREATE TABLE sys_area_t (
  id          BIGINT(19)   NOT NULL COMMENT '主键id',
  name_zh     VARCHAR(100) COMMENT '中文',
  name_zht    VARCHAR(100) COMMENT '粤语',
  name_en     VARCHAR(100) COMMENT '英文',
  create_time DATETIME NOT NULL COMMENT '创建时间',
  delete_flag CHAR(1)  NOT NULL COMMENT '是否删除(1.已删除0.未删除)',
  PRIMARY KEY (id)
) COMMENT = '区域表';