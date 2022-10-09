# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.29)
# Database: shareshop
# Generation Time: 2022-10-09 03:09:32 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table t_borrow_record
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_borrow_record`;

CREATE TABLE `t_borrow_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `borrow_user_id` int(11) DEFAULT NULL COMMENT '借用者',
  `goods_id` varchar(256) NOT NULL DEFAULT '' COMMENT '物品编号',
  `start_time` datetime DEFAULT NULL COMMENT '借用时间',
  `end_time` datetime DEFAULT NULL COMMENT '归还时间',
  `borrow_status` int(11) DEFAULT NULL COMMENT '借用状态',
  `shop_id` bigint(20) DEFAULT NULL COMMENT '店铺ID',
  `refuse_reason` varchar(256) DEFAULT NULL COMMENT '被拒理由',
  `username` varchar(256) DEFAULT NULL COMMENT '借用者姓名',
  `phone` varchar(256) DEFAULT NULL COMMENT '借用者手机',
  `take_count` int(11) DEFAULT '0' COMMENT '享用数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

LOCK TABLES `t_borrow_record` WRITE;
/*!40000 ALTER TABLE `t_borrow_record` DISABLE KEYS */;

INSERT INTO `t_borrow_record` (`id`, `borrow_user_id`, `goods_id`, `start_time`, `end_time`, `borrow_status`, `shop_id`, `refuse_reason`, `username`, `phone`, `take_count`)
VALUES
	(1,1,'1','2022-08-09 00:00:00','2022-09-29 00:00:00',1,NULL,NULL,NULL,NULL,NULL),
	(2,1,'2','2022-09-13 00:00:00',NULL,4,1,NULL,'djh','18367823310',3),
	(3,1,'2','2022-09-13 00:00:00',NULL,4,1,NULL,'djh','18367823310',5),
	(4,1,'2','2022-09-15 00:00:00',NULL,3,0,NULL,'djh','18367823310',0),
	(6,1,'2','2022-09-15 17:11:38',NULL,3,2,NULL,'djh','18367823310',0);

/*!40000 ALTER TABLE `t_borrow_record` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table t_goods
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_goods`;

CREATE TABLE `t_goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(256) NOT NULL DEFAULT '' COMMENT '物品名称',
  `type` varchar(256) NOT NULL DEFAULT '' COMMENT '物品类型',
  `good_url` varchar(256) DEFAULT NULL COMMENT '物品图标',
  `good_id` varchar(256) DEFAULT NULL COMMENT '物品编号',
  `shop_id` bigint(20) DEFAULT NULL COMMENT '店铺ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `stock` bigint(20) NOT NULL DEFAULT '0' COMMENT '商品库存',
  `borrow_count` bigint(20) NOT NULL DEFAULT '0' COMMENT '已借出数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

LOCK TABLES `t_goods` WRITE;
/*!40000 ALTER TABLE `t_goods` DISABLE KEYS */;

INSERT INTO `t_goods` (`id`, `name`, `type`, `good_url`, `good_id`, `shop_id`, `create_time`, `update_time`, `stock`, `borrow_count`)
VALUES
	(2,'雨伞','享用类型',NULL,'2',2,'2022-09-05 00:00:00','2022-09-15 00:00:00',2,0),
	(3,'矿泉水','借用类型',NULL,'1',1,'2022-09-15 00:00:00','2022-09-15 00:00:00',20,0),
	(4,'毛巾','借用类型',NULL,'3',2,'2022-09-15 17:13:00','2022-09-15 17:13:00',10,0);

/*!40000 ALTER TABLE `t_goods` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table t_log
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_log`;

CREATE TABLE `t_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `content` varchar(1000) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作内容',
  `time` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作时间',
  `user` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作人',
  `ip` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'ip',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

LOCK TABLES `t_log` WRITE;
/*!40000 ALTER TABLE `t_log` DISABLE KEYS */;

