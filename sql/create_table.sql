-- 创建库
create database if not exists oj;

-- 使用库
use oj;

DROP TABLE IF EXISTS `exercise_finish`;
DROP TABLE IF EXISTS exam_finish;
DROP TABLE IF EXISTS examination;
DROP TABLE IF EXISTS exercise;
DROP TABLE IF EXISTS question;
DROP TABLE IF EXISTS `user`;

CREATE TABLE `user`
(
    `id`       BIGINT NOT NULL AUTO_INCREMENT,
    `account`  varchar(256) DEFAULT NULL,
    `password` varchar(256) DEFAULT NULL,
    `name`     varchar(256) DEFAULT NULL,
    `avatar`   varchar(256) DEFAULT NULL,
    `role`     varchar(256) DEFAULT NULL,
    `isDelete` varchar(256) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 13
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


create table exercise
(
    id        bigint auto_increment comment 'id' primary key,
    name      varchar(256),
    content   varchar(2000),
    state     int,
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


CREATE TABLE `exercise_finish`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `exercise_id` bigint(20) NOT NULL,
    `user_id`     bigint(20) NOT NULL,
    `score`       int(11)             DEFAULT NULL,
    `answer`      longtext   NOT NULL COMMENT '提交的答案',
    `result`      int(11)             DEFAULT NULL COMMENT '评语',
    `create_time` datetime   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `exercise_id` (`exercise_id`),
    KEY `user_id` (`user_id`),
    CONSTRAINT `exercise_finish_ibfk_1` FOREIGN KEY (`exercise_id`) REFERENCES `exercise` (`id`),
    CONSTRAINT `exercise_finish_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 9
  DEFAULT CHARSET = utf8 COMMENT ='实验完成表';


INSERT INTO `user`
VALUES (1, '123456', 'e10adc3949ba59abbe56e057f20f883e', NULL, NULL, 'student', NULL),
       (3, '1234567890', '1234567890', NULL, NULL, 'student', '0'),
       (6, '1234567891', '111', NULL, NULL, 'student', '0'),
       (7, '1234567892', '111', NULL, NULL, 'student', '0'),
       (8, '2021150110', '11111', NULL, NULL, 'student', '0'),
       (9, '1111111111', '1111111111', NULL, NULL, 'student', '0'),
       (10, '1111111112', '111', NULL, NULL, 'student', '0'),
       (11, '1111333333', '111', NULL, NULL, 'student', '0'),
       (12, '1113333333', '333', NULL, NULL, 'student', '0');
UNLOCK TABLES;

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

INSERT INTO examination (id, title, questions, randomize_options, allow_view_answers, allow_backward, time_limit, type, user_id, create_time, update_time) VALUES (1, '测试随机生成', '[2, 1, 3, 4, 5, 7, 12, 6, 9, 8]', 1, 1, 1, 120, 0, 1, '2024-06-21 12:42:59', '2024-06-21 12:42:59');
INSERT INTO examination (id, title, questions, randomize_options, allow_view_answers, allow_backward, time_limit, type, user_id, create_time, update_time) VALUES (2, '测试提交试卷', '[1, 2, 3, 4]', 1, 1, 1, 119, 0, 1, '2024-06-21 12:43:27', '2024-06-21 12:43:27');

INSERT INTO exam_finish (id, examination_id, user_id, score, answer, result, create_time, update_time) VALUES (1, 2, 1, 5, '[["document.createElement()"], ["map"], ["True"], ["cs"]]', '[false, false, true, false]', '2024-06-21 12:43:50', '2024-06-21 12:43:50');


INSERT INTO exercise (id, name, content, state, timestamp) VALUES (1, '测试实验', '<blockquote><span style="font-size: 22px; font-family: 标楷体;"><strong>测试实验 😀😃😇</strong></span></blockquote><p>红色 背景色 删除线 斜体 &nbsp;<a href="https://www.wangeditor.com/" target="_blank">链接</a> </p><ul><li>无序列表</li><li>无序列表</li></ul><p style="line-height: 1;"><br></p><ol><li>有序列表</li><li>有序列表</li></ol><p style="line-height: 1;"><br></p><div data-w-e-type="todo"><input type="checkbox" disabled checked>代办</div><div data-w-e-type="todo"><input type="checkbox" disabled >代办</div><p><img src="http://localhost:8100/upload/9c814a4c2c2a45faa1dcd01b664e57ac.png" alt="" data-href="" style=""/></p><table style="width: auto;"><tbody><tr><th colSpan="1" rowSpan="1" width="auto">1</th><th colSpan="1" rowSpan="1" width="auto">2</th><th colSpan="1" rowSpan="1" width="auto">3</th><th colSpan="1" rowSpan="1" width="auto">4</th><th colSpan="1" rowSpan="1" width="auto">5</th></tr><tr><td colSpan="1" rowSpan="1" width="auto"></td><td colSpan="1" rowSpan="1" width="auto"></td><td colSpan="1" rowSpan="1" width="auto"></td><td colSpan="1" rowSpan="1" width="auto"></td><td colSpan="1" rowSpan="1" width="auto"></td></tr><tr><td colSpan="1" rowSpan="1" width="auto"></td><td colSpan="1" rowSpan="1" width="auto"></td><td colSpan="1" rowSpan="1" width="auto"></td><td colSpan="1" rowSpan="1" width="auto"></td><td colSpan="1" rowSpan="1" width="auto"></td></tr></tbody></table><pre><code class="language-typescript">// 创建编辑器
const editor = createEditor({
  selector: \'#editor-container\'
})</code></pre><p><br></p>', 0, '2024-06-21 13:00:01');


INSERT INTO exercise_finish (id, exercise_id, user_id, score, answer, result, create_time, update_time) VALUES (9, 1, 1, null, '<blockquote><strong>测试答题</strong></blockquote><pre><code class="language-typescript">import \'@wangeditor/editor/dist/css/style.css\'
import { createEditor, createToolbar } from \'@wangeditor/editor\'

// 创建编辑器
const editor = createEditor({
  selector: \'#editor-container\'
})
// 创建工具栏
const toolbar = createToolbar({
  editor,
  selector: \'#toolbar-container\'
})</code></pre><p><br></p>', null, '2024-06-21 12:35:04', '2024-06-21 12:35:04');
