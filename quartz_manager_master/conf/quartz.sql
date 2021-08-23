/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50711
Source Host           : localhost:3306
Source Database       : quartz

Target Server Type    : MYSQL
Target Server Version : 50711
File Encoding         : 65001

Date: 2019-06-06 15:47:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL COMMENT '菜单名称',
  `pid` int(11) DEFAULT NULL COMMENT '父菜单id',
  `zindex` int(2) DEFAULT NULL COMMENT '菜单排序',
  `istype` int(1) DEFAULT NULL COMMENT '权限分类（0 菜单；1 功能）',
  `descpt` varchar(50) DEFAULT NULL COMMENT '描述',
  `code` varchar(20) DEFAULT NULL COMMENT '菜单编号',
  `icon` varchar(30) DEFAULT NULL COMMENT '菜单图标名称',
  `page` varchar(50) DEFAULT NULL COMMENT '菜单url',
  `insert_time` datetime DEFAULT NULL COMMENT '添加时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('1', '系统管理', '0', '100', '0', '系统管理', 'system', '', '/', '2017-12-20 16:22:43', '2019-05-22 16:39:44');
INSERT INTO `permission` VALUES ('2', '用户管理', '1', '1100', '0', '用户管理', 'usermanage', '', '/user/userList', '2017-12-20 16:27:03', '2019-05-22 11:10:09');
INSERT INTO `permission` VALUES ('3', '角色管理', '1', '1200', '0', '角色管理', 'rolemanage', '', '/auth/roleManage', '2017-12-20 16:27:03', '2018-01-09 19:26:42');
INSERT INTO `permission` VALUES ('4', '权限管理', '1', '1300', '0', '权限管理', 'permmanage', null, '/auth/permList', '2017-12-30 19:17:32', '2018-01-09 19:26:48');
INSERT INTO `permission` VALUES ('15', '提交权限', '2', '1', '1', '', 'sys:addUser', null, '/', '2019-05-22 11:44:08', '2019-05-22 17:07:19');
INSERT INTO `permission` VALUES ('16', '定时任务管理', '0', '1', '0', '', 'quartz', null, '/', '2019-05-27 10:52:18', '2019-05-27 10:52:34');
INSERT INTO `permission` VALUES ('17', '任务管理', '16', '1', '0', '', 'schedule', null, '/quartz/list', '2019-05-27 10:53:09', '2019-05-28 14:11:39');
INSERT INTO `permission` VALUES ('18', '查询', '17', '1', '1', '', 'quartz:getList', null, '/', '2019-05-28 14:17:54', '2019-05-28 14:18:02');
INSERT INTO `permission` VALUES ('19', '新增', '18', '2', '1', '', 'quartz:add', null, '/', '2019-05-28 14:18:23', null);
INSERT INTO `permission` VALUES ('20', '系统日志管理', '1', '0', '0', '', 'sys.log:query', null, '/sys/log/toPage', '2019-06-05 10:25:15', '2019-06-05 10:26:46');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(30) DEFAULT NULL COMMENT '角色名称',
  `descpt` varchar(50) DEFAULT NULL COMMENT '角色描述',
  `code` varchar(20) DEFAULT NULL COMMENT '角色编号',
  `insert_uid` int(11) DEFAULT NULL COMMENT '操作用户id',
  `insert_time` datetime DEFAULT NULL COMMENT '添加数据时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '超级管理', '超级管理员', 'superman', null, '2018-01-09 19:28:53', '2019-06-06 14:13:41');
INSERT INTO `role` VALUES ('2', '高级管理员', '高级管理员', 'highmanage', null, '2018-01-17 13:53:23', '2018-01-18 13:39:29');
INSERT INTO `role` VALUES ('6', '普通角色', '', 'common_role', null, '2019-05-22 11:33:36', '2019-05-27 10:53:53');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `permit_id` int(5) NOT NULL AUTO_INCREMENT,
  `role_id` int(5) NOT NULL,
  PRIMARY KEY (`permit_id`,`role_id`),
  KEY `perimitid` (`permit_id`) USING BTREE,
  KEY `roleid` (`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES ('1', '1');
INSERT INTO `role_permission` VALUES ('1', '2');
INSERT INTO `role_permission` VALUES ('1', '6');
INSERT INTO `role_permission` VALUES ('2', '1');
INSERT INTO `role_permission` VALUES ('2', '2');
INSERT INTO `role_permission` VALUES ('2', '6');
INSERT INTO `role_permission` VALUES ('3', '1');
INSERT INTO `role_permission` VALUES ('3', '2');
INSERT INTO `role_permission` VALUES ('4', '1');
INSERT INTO `role_permission` VALUES ('5', '2');
INSERT INTO `role_permission` VALUES ('6', '2');
INSERT INTO `role_permission` VALUES ('8', '2');
INSERT INTO `role_permission` VALUES ('10', '2');
INSERT INTO `role_permission` VALUES ('11', '2');
INSERT INTO `role_permission` VALUES ('12', '2');
INSERT INTO `role_permission` VALUES ('13', '2');
INSERT INTO `role_permission` VALUES ('14', '2');
INSERT INTO `role_permission` VALUES ('15', '1');
INSERT INTO `role_permission` VALUES ('16', '1');
INSERT INTO `role_permission` VALUES ('16', '6');
INSERT INTO `role_permission` VALUES ('17', '1');
INSERT INTO `role_permission` VALUES ('17', '6');
INSERT INTO `role_permission` VALUES ('18', '1');
INSERT INTO `role_permission` VALUES ('19', '1');
INSERT INTO `role_permission` VALUES ('20', '1');

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
  `runing_status` tinyint(4) DEFAULT NULL COMMENT '任务运行状态0表示已停止 ',
  `job_status` tinyint(4) DEFAULT NULL COMMENT '任务有效状态',
  `job_data` text COMMENT '调用方法的数据',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `is_effective` tinyint(4) DEFAULT '1' COMMENT '是否可用 0不可用，1可用',
  PRIMARY KEY (`job_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of schedule_job
-- ----------------------------
INSERT INTO `schedule_job` VALUES ('1', '测试', '测试', '1', '0/10 * * * * ?', 'HttpScheduleMethod', 'com.example.schedule.method.HttpScheduleMethod', 'test', '0', '1', null, null, null, '1');

-- ----------------------------
-- Table structure for t_sys_log
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_log`;
CREATE TABLE `t_sys_log` (
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
) ENGINE=MyISAM AUTO_INCREMENT=47 DEFAULT CHARSET=utf8 COMMENT='系统操作日志';

-- ----------------------------
-- Records of t_sys_log
-- ----------------------------
INSERT INTO `t_sys_log` VALUES ('1', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-05 10:09:17');
INSERT INTO `t_sys_log` VALUES ('2', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-05 10:23:35');
INSERT INTO `t_sys_log` VALUES ('3', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-05 10:30:42');
INSERT INTO `t_sys_log` VALUES ('4', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-05 14:25:38');
INSERT INTO `t_sys_log` VALUES ('5', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-05 14:27:54');
INSERT INTO `t_sys_log` VALUES ('6', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-05 14:29:32');
INSERT INTO `t_sys_log` VALUES ('7', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-05 10:09:17');
INSERT INTO `t_sys_log` VALUES ('8', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-05 10:23:35');
INSERT INTO `t_sys_log` VALUES ('9', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-05 10:30:42');
INSERT INTO `t_sys_log` VALUES ('10', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-05 14:25:38');
INSERT INTO `t_sys_log` VALUES ('11', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-05 14:27:54');
INSERT INTO `t_sys_log` VALUES ('12', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-05 14:29:32');
INSERT INTO `t_sys_log` VALUES ('13', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-05 14:33:09');
INSERT INTO `t_sys_log` VALUES ('14', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-05 14:33:11');
INSERT INTO `t_sys_log` VALUES ('15', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-05 14:45:45');
INSERT INTO `t_sys_log` VALUES ('16', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-05 14:49:57');
INSERT INTO `t_sys_log` VALUES ('17', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-05 14:52:53');
INSERT INTO `t_sys_log` VALUES ('18', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-05 15:13:24');
INSERT INTO `t_sys_log` VALUES ('19', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-05 15:23:50');
INSERT INTO `t_sys_log` VALUES ('20', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-05 15:25:40');
INSERT INTO `t_sys_log` VALUES ('21', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-05 15:28:08');
INSERT INTO `t_sys_log` VALUES ('22', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-05 15:32:17');
INSERT INTO `t_sys_log` VALUES ('23', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-05 15:34:27');
INSERT INTO `t_sys_log` VALUES ('24', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-06 09:50:37');
INSERT INTO `t_sys_log` VALUES ('25', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-06 10:26:05');
INSERT INTO `t_sys_log` VALUES ('26', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-06 10:27:58');
INSERT INTO `t_sys_log` VALUES ('27', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-06 10:28:24');
INSERT INTO `t_sys_log` VALUES ('28', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-06 10:40:45');
INSERT INTO `t_sys_log` VALUES ('29', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-06 10:43:47');
INSERT INTO `t_sys_log` VALUES ('30', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-06 10:46:47');
INSERT INTO `t_sys_log` VALUES ('31', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-06 10:47:21');
INSERT INTO `t_sys_log` VALUES ('32', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-06 10:58:38');
INSERT INTO `t_sys_log` VALUES ('33', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-06 10:58:44');
INSERT INTO `t_sys_log` VALUES ('34', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-06 11:10:19');
INSERT INTO `t_sys_log` VALUES ('35', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-06 11:10:19');
INSERT INTO `t_sys_log` VALUES ('36', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-06 11:22:15');
INSERT INTO `t_sys_log` VALUES ('37', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-06 11:26:09');
INSERT INTO `t_sys_log` VALUES ('38', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-06 11:43:00');
INSERT INTO `t_sys_log` VALUES ('39', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-06 12:09:35');
INSERT INTO `t_sys_log` VALUES ('40', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-06 12:09:57');
INSERT INTO `t_sys_log` VALUES ('41', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-06 12:11:06');
INSERT INTO `t_sys_log` VALUES ('42', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-06 13:58:40');
INSERT INTO `t_sys_log` VALUES ('43', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-06 14:25:24');
INSERT INTO `t_sys_log` VALUES ('44', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-06 14:28:07');
INSERT INTO `t_sys_log` VALUES ('45', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-06 14:32:03');
INSERT INTO `t_sys_log` VALUES ('46', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-06 14:41:01');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `real_name` varchar(100) NOT NULL COMMENT '账户名',
  `username` varchar(50) DEFAULT '' COMMENT '用户名',
  `mobile` varchar(15) DEFAULT '' COMMENT '手机号',
  `email` varchar(50) DEFAULT '' COMMENT '邮箱',
  `password` varchar(50) DEFAULT '' COMMENT '密码',
  `insert_uid` int(11) DEFAULT NULL COMMENT '添加该用户的用户id',
  `insert_time` datetime DEFAULT NULL COMMENT '注册时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_del` tinyint(1) DEFAULT '0' COMMENT '是否删除（0：正常；1：已删）',
  `is_job` tinyint(1) DEFAULT '0' COMMENT '是否在职（0：正常；1，离职）',
  `mcode` varchar(10) DEFAULT '' COMMENT '短信验证码',
  `send_time` datetime DEFAULT NULL COMMENT '短信发送时间',
  `version` int(10) DEFAULT '0' COMMENT '更新版本',
  PRIMARY KEY (`id`,`real_name`),
  KEY `name` (`username`) USING BTREE,
  KEY `id` (`id`) USING BTREE,
  KEY `mobile` (`mobile`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', 'wyait', '12316596566', 'aaa', 'c33367701511b4f6020ec61ded352059', null, '2017-12-29 17:27:23', '2018-01-09 13:34:33', '0', '0', '181907', '2018-01-17 13:42:45', '0');
INSERT INTO `user` VALUES ('28', 'jiandequn', 'jiandequn', '13692218270', 'aaa', 'e10adc3949ba59abbe56e057f20f883e', '1', '2019-05-22 11:34:28', '2019-06-06 14:12:30', '0', '1', null, null, '9');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(5) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `userid` (`user_id`) USING BTREE,
  KEY `roleid` (`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1');
INSERT INTO `user_role` VALUES ('3', '5');
INSERT INTO `user_role` VALUES ('12', '5');
INSERT INTO `user_role` VALUES ('19', '3');
INSERT INTO `user_role` VALUES ('20', '2');
INSERT INTO `user_role` VALUES ('21', '4');
INSERT INTO `user_role` VALUES ('22', '5');
INSERT INTO `user_role` VALUES ('23', '3');
INSERT INTO `user_role` VALUES ('24', '5');
INSERT INTO `user_role` VALUES ('25', '2');
INSERT INTO `user_role` VALUES ('26', '5');
INSERT INTO `user_role` VALUES ('27', '5');
INSERT INTO `user_role` VALUES ('28', '6');