INSERT INTO `t_log` (`id`, `content`, `time`, `user`, `ip`)
VALUES
	(80,'用户 admin 登录系统','2021-05-25 16:42:07','admin','0:0:0:0:0:0:0:1'),
	(81,'更新用户：admin ','2021-05-25 16:42:19','admin','0:0:0:0:0:0:0:1'),
	(82,'用户 admin 退出系统','2021-05-25 16:42:29','admin','0:0:0:0:0:0:0:1'),
	(83,'用户 admin 登录系统','2021-05-25 16:42:31','admin','0:0:0:0:0:0:0:1'),
	(84,'更新用户：jerry ','2021-05-25 16:49:14','admin','0:0:0:0:0:0:0:1'),
	(85,'用户 admin 登录系统','2021-05-25 16:49:30','admin','0:0:0:0:0:0:0:1'),
	(86,'用户 admin 登录系统','2021-05-25 16:50:07','admin','0:0:0:0:0:0:0:1'),
	(87,'更新用户：jerry ','2021-05-25 16:50:21','admin','0:0:0:0:0:0:0:1'),
	(88,'用户 admin 登录系统','2021-05-26 16:35:40','admin','0:0:0:0:0:0:0:1'),
	(89,'更新用户：jerry ','2021-05-26 16:36:16','admin','0:0:0:0:0:0:0:1'),
	(90,'更新用户：jack ','2021-05-26 16:36:18','admin','0:0:0:0:0:0:0:1'),
	(91,'删除用户 tom ','2021-05-26 16:36:29','admin','0:0:0:0:0:0:0:1'),
	(92,'删除用户 hello ','2021-05-26 16:36:30','admin','0:0:0:0:0:0:0:1'),
	(93,'删除用户 jack ','2021-05-26 16:36:31','admin','0:0:0:0:0:0:0:1'),
	(94,'删除用户 jerry ','2021-05-26 16:36:32','admin','0:0:0:0:0:0:0:1'),
	(95,'用户 admin 登录系统','2021-05-31 12:18:16','admin','0:0:0:0:0:0:0:1'),
	(96,'更新用户：admin ','2021-05-31 12:19:48','admin','0:0:0:0:0:0:0:1'),
	(97,'更新用户：admin ','2021-05-31 12:19:57','admin','0:0:0:0:0:0:0:1'),
	(98,'用户 admin 登录系统','2021-06-01 16:46:09','admin','0:0:0:0:0:0:0:1'),
	(99,'新增用户：zhang ','2021-06-01 16:46:46','admin','0:0:0:0:0:0:0:1'),
	(100,'新增用户：li ','2021-06-01 16:47:12','admin','0:0:0:0:0:0:0:1'),
	(101,'更新用户：admin ','2021-06-01 16:47:19','admin','0:0:0:0:0:0:0:1'),
	(102,'用户 admin 退出系统','2021-06-01 16:47:22','admin','0:0:0:0:0:0:0:1'),
	(103,'用户 admin 登录系统','2021-06-01 16:47:24','admin','0:0:0:0:0:0:0:1'),
	(104,'更新用户：li ','2021-06-01 16:47:34','admin','0:0:0:0:0:0:0:1'),
	(105,'更新用户：zhang ','2021-06-01 16:47:35','admin','0:0:0:0:0:0:0:1'),
	(106,'用户 admin 登录系统','2021-06-07 12:32:17','admin','0:0:0:0:0:0:0:1'),
	(107,'用户 admin 登录系统','2021-06-07 14:16:00','admin','0:0:0:0:0:0:0:1'),
	(108,'用户 admin 登录系统','2021-06-07 14:34:26','admin','0:0:0:0:0:0:0:1'),
	(109,'更新用户：li ','2021-06-07 14:35:21','admin','0:0:0:0:0:0:0:1'),
	(110,'更新用户：li ','2021-06-07 14:35:23','admin','0:0:0:0:0:0:0:1'),
	(111,'更新角色：超级管理员','2021-06-07 14:35:41','admin','0:0:0:0:0:0:0:1'),
	(112,'更新角色：超级管理员','2021-06-07 14:35:45','admin','0:0:0:0:0:0:0:1'),
	(113,'更新权限菜单：用户管理','2021-06-07 14:37:58','admin','0:0:0:0:0:0:0:1'),
	(114,'更新权限菜单：用户管理','2021-06-07 14:38:08','admin','0:0:0:0:0:0:0:1'),
	(115,'更新权限菜单：用户管理','2021-06-07 14:38:13','admin','0:0:0:0:0:0:0:1'),
	(116,'更新权限菜单：用户管理','2021-06-07 14:38:21','admin','0:0:0:0:0:0:0:1'),
	(117,'用户 admin 登录系统','2021-06-07 14:57:29','admin','0:0:0:0:0:0:0:1'),
	(118,'更新角色：超级管理员','2021-06-07 14:57:43','admin','0:0:0:0:0:0:0:1'),
	(119,'用户 admin 登录系统','2021-06-09 23:19:38','admin','0:0:0:0:0:0:0:1'),
	(120,'删除权限菜单：学生管理','2021-06-09 23:19:50','admin','0:0:0:0:0:0:0:1'),
	(121,'用户 admin 登录系统','2021-06-12 10:18:54','admin','0:0:0:0:0:0:0:1'),
	(122,'更新角色：超级管理员','2021-06-12 10:30:15','admin','0:0:0:0:0:0:0:1'),
	(123,'用户 admin 登录系统','2021-06-12 13:55:01','admin','0:0:0:0:0:0:0:1'),
	(124,'用户 admin 登录系统','2021-06-27 10:44:12','admin','0:0:0:0:0:0:0:1'),
	(125,'更新角色：超级管理员','2021-06-27 10:44:26','admin','0:0:0:0:0:0:0:1'),
	(126,'用户 admin 登录系统','2021-06-27 11:15:11','admin','0:0:0:0:0:0:0:1'),
	(127,'更新角色：超级管理员','2021-06-27 11:15:17','admin','0:0:0:0:0:0:0:1'),
	(128,'用户 admin 登录系统','2021-06-27 11:18:36','admin','0:0:0:0:0:0:0:1'),
	(129,'用户 admin 登录系统','2021-06-27 11:41:59','admin','0:0:0:0:0:0:0:1'),
	(130,'更新角色：超级管理员','2021-06-27 11:42:04','admin','0:0:0:0:0:0:0:1'),
	(131,'用户 admin 登录系统','2021-06-27 11:49:27','admin','0:0:0:0:0:0:0:1'),
	(132,'用户 admin 登录系统','2022-08-26 10:44:48','admin','0:0:0:0:0:0:0:1'),
	(133,'用户 admin 登录系统','2022-08-26 10:46:36','admin','0:0:0:0:0:0:0:1'),
	(134,'用户 admin 登录系统','2022-08-26 10:49:29','admin','0:0:0:0:0:0:0:1'),
	(135,'更新角色：超级管理员','2022-08-26 10:50:41','admin','0:0:0:0:0:0:0:1'),
	(136,'更新角色：超级管理员','2022-08-26 10:51:01','admin','0:0:0:0:0:0:0:1'),
	(137,'用户 admin 登录系统','2022-08-26 11:17:01','admin','0:0:0:0:0:0:0:1'),
	(138,'更新角色：超级管理员','2022-08-26 11:17:10','admin','0:0:0:0:0:0:0:1'),
	(139,'用户 admin 登录系统','2022-08-26 11:21:02','admin','0:0:0:0:0:0:0:1'),
	(140,'更新角色：超级管理员','2022-08-26 11:21:10','admin','0:0:0:0:0:0:0:1'),
	(141,'更新角色：超级管理员','2022-08-26 11:23:44','admin','0:0:0:0:0:0:0:1'),
	(142,'更新角色：超级管理员','2022-08-26 11:24:51','admin','0:0:0:0:0:0:0:1'),
	(143,'更新角色：超级管理员','2022-08-26 11:25:32','admin','0:0:0:0:0:0:0:1'),
	(144,'用户 admin 登录系统','2022-08-26 11:32:12','admin','0:0:0:0:0:0:0:1'),
	(145,'更新角色：超级管理员','2022-08-26 11:33:32','admin','0:0:0:0:0:0:0:1'),
	(146,'用户 admin 登录系统','2022-08-26 12:35:50','admin','0:0:0:0:0:0:0:1'),
	(147,'用户 admin 登录系统','2022-08-26 14:01:28','admin','0:0:0:0:0:0:0:1'),
	(148,'用户 admin 登录系统','2022-08-26 14:18:17','admin','0:0:0:0:0:0:0:1'),
	(149,'更新角色：超级管理员','2022-08-26 14:18:37','admin','0:0:0:0:0:0:0:1'),
	(150,'删除权限菜单：图书管理','2022-08-26 14:18:49','admin','0:0:0:0:0:0:0:1'),
	(151,'删除权限菜单：物品管理','2022-08-26 14:19:16','admin','0:0:0:0:0:0:0:1'),
	(152,'用户 admin 登录系统','2022-08-26 16:03:43','admin','0:0:0:0:0:0:0:1'),
	(153,'更新角色：超级管理员','2022-08-26 16:04:25','admin','0:0:0:0:0:0:0:1'),
	(154,'用户 admin 登录系统','2022-08-26 16:12:02','admin','0:0:0:0:0:0:0:1'),
	(155,'用户 admin 登录系统','2022-08-26 16:22:25','admin','0:0:0:0:0:0:0:1'),
	(156,'用户 admin 登录系统','2022-08-26 16:29:11','admin','0:0:0:0:0:0:0:1'),
	(157,'用户 admin 登录系统','2022-08-26 17:00:58','admin','0:0:0:0:0:0:0:1'),
	(158,'用户 admin 登录系统','2022-08-26 20:22:42','admin','0:0:0:0:0:0:0:1'),
	(159,'用户 admin 登录系统','2022-09-02 09:26:54','admin','0:0:0:0:0:0:0:1'),
	(160,'用户 admin 登录系统','2022-09-02 10:20:33','admin','0:0:0:0:0:0:0:1'),
	(161,'用户 admin 登录系统','2022-09-02 10:25:41','admin','0:0:0:0:0:0:0:1'),
	(162,'用户 admin 登录系统','2022-09-02 10:27:17','admin','0:0:0:0:0:0:0:1'),
	(163,'用户 admin 登录系统','2022-09-02 10:29:10','admin','0:0:0:0:0:0:0:1'),
	(164,'用户 admin 登录系统','2022-09-02 10:38:07','admin','0:0:0:0:0:0:0:1'),
	(165,'用户 admin 登录系统','2022-09-02 10:41:33','admin','0:0:0:0:0:0:0:1'),
	(166,'用户 admin 登录系统','2022-09-02 13:01:27','admin','0:0:0:0:0:0:0:1'),
	(167,'用户 admin 登录系统','2022-09-02 13:06:28','admin','0:0:0:0:0:0:0:1'),
	(168,'用户 admin 登录系统','2022-09-02 13:09:15','admin','0:0:0:0:0:0:0:1'),
	(169,'用户 admin 登录系统','2022-09-02 13:18:23','admin','0:0:0:0:0:0:0:1'),
	(170,'用户 admin 登录系统','2022-09-02 13:26:47','admin','0:0:0:0:0:0:0:1'),
	(171,'用户 admin 登录系统','2022-09-02 13:32:10','admin','0:0:0:0:0:0:0:1'),
	(172,'用户 admin 登录系统','2022-09-02 13:35:46','admin','0:0:0:0:0:0:0:1'),
	(173,'用户 admin 登录系统','2022-09-02 13:43:07','admin','0:0:0:0:0:0:0:1'),
	(174,'用户 admin 登录系统','2022-09-02 13:44:46','admin','0:0:0:0:0:0:0:1'),
	(175,'用户 admin 登录系统','2022-09-02 13:45:58','admin','0:0:0:0:0:0:0:1'),
	(176,'用户 admin 登录系统','2022-09-02 13:47:54','admin','0:0:0:0:0:0:0:1'),
	(177,'用户 admin 登录系统','2022-09-07 10:29:52','admin','0:0:0:0:0:0:0:1'),
	(178,'用户 admin 登录系统','2022-09-07 10:29:52','admin','0:0:0:0:0:0:0:1'),
	(179,'用户 admin 登录系统','2022-09-07 10:29:52','admin','0:0:0:0:0:0:0:1'),
	(180,'用户 admin 登录系统','2022-09-07 10:29:52','admin','0:0:0:0:0:0:0:1'),
	(181,'用户 admin 登录系统','2022-09-07 10:29:52','admin','0:0:0:0:0:0:0:1'),
	(182,'用户 admin 登录系统','2022-09-07 10:29:52','admin','0:0:0:0:0:0:0:1'),
	(183,'用户 admin 登录系统','2022-09-07 10:29:52','admin','0:0:0:0:0:0:0:1'),
	(184,'用户 admin 登录系统','2022-09-13 10:51:15','admin','0:0:0:0:0:0:0:1'),
	(185,'更新角色：超级管理员','2022-09-13 10:51:44','admin','0:0:0:0:0:0:0:1'),
	(186,'用户 admin 登录系统','2022-09-13 10:53:09','admin','0:0:0:0:0:0:0:1'),
	(187,'用户 admin 登录系统','2022-09-13 10:53:56','admin','0:0:0:0:0:0:0:1'),
	(188,'用户 admin 登录系统','2022-09-13 11:15:49','admin','0:0:0:0:0:0:0:1'),
	(189,'用户 admin 登录系统','2022-09-13 13:00:35','admin','0:0:0:0:0:0:0:1'),
	(190,'用户 admin 登录系统','2022-09-13 13:15:46','admin','0:0:0:0:0:0:0:1'),
	(191,'用户 admin 登录系统','2022-09-15 10:04:35','admin','0:0:0:0:0:0:0:1'),
	(192,'用户 admin 登录系统','2022-09-15 17:03:15','admin','0:0:0:0:0:0:0:1'),
	(193,'用户 admin 登录系统','2022-09-15 17:06:55','admin','0:0:0:0:0:0:0:1'),
	(194,'用户 admin 登录系统','2022-09-15 17:39:19','admin','0:0:0:0:0:0:0:1');

