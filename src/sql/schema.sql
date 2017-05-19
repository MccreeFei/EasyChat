#创建数据库
CREATE database easychat;

#创建用户表
CREATE TABLE user(
id INT NOT NULL PRIMARY KEY auto_increment comment'用户id',
name VARCHAR(100) comment'用户名',
password VARCHAR(100) comment'用户密码'
)ENGINE = innoDB CHARSET = utf8;