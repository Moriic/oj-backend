-- 创建库
create database if not exists oj;

-- 使用库
use oj;

DROP TABLE IF EXISTS exam_finish;
DROP TABLE IF EXISTS examination;
DROP TABLE IF EXISTS exercise;
DROP TABLE IF EXISTS question;
DROP TABLE IF EXISTS user;

-- 用户表
create table if not exists user
(
    id       bigint auto_increment comment 'id' primary key,
    account  varchar(256) not null comment '账号',
    password varchar(256) not null comment '密码'
) comment '用户';

create table exercise
(
    id      bigint auto_increment comment 'id' primary key,
    name    varchar(256),
    content varchar(2000),
    state int,
    timestamp datetime
) comment '实验';

CREATE TABLE question
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    type        TINYINT                            NOT NULL COMMENT '题目类型',
    question    VARCHAR(255)                       NOT NULL COMMENT '题目',
    options     JSON                               NOT NULL COMMENT '选项',
    answer      JSON                               NOT NULL COMMENT '答案',
    user_id     bigint                             NOT NULL COMMENT '用户id',
    score       INT      DEFAULT 5                 NOT NULL,
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    foreign key (user_id) references user (id)
) comment '题库表';


CREATE TABLE examination
(
    id                 BIGINT AUTO_INCREMENT PRIMARY KEY,
    title              VARCHAR(255) NOT NULL COMMENT '试卷标题',
    questions          JSON         NOT NULL COMMENT '试卷题目，以 JSON 数组形式存储',
    randomize_options  BOOLEAN      NOT NULL DEFAULT FALSE COMMENT '是否随机排列选择题的答案选项',
    allow_view_answers BOOLEAN      NOT NULL DEFAULT FALSE COMMENT '是否允许答题后查看答案',
    allow_backward     BOOLEAN      NOT NULL DEFAULT FALSE COMMENT '是否允许回退答题',
    time_limit         INT          NOT NULL DEFAULT 120 COMMENT '限制时长',
    type               INT          NOT NULL DEFAULT 0 COMMENT '学生/老师未发布/老师发布',
    user_id            BIGINT       NOT NULL COMMENT '创建试卷的用户 ID',
    create_time        datetime              default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time        datetime              default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    FOREIGN KEY (user_id) REFERENCES user (id)
) COMMENT '试卷表';

CREATE TABLE exam_finish
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    examination_id BIGINT                             NOT NULL,
    user_id        BIGINT                             NOT NULL,
    score          INT                                NOT NULL,
    answer         JSON                               NOT NULL,
    result         JSON                               NOT NULL,
    create_time    datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time    datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    foreign key (examination_id) references examination (id),
    foreign key (user_id) references user (id)
) COMMENT '完成表';



INSERT INTO user
VALUES (1, 1, 1);

INSERT INTO question (type, question, options, answer, user_id)
VALUES (0, '以下哪种方法可以用于选择 HTML 元素？', '[
  "document.querySelector()",
  "document.createElement()",
  "document.addEventListener()",
  "document.getElementById()"
]', '[
  "document.querySelector()"
]', 1),
       (1, '以下哪些是 JavaScript 中的数组方法？', '[
         "forEach",
         "map",
         "filter",
         "reduce",
         "toString"
       ]', '[
         "forEach",
         "map",
         "filter",
         "reduce"
       ]', 1),
       (2, 'JavaScript 是一种动态类型语言。', '[
         "True",
         "False"
       ]', '[
         "True"
       ]', 1),
       (3, '简述 JavaScript 中闭包的概念。', '[]', '[
         "闭包是指有权访问另一个函数作用域中的变量的函数。"
       ]', 1),
       (0, '以下哪个 CSS 属性用于改变元素的背景颜色？', '[
         "color",
         "background-color",
         "font-size",
         "border"
       ]', '[
         "background-color"
       ]', 1),
       (1, '以下哪些是 JavaScript 的基本数据类型？', '[
         "Undefined",
         "Null",
         "Boolean",
         "Number",
         "Symbol"
       ]', '[
         "Undefined",
         "Null",
         "Boolean",
         "Number",
         "Symbol"
       ]', 1),
       (2, 'JavaScript 可以在浏览器和服务器上运行。', '[
         "True",
         "False"
       ]', '[
         "True"
       ]', 1),
       (3, '什么是 JavaScript 的事件循环？', '[]', '[
         "事件循环是 JavaScript 处理异步操作的一种机制。"
       ]', 1),
       (0, '以下哪个方法用于将 JSON 字符串解析为对象？', '[
         "JSON.stringify()",
         "JSON.parse()",
         "parseInt()",
         "toString()"
       ]', '[
         "JSON.parse()"
       ]', 1),
       (1, '以下哪些是 ES6 新增的特性？', '[
         "箭头函数",
         "let 和 const",
         "模板字符串",
         "类",
         "模块"
       ]', '[
         "箭头函数",
         "let 和 const",
         "模板字符串",
         "类",
         "模块"
       ]', 1),
       (2, 'JavaScript 中的变量提升只对使用 var 声明的变量有效。', '[
         "True",
         "False"
       ]', '[
         "True"
       ]', 1),
       (3, '解释 JavaScript 中的原型链。', '[]', '[
         "原型链是 JavaScript 用来实现继承的机制，每个对象都有一个原型对象，通过这个原型对象可以访问其原型对象的属性和方法。"
       ]', 1),
       (0, '以下哪个 HTML 标签用于定义段落？', '[
         "<div>",
         "<span>",
         "<p>",
         "<a>"
       ]', '[
         "<p>"
       ]', 1),
       (1, '以下哪些是 JavaScript 的全局对象？', '[
         "Math",
         "Date",
         "JSON",
         "String",
         "Number"
       ]', '[
         "Math",
         "Date",
         "JSON"
       ]', 1),
       (2, '在 JavaScript 中，null 和 undefined 是相等的。', '[
         "True",
         "False"
       ]', '[
         "True"
       ]', 1),
       (3, '解释 JavaScript 中的作用域链。', '[]', '[
         "作用域链是指在 JavaScript 中嵌套的作用域，内部作用域可以访问外部作用域的变量。"
       ]', 1),
       (0, '以下哪个方法用于合并两个或多个数组？', '[
         "concat()",
         "push()",
         "pop()",
         "shift()"
       ]', '[
         "concat()"
       ]', 1),
       (1, '以下哪些是 JavaScript 的字符串方法？', '[
         "charAt()",
         "concat()",
         "includes()",
         "slice()",
         "split()"
       ]', '[
         "charAt()",
         "concat()",
         "includes()",
         "slice()",
         "split()"
       ]', 1),
       (2, 'JavaScript 中的所有函数都是对象。', '[
         "True",
         "False"
       ]', '[
         "True"
       ]', 1),
       (3, '什么是 JavaScript 中的“this”？', '[]', '[
         "this 是一个指向当前执行上下文的对象的引用。"
       ]', 1);

