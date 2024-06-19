-- 创建库
create database if not exists oj;

-- 使用库
use oj;

-- 用户表
create table if not exists user
(
    id         bigint auto_increment comment 'id' primary key,
    account    varchar(256)                          not null comment '账号',
    password   varchar(256)                          not null comment '密码'
) comment '用户';


--
create table exercise
(
    id         bigint auto_increment comment 'id' primary key,
    name    varchar(256),
    content   varchar(256)
) comment '实验';