-- 创建库
create database if not exists oral;

-- 使用库
use oral;

-- 用户表
create table if not exists user
(
    id         bigint auto_increment comment 'id' primary key,
    account    varchar(256)                          not null comment '账号',
    password   varchar(256)                          not null comment '密码',
    name       varchar(50)                           not null comment '姓名',
    age        integer                               not null comment '年龄',
    birthday   date                                  not null comment '出生日期',
    gender     varchar(10)                           not null comment '性别',
    phone      varchar(20)                           not null comment '联系方式',
    department varchar(50)                           not null comment '科室',
    job        varchar(50)                           not null comment '职称',
    avatar     varchar(1024)                         null comment '头像',
    photo      varchar(1024)                         null comment '照片',
    profile    varchar(512)                          null comment '简介',
    role       varchar(50) default 'doctor'          not null comment '用户角色：doctor/admin',
    createTime datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint     default 0                 not null comment '是否删除'
) comment '用户';

-- 患者表
create table if not exists patient
(
    id         bigint auto_increment comment 'id' primary key,
    name       varchar(50)                        not null comment '姓名',
    gender     varchar(10)                        not null comment '性别',
    age        integer                            not null comment '年龄',
    phone      varchar(20)                        not null comment '联系方式',
    blood      varchar(10)                        not null comment '血型',
    ssCard     varchar(50)                        not null comment '社保卡号',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint  default 0                 not null comment '是否删除'
) comment '患者';

-- 手术表
create table if not exists surgery
(
    id             bigint auto_increment comment 'id' primary key,
    patient        bigint                                 not null comment '患者id',
    doctor         bigint                                 not null comment '医生id',
    name           varchar(50)                            not null comment '手术名称',
    startTime      datetime                               not null comment '开始时间',
    duration       tinyint                                not null comment '手术时长',
    job            varchar(50)                            not null comment '医生工作(主刀)',
    medicalHistory varchar(256) default '无' comment '既往病史',
    chronicHistory varchar(256) default '无' comment '慢性病史',
    allergy        varchar(256) default '无' comment '药物过敏',
    reaction       varchar(256) default '无' comment '不良反应',
    evaluation     tinyint                                not null comment '术前评估,1-无风险 2-低风险 3-中风险 4-高风险',
    examination    tinyint                                not null comment '术前检查,1-合格 2-不合格',
    createTime     datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime     datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete       tinyint      default 0                 not null comment '是否删除',
    foreign key (patient) references patient (id),
    foreign key (doctor) references user (id)
) comment '手术';

-- 默认账号 doctor 123456 md5加密
insert into user (id, account, password, name, age, birthday, gender, phone, department, job, avatar, photo, profile,
                  role)
VALUES (1, 'doctor', 'e10adc3949ba59abbe56e057f20f883e', '张三', 37, '1986-10-17', '男', 133333333333, '口腔全科',
        '主任医师',
        null, null,
        '口腔全科医生，掌握口腔医学各学科知识与基本能力，擅长牙体牙髓常见病疑难病诊断治疗,显微根管治疗,牙齿美白美容修复。',
        'doctor');

insert into patient (id, name, gender, age, phone, blood, ssCard)
values (1, '李四', '男', 25, 13222222222, 'B', '1140001140001');


insert into surgery (id, patient, doctor, name, startTime, duration, job, evaluation, examination)
values (1, 1, 1, '口腔内肿瘤手术', '2023-10-17 12:00:00', 5, '主刀', 3, 1);