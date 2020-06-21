drop table if exists sys_user_t;
CREATE TABLE sys_user_t(
    id INT NOT NULL AUTO_INCREMENT  COMMENT '主键id' ,
    user_name VARCHAR(150)    COMMENT '用户名' ,
    password VARCHAR(150)    COMMENT '密码' ,
    phone VARCHAR(20)    COMMENT '手机号' ,
    email VARCHAR(100)    COMMENT '邮箱' ,
    register_time DATETIME    COMMENT '注册时间' ,
    register_ip VARCHAR(50)    COMMENT '注册ip' ,
    login_time DATETIME    COMMENT '最后登录时间' ,
    login_ip VARCHAR(50)    COMMENT '最后登录ip' ,
    delete_flag CHAR(1)    COMMENT '删除标识(0/1)' ,
    nick_name VARCHAR(150) COMMENT '昵称',
    age INT(2) COMMENT '年龄',
    sex INT(1) COMMENT '性别：1男，2女',
    sign VARCHAR(255) COMMENT '签名',
    img text COMMENT '头像base64字符串',
    PRIMARY KEY (id)
) COMMENT = '系统用户表';