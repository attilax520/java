drop table if exists sys_country_t;
CREATE TABLE sys_country_t(
  id BIGINT(19) NOT NULL   COMMENT '国家id' ,
  area_id BIGINT(19) NOT NULL   COMMENT '地区id' ,
  name_zh VARCHAR(100)    COMMENT '中文' ,
  name_zht VARCHAR(100)    COMMENT '粤语' ,
  name_en VARCHAR(100)    COMMENT '英文' ,
  country_logo VARCHAR(100)    COMMENT '国家logo，https://cdn.sportnanoapi.com/football/country/51e67e390385b5ddb423f8483300d7f6.png' ,
  create_time DATETIME NOT NULL   COMMENT '创建时间' ,
  delete_flag CHAR(1) NOT NULL   COMMENT '是否删除(1.已删除0.未删除)' ,
  PRIMARY KEY (id)
) COMMENT = '国家表';