/*!40000 ALTER TABLE `t_log` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table t_message
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_message`;

CREATE TABLE `t_message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `content` text CHARACTER SET utf8mb4 COMMENT '内容',
  `username` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '评论人',
  `time` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '评论时间',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父ID',
  `foreign_id` bigint(20) DEFAULT '0' COMMENT '关联id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='留言表';

LOCK TABLES `t_message` WRITE;
/*!40000 ALTER TABLE `t_message` DISABLE KEYS */;

INSERT INTO `t_message` (`id`, `content`, `username`, `time`, `parent_id`, `foreign_id`)
VALUES
	(16,'我来了','admin','2021-04-23 23:15:57',NULL,0),
	(17,'来了老弟','admin','2021-04-23 23:17:46',16,0),
	(19,'今天直播','admin','2021-04-24 11:08:41',17,0);

/*!40000 ALTER TABLE `t_message` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table t_notice
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_notice`;

CREATE TABLE `t_notice` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `title` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标题',
  `content` text COLLATE utf8mb4_unicode_ci COMMENT '内容',
  `time` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发布时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

LOCK TABLES `t_notice` WRITE;
/*!40000 ALTER TABLE `t_notice` DISABLE KEYS */;

INSERT INTO `t_notice` (`id`, `title`, `content`, `time`)
VALUES
	(4,'学习','别问！问就是3连！','2021-05-17 15:29:29'),
	(5,'快乐是什么？','快乐就是一杯咖啡，一个键盘，从早到晚','2021-05-17 15:30:08'),
	(6,'Java是什么','Java是世界上最好的语言','2021-05-17 15:30:42');

/*!40000 ALTER TABLE `t_notice` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table t_permission
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_permission`;

CREATE TABLE `t_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `path` varchar(255) DEFAULT NULL COMMENT '菜单路径',
  `icon` varchar(255) DEFAULT 's-data' COMMENT '图标',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='权限菜单表';

LOCK TABLES `t_permission` WRITE;
/*!40000 ALTER TABLE `t_permission` DISABLE KEYS */;

INSERT INTO `t_permission` (`id`, `name`, `description`, `path`, `icon`)
VALUES
	(1,'用户管理','用户管理','user','user-solid'),
	(2,'角色管理','角色管理','role','s-cooperation'),
	(3,'权限管理','权限管理','permission','menu'),
	(19,'公告管理','公告管理','notice','data-board'),
	(24,'日志管理','日志管理','log','notebook-2'),
	(25,'在线留言','在线留言','message','message'),
	(39,'店铺管理','店铺管理','shop','s-data'),
	(40,'借用记录管理','借用记录管理','borrowRecord','s-data'),
	(42,'商品管理','商品管理','good','s-data');

/*!40000 ALTER TABLE `t_permission` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table t_role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_role`;

CREATE TABLE `t_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `permission` varchar(2000) DEFAULT NULL COMMENT '权限列表',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色表';

LOCK TABLES `t_role` WRITE;
/*!40000 ALTER TABLE `t_role` DISABLE KEYS */;

