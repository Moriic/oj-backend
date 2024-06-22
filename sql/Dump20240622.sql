-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: oj
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `exam_finish`
--

DROP TABLE IF EXISTS `exam_finish`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exam_finish` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `examination_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `score` int NOT NULL,
  `answer` json NOT NULL,
  `result` json NOT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `examination_id` (`examination_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `exam_finish_ibfk_1` FOREIGN KEY (`examination_id`) REFERENCES `examination` (`id`),
  CONSTRAINT `exam_finish_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='完成表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exam_finish`
--

LOCK TABLES `exam_finish` WRITE;
/*!40000 ALTER TABLE `exam_finish` DISABLE KEYS */;
/*!40000 ALTER TABLE `exam_finish` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `examination`
--

DROP TABLE IF EXISTS `examination`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `examination` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL COMMENT '试卷标题',
  `questions` json NOT NULL COMMENT '试卷题目，以 JSON 数组形式存储',
  `randomize_options` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否随机排列选择题的答案选项',
  `allow_view_answers` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否允许答题后查看答案',
  `allow_backward` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否允许回退答题',
  `time_limit` int NOT NULL DEFAULT '120' COMMENT '限制时长',
  `type` int NOT NULL DEFAULT '0' COMMENT '学生/老师未发布/老师发布',
  `user_id` bigint NOT NULL COMMENT '创建试卷的用户 ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `examination_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='试卷表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `examination`
--

LOCK TABLES `examination` WRITE;
/*!40000 ALTER TABLE `examination` DISABLE KEYS */;
/*!40000 ALTER TABLE `examination` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exercise`
--

DROP TABLE IF EXISTS `exercise`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exercise` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(256) DEFAULT NULL,
  `content` varchar(2000) DEFAULT NULL,
  `state` int DEFAULT NULL,
  `timestamp` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='实验';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exercise`
--

LOCK TABLES `exercise` WRITE;
/*!40000 ALTER TABLE `exercise` DISABLE KEYS */;
/*!40000 ALTER TABLE `exercise` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `question` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `type` tinyint NOT NULL COMMENT '题目类型',
  `question` varchar(255) NOT NULL COMMENT '题目',
  `options` json NOT NULL COMMENT '选项',
  `answer` json NOT NULL COMMENT '答案',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `score` int NOT NULL DEFAULT '5',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `question_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='题库表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES (1,0,'以下哪种方法可以用于选择 HTML 元素？','[\"document.querySelector()\", \"document.createElement()\", \"document.addEventListener()\", \"document.getElementById()\"]','[\"document.querySelector()\"]',1,5,'2024-06-21 02:13:11','2024-06-21 02:13:11'),(2,1,'以下哪些是 JavaScript 中的数组方法？','[\"forEach\", \"map\", \"filter\", \"reduce\", \"toString\"]','[\"forEach\", \"map\", \"filter\", \"reduce\"]',1,5,'2024-06-21 02:13:11','2024-06-21 02:13:11'),(3,2,'JavaScript 是一种动态类型语言。','[\"True\", \"False\"]','[\"True\"]',1,5,'2024-06-21 02:13:11','2024-06-21 02:13:11'),(4,3,'简述 JavaScript 中闭包的概念。','[]','[\"闭包是指有权访问另一个函数作用域中的变量的函数。\"]',1,5,'2024-06-21 02:13:11','2024-06-21 02:13:11'),(5,0,'以下哪个 CSS 属性用于改变元素的背景颜色？','[\"color\", \"background-color\", \"font-size\", \"border\"]','[\"background-color\"]',1,5,'2024-06-21 02:13:11','2024-06-21 02:13:11'),(6,1,'以下哪些是 JavaScript 的基本数据类型？','[\"Undefined\", \"Null\", \"Boolean\", \"Number\", \"Symbol\"]','[\"Undefined\", \"Null\", \"Boolean\", \"Number\", \"Symbol\"]',1,5,'2024-06-21 02:13:11','2024-06-21 02:13:11'),(7,2,'JavaScript 可以在浏览器和服务器上运行。','[\"True\", \"False\"]','[\"True\"]',1,5,'2024-06-21 02:13:11','2024-06-21 02:13:11'),(8,3,'什么是 JavaScript 的事件循环？','[]','[\"事件循环是 JavaScript 处理异步操作的一种机制。\"]',1,5,'2024-06-21 02:13:11','2024-06-21 02:13:11'),(9,0,'以下哪个方法用于将 JSON 字符串解析为对象？','[\"JSON.stringify()\", \"JSON.parse()\", \"parseInt()\", \"toString()\"]','[\"JSON.parse()\"]',1,5,'2024-06-21 02:13:11','2024-06-21 02:13:11'),(10,1,'以下哪些是 ES6 新增的特性？','[\"箭头函数\", \"let 和 const\", \"模板字符串\", \"类\", \"模块\"]','[\"箭头函数\", \"let 和 const\", \"模板字符串\", \"类\", \"模块\"]',1,5,'2024-06-21 02:13:11','2024-06-21 02:13:11'),(11,2,'JavaScript 中的变量提升只对使用 var 声明的变量有效。','[\"True\", \"False\"]','[\"True\"]',1,5,'2024-06-21 02:13:11','2024-06-21 02:13:11'),(12,3,'解释 JavaScript 中的原型链。','[]','[\"原型链是 JavaScript 用来实现继承的机制，每个对象都有一个原型对象，通过这个原型对象可以访问其原型对象的属性和方法。\"]',1,5,'2024-06-21 02:13:11','2024-06-21 02:13:11'),(13,0,'以下哪个 HTML 标签用于定义段落？','[\"<div>\", \"<span>\", \"<p>\", \"<a>\"]','[\"<p>\"]',1,5,'2024-06-21 02:13:11','2024-06-21 02:13:11'),(14,1,'以下哪些是 JavaScript 的全局对象？','[\"Math\", \"Date\", \"JSON\", \"String\", \"Number\"]','[\"Math\", \"Date\", \"JSON\"]',1,5,'2024-06-21 02:13:11','2024-06-21 02:13:11'),(15,2,'在 JavaScript 中，null 和 undefined 是相等的。','[\"True\", \"False\"]','[\"True\"]',1,5,'2024-06-21 02:13:11','2024-06-21 02:13:11'),(16,3,'解释 JavaScript 中的作用域链。','[]','[\"作用域链是指在 JavaScript 中嵌套的作用域，内部作用域可以访问外部作用域的变量。\"]',1,5,'2024-06-21 02:13:11','2024-06-21 02:13:11'),(17,0,'以下哪个方法用于合并两个或多个数组？','[\"concat()\", \"push()\", \"pop()\", \"shift()\"]','[\"concat()\"]',1,5,'2024-06-21 02:13:11','2024-06-21 02:13:11'),(18,1,'以下哪些是 JavaScript 的字符串方法？','[\"charAt()\", \"concat()\", \"includes()\", \"slice()\", \"split()\"]','[\"charAt()\", \"concat()\", \"includes()\", \"slice()\", \"split()\"]',1,5,'2024-06-21 02:13:11','2024-06-21 02:13:11'),(19,2,'JavaScript 中的所有函数都是对象。','[\"True\", \"False\"]','[\"True\"]',1,5,'2024-06-21 02:13:11','2024-06-21 02:13:11'),(20,3,'什么是 JavaScript 中的“this”？','[]','[\"this 是一个指向当前执行上下文的对象的引用。\"]',1,5,'2024-06-21 02:13:11','2024-06-21 02:13:11');
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_config`
--

DROP TABLE IF EXISTS `system_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system_config` (
  `id` int NOT NULL AUTO_INCREMENT,
  `config_key` varchar(255) NOT NULL,
  `config_value` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_config`
--

LOCK TABLES `system_config` WRITE;
/*!40000 ALTER TABLE `system_config` DISABLE KEYS */;
INSERT INTO `system_config` VALUES (1,'register','1',NULL);
/*!40000 ALTER TABLE `system_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `todolist`
--

DROP TABLE IF EXISTS `todolist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `todolist` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `task` varchar(256) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `todolist_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `todolist`
--

LOCK TABLES `todolist` WRITE;
/*!40000 ALTER TABLE `todolist` DISABLE KEYS */;
INSERT INTO `todolist` VALUES (1,'啊啊啊',1),(2,'24333',1),(3,'24333',1),(4,'啊啊啊',1),(5,'',1),(6,'啊啊',1),(7,'啊啊',1),(8,'啊啊啊啊',1),(9,'啊啊啊啊',1),(10,'啊啊啊',1),(11,'啊啊',1),(12,'啊',1),(13,'啊',1),(14,'啊',1);
/*!40000 ALTER TABLE `todolist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL,
  `account` varchar(256) DEFAULT NULL,
  `password` varchar(256) DEFAULT NULL,
  `name` varchar(256) DEFAULT NULL,
  `avatar` varchar(256) DEFAULT NULL,
  `role` varchar(256) DEFAULT NULL,
  `isDelete` varchar(256) DEFAULT NULL,
  `signature` varchar(256) DEFAULT NULL,
  `blocked` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'123456','e10adc3949ba59abbe56e057f20f883e','邵致乐',NULL,'student',NULL,'啊啊啊',NULL),(8,'2021150110','11111',NULL,NULL,'student','0',NULL,NULL),(9,'1111111111','1111111111',NULL,NULL,'student','0',NULL,NULL),(10,'1111111112','111',NULL,NULL,'student','0',NULL,NULL),(11,'1111333333','111',NULL,NULL,'student','0',NULL,NULL),(12,'1113333333','333',NULL,NULL,'student','0',NULL,NULL),(13,'1222222222','b59c67bf196a4758191e42f76670ceba',NULL,NULL,'student','0',NULL,NULL),(101,'1234567','e10adc3949ba59abbe56e057f20f883e','szl',NULL,'teacher',NULL,'你好',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'oj'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-22 13:38:46
