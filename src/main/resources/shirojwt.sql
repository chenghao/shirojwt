-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: shirojwt
-- ------------------------------------------------------
-- Server version	8.0.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `sys_config`
--

DROP TABLE IF EXISTS `sys_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_config` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'KEY',
  `value` varchar(100) COLLATE utf8mb4_general_ci NOT NULL COMMENT '值',
  `remark` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `del_state` int NOT NULL DEFAULT '0' COMMENT '删除状态（0未删除，1已删除）',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sys_config_un` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='配置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_config`
--

LOCK TABLES `sys_config` WRITE;
/*!40000 ALTER TABLE `sys_config` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dict`
--

DROP TABLE IF EXISTS `sys_dict`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dict` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `pid` int NOT NULL DEFAULT '0' COMMENT '父id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '字典名称',
  `value` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '字典值',
  `sort` float(5,2) NOT NULL DEFAULT '1.00' COMMENT '排序',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `del_state` varchar(100) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除状态（0未删除，1已删除）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sys_dict_un` (`pid`,`value`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='字典表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict`
--

LOCK TABLES `sys_dict` WRITE;
/*!40000 ALTER TABLE `sys_dict` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_dict` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_menu` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单名称',
  `pid` int DEFAULT '0' COMMENT '父ID',
  `url` varchar(100) CHARACTER SET utf8 DEFAULT '' COMMENT 'URL',
  `sort` float(5,2) NOT NULL DEFAULT '1.00' COMMENT '排序',
  `authority` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '对应权限',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `type` int DEFAULT '1' COMMENT '类型（1目录，2菜单，3按钮）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sys_menu_un` (`url`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` VALUES (1,'系统管理',0,'',10.00,'','2019-10-26 23:54:09',1),(2,'菜单管理',1,'/menu',1.00,'get:menu','2019-10-26 23:58:40',2),(3,'用户管理',1,'/user',2.00,'get:user','2019-10-27 00:05:48',2),(4,'角色管理',1,'/role',3.00,'get:role','2019-10-27 00:06:34',2);
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_oper_log`
--

DROP TABLE IF EXISTS `sys_oper_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_oper_log` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
  `business_type` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '业务类型',
  `method` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '方法名称',
  `oper_id` int DEFAULT NULL COMMENT '操作人id',
  `oper_url` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '请求URL',
  `oper_ip` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '操作ip',
  `oper_location` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '操作地址',
  `oper_param` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '请求参数',
  `status` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '操作状态',
  `error_msg` text COLLATE utf8mb4_general_ci COMMENT '错误信息',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1246429276854128642 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='操作日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_oper_log`
--

LOCK TABLES `sys_oper_log` WRITE;
/*!40000 ALTER TABLE `sys_oper_log` DISABLE KEYS */;
INSERT INTO `sys_oper_log` VALUES (1246427718896697345,'登录授权','OTHER','com.hao.sys.controller.UserController.login()',NULL,'/user/login','127.0.0.1','内网IP-内网IP','{}','SUCCESS','','2020-04-04 21:21:40'),(1246429276854128641,'登录授权','OTHER','com.hao.sys.controller.UserController.login()',NULL,'/user/login','127.0.0.1','内网IP-内网IP','{}','SUCCESS','','2020-04-04 21:27:51');
/*!40000 ALTER TABLE `sys_oper_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色code',
  `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sys_role_un` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (1,'admin','管理员'),(2,'test','测试');
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_menu`
--

DROP TABLE IF EXISTS `sys_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role_menu` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_id` int NOT NULL COMMENT '角色id',
  `menu_id` int NOT NULL COMMENT '菜单id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sys_role_menu_un` (`role_id`,`menu_id`),
  KEY `role_id` (`role_id`),
  KEY `permission_id` (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='角色资源表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_menu`
--

LOCK TABLES `sys_role_menu` WRITE;
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;
INSERT INTO `sys_role_menu` VALUES (1,1,1),(2,1,2),(4,1,3),(3,2,1);
/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `account` varchar(20) CHARACTER SET utf8 NOT NULL COMMENT '帐号',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `username` varchar(20) CHARACTER SET utf8 NOT NULL COMMENT '昵称',
  `reg_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `del_state` int NOT NULL DEFAULT '0' COMMENT '删除状态（0未删除，1已删除）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `account` (`account`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (1,'admin','QTQ5MjI2MkRBQjJFMEM1NjBEQ0Q1Q0EyNEJDODUwOEM=','admin','2019-10-08 14:31:31',0),(2,'wang','RUZGMzhFQzEyMDY2MUMxQTM0RDMzNDMyRkMwMEMzMUQ=','wang','2019-10-08 14:31:31',0),(3,'guest','QTNCMzMwREY3MkMwQjRGQjNBQzUyOTM0NTFFMzJCNDg=','guest','2019-10-08 14:31:31',0);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_role` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int NOT NULL COMMENT '用户id',
  `role_id` int NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sys_user_role_un` (`user_id`,`role_id`),
  KEY `user_id` (`user_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `sys_user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `sys_user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` VALUES (1,1,1),(2,2,2);
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_whitelist`
--

DROP TABLE IF EXISTS `sys_whitelist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_whitelist` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ip` varchar(100) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'IP',
  `remark` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='ip白名单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_whitelist`
--

LOCK TABLES `sys_whitelist` WRITE;
/*!40000 ALTER TABLE `sys_whitelist` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_whitelist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'shirojwt'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-04-17 15:18:46