INSERT INTO `t_role` (`id`, `name`, `description`, `permission`)
VALUES
	(1,'超级管理员','所有权限','[19,27,30,24,38,39,2,40,3,42]'),
	(2,'普通用户','部分权限','[]');

/*!40000 ALTER TABLE `t_role` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table t_shop
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_shop`;

CREATE TABLE `t_shop` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(256) NOT NULL DEFAULT '' COMMENT '店名',
  `address` varchar(256) DEFAULT '' COMMENT '地址',
  `phonename` varchar(256) DEFAULT '' COMMENT '联系人',
  `phone` varchar(256) NOT NULL DEFAULT '' COMMENT '联系电话',
  `longitude` float DEFAULT NULL COMMENT '经度',
  `latitude` float DEFAULT NULL COMMENT '纬度',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

LOCK TABLES `t_shop` WRITE;
/*!40000 ALTER TABLE `t_shop` DISABLE KEYS */;

INSERT INTO `t_shop` (`id`, `name`, `address`, `phonename`, `phone`, `longitude`, `latitude`)
VALUES
	(1,'石浦小店','浙江省杭州市余杭区五常街道东山村','李虎','18367823310',0,0),
	(2,'大众店铺','浙江省宁波市xxx小区1号楼102','田一明','18778768965',12.23,12.23);

/*!40000 ALTER TABLE `t_shop` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table t_user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '密码',
  `nick_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '昵称',
  `email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号',
  `avatar` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头像',
  `role` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '角色',
  `address` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '地址',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `shop_id` bigint(20) DEFAULT NULL COMMENT '店铺ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='用户表';

LOCK TABLES `t_user` WRITE;
/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;

INSERT INTO `t_user` (`id`, `username`, `password`, `nick_name`, `email`, `phone`, `avatar`, `role`, `address`, `age`, `shop_id`)
VALUES
	(1,'admin','admin','管理员','111124444','13978786565','1622537239707','[1]','北京',NULL,NULL),
	(2,'zhang','123456','张三','zhang@qq.com','13089897878',NULL,'[2]','北京',24,NULL),
	(3,'li','123456','李四','li@qq.com','13989897898',NULL,'[2]','南京',22,NULL);

/*!40000 ALTER TABLE `t_user` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
