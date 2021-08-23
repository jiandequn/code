/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50711
Source Host           : localhost:3306
Source Database       : quartz_sys

Target Server Type    : MYSQL
Target Server Version : 50711
File Encoding         : 65001

Date: 2020-03-06 17:59:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for schedule_job
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job`;
CREATE TABLE `schedule_job` (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(255) DEFAULT NULL COMMENT '任务名称',
  `job_group` varchar(255) DEFAULT NULL COMMENT '任务组',
  `is_concurrent` tinyint(4) DEFAULT NULL COMMENT '运行状态1\\0',
  `cron_expression` varchar(255) DEFAULT NULL COMMENT 'corn表达式',
  `spring_id` varchar(255) DEFAULT NULL COMMENT 'beanId；相对beanClass优先级高',
  `bean_class` varchar(255) DEFAULT NULL COMMENT '完整路径',
  `method_name` varchar(255) DEFAULT NULL COMMENT '方法名',
  `runing_status` tinyint(4) DEFAULT NULL COMMENT '任务运行状态-1非法的 0表示已停止 1已在队列 ',
  `job_status` tinyint(4) DEFAULT '0' COMMENT '任务有效状态 0禁用 1启用',
  `job_data` text COMMENT '调用方法的数据',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `is_effective` tinyint(4) DEFAULT '1' COMMENT '是否可用 0不可用，1可用',
  PRIMARY KEY (`job_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of schedule_job
-- ----------------------------

-- ----------------------------
-- Table structure for sys_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_operation_log`;
CREATE TABLE `sys_operation_log` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户ID',
  `user_name` varchar(10) DEFAULT NULL COMMENT '用户账号',
  `real_name` varchar(50) DEFAULT NULL COMMENT '真实名字',
  `broswer` varchar(100) DEFAULT NULL COMMENT '浏览器',
  `content` longtext NOT NULL COMMENT '日志内容',
  `level` tinyint(2) DEFAULT NULL COMMENT '日志级别',
  `ip` varchar(100) DEFAULT NULL COMMENT 'IP',
  `req_uri` varchar(255) DEFAULT NULL COMMENT '请求URI',
  `op_type` tinyint(2) DEFAULT NULL COMMENT '操作类型',
  `op_time` datetime NOT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`),
  KEY `uid` (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=221 DEFAULT CHARSET=utf8 COMMENT='系统操作日志';

-- ----------------------------
-- Records of sys_operation_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(20) DEFAULT NULL COMMENT '菜单编号',
  `icon` varchar(30) DEFAULT NULL COMMENT '菜单图标名称',
  `name` varchar(30) DEFAULT NULL COMMENT '菜单名称',
  `parent_id` int(11) DEFAULT NULL COMMENT '父菜单id',
  `parent_ids` varchar(255) DEFAULT NULL COMMENT '父菜单id',
  `seq` int(2) DEFAULT NULL COMMENT '菜单排序',
  `type` int(1) DEFAULT NULL COMMENT '权限分类（0 菜单；1 功能）',
  `descpt` varchar(50) DEFAULT NULL COMMENT '描述',
  `path` varchar(50) DEFAULT NULL COMMENT '菜单url',
  `create_time` datetime DEFAULT NULL COMMENT '添加时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(30) DEFAULT NULL COMMENT '角色名称',
  `descpt` varchar(50) DEFAULT NULL COMMENT '角色描述',
  `code` varchar(20) DEFAULT NULL COMMENT '角色编号',
  `create_user` int(11) DEFAULT NULL COMMENT '操作用户id',
  `create_time` datetime DEFAULT NULL COMMENT '添加数据时间',
  `update_user` int(11) DEFAULT NULL COMMENT '操作用户id',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `permission_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`permission_id`,`role_id`),
  KEY `perm_id` (`permission_id`) USING BTREE,
  KEY `roleid` (`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `real_name` varchar(100) NOT NULL COMMENT '账户名',
  `user_name` varchar(50) DEFAULT '' COMMENT '用户名',
  `telephone` varchar(15) DEFAULT '' COMMENT '手机号',
  `email` varchar(50) DEFAULT '' COMMENT '邮箱',
  `password` varchar(50) DEFAULT '' COMMENT '密码',
  `create_user` int(11) DEFAULT NULL COMMENT '新增用户',
  `create_time` datetime DEFAULT NULL COMMENT '注册时间',
  `update_user` int(11) DEFAULT NULL COMMENT '修改用户',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_del` tinyint(1) DEFAULT '0' COMMENT '是否删除（1正常；0已删）',
  `is_job` tinyint(1) DEFAULT '0' COMMENT '是否在职（1正常；0离职）',
  `version` int(10) DEFAULT '0' COMMENT '更新版本',
  PRIMARY KEY (`id`,`real_name`),
  KEY `username` (`user_name`) USING BTREE,
  KEY `id` (`id`) USING BTREE,
  KEY `telephone` (`telephone`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='系统用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `userid` (`user_id`) USING BTREE,
  KEY `roleid` (`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色关联表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
