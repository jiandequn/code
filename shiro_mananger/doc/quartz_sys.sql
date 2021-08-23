/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50711
Source Host           : localhost:3306
Source Database       : quartz_sys

Target Server Type    : MYSQL
Target Server Version : 50711
File Encoding         : 65001

Date: 2020-03-30 17:53:36
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
  `code` varchar(25) DEFAULT NULL COMMENT '菜单编号',
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
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1', 'system', '', '系统管理', '0', '0', '100', '0', '系统管理', '/', '2017-12-20 16:22:43', '2020-03-12 14:59:31');
INSERT INTO `sys_permission` VALUES ('2', 'usermanage', '', '用户管理', '1', '0,1', '1100', '0', '用户管理', '/sys/user/toPage', '2017-12-20 16:27:03', '2020-03-12 14:59:40');
INSERT INTO `sys_permission` VALUES ('3', 'rolemanage', '', '角色管理', '1', '0,1', '1200', '0', '角色管理', '/sys/role/toPage', '2017-12-20 16:27:03', '2020-03-12 15:02:00');
INSERT INTO `sys_permission` VALUES ('4', 'permmanage', null, '权限管理', '1', '0,1', '1300', '0', '权限管理', '/sys/permission/toPage', '2017-12-30 19:17:32', '2020-03-12 15:00:32');
INSERT INTO `sys_permission` VALUES ('15', 'sys_user:update', null, '修改', '2', '0,1,2', '1', '1', '', '/', '2019-05-22 11:44:08', '2020-03-27 16:04:39');
INSERT INTO `sys_permission` VALUES ('20', 'sys.log:query', null, '系统日志管理', '1', '0,1', '0', '0', '', '/sys/log/toPage', '2019-06-05 10:25:15', '2020-03-12 15:00:39');
INSERT INTO `sys_permission` VALUES ('27', 'sys_user:del', null, '删除按钮', '2', '0,1,2', '2', '1', '', '/', '2020-03-12 14:45:56', '2020-03-27 16:10:32');
INSERT INTO `sys_permission` VALUES ('28', 'sys_user:resetPwd', null, '重置密码', '2', '0,1,2', '3', '1', '重置用户密码', '/', '2020-03-27 16:02:10', null);
INSERT INTO `sys_permission` VALUES ('29', 'sys_user:bindRole', null, '角色授权', '2', '0,1,2', '4', '1', '绑定角色', '/', '2020-03-27 16:03:05', '2020-03-27 16:04:20');
INSERT INTO `sys_permission` VALUES ('30', 'sys_user:add', null, '新增', '2', '0,1,2', null, '1', '新增用户', '/', '2020-03-27 16:04:13', null);
INSERT INTO `sys_permission` VALUES ('31', 'sys_user:recover', null, '恢复按钮', '2', '0,1,2', null, '0', '恢复已删除的用户', '/', '2020-03-27 16:05:39', null);
INSERT INTO `sys_permission` VALUES ('32', 'sys_user:setJob', null, '职位按钮', '2', '0,1,2', '6', '1', '行的职位是否可以设置', '/', '2020-03-27 16:07:05', null);
INSERT INTO `sys_permission` VALUES ('33', 'sys_role:add', null, '新增', '3', '0,1,3', '1', '1', '', '/', '2020-03-30 17:21:00', null);
INSERT INTO `sys_permission` VALUES ('34', 'sys_role:del', null, '删除', '3', '0,1,3', '2', '1', '', '/', '2020-03-30 17:21:21', null);
INSERT INTO `sys_permission` VALUES ('35', 'sys:update', null, '修改', '3', '0,1,3', '3', '1', '', '/', '2020-03-30 17:21:42', null);
INSERT INTO `sys_permission` VALUES ('36', 'sys_permission:add', null, '新增', '4', '0,1,4', null, '1', '', '/', '2020-03-30 17:36:44', null);
INSERT INTO `sys_permission` VALUES ('38', 'sys_permission:update', null, '修改', '4', '0,1,4', null, '1', '', '/', '2020-03-30 17:39:23', null);
INSERT INTO `sys_permission` VALUES ('39', 'sys_permission:del', null, '删除', '4', '0,1,4', null, '0', '', '/', '2020-03-30 17:39:37', null);

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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '超级管理', '超级管理员', 'superman', null, '2018-01-09 19:28:53', null, '2019-06-06 14:13:41');
INSERT INTO `sys_role` VALUES ('2', '高级管理员', '高级管理员', 'highmanage', null, '2018-01-17 13:53:23', null, '2020-03-12 16:57:48');
INSERT INTO `sys_role` VALUES ('6', '普通角色', '', 'common_role', null, '2019-05-22 11:33:36', null, '2020-03-30 17:46:56');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `permission_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`permission_id`,`role_id`),
  UNIQUE KEY `perm_id` (`permission_id`,`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES ('1', '6');
INSERT INTO `sys_role_permission` VALUES ('2', '6');
INSERT INTO `sys_role_permission` VALUES ('3', '6');
INSERT INTO `sys_role_permission` VALUES ('4', '6');
INSERT INTO `sys_role_permission` VALUES ('15', '6');
INSERT INTO `sys_role_permission` VALUES ('20', '6');
INSERT INTO `sys_role_permission` VALUES ('27', '6');
INSERT INTO `sys_role_permission` VALUES ('28', '6');
INSERT INTO `sys_role_permission` VALUES ('29', '6');
INSERT INTO `sys_role_permission` VALUES ('33', '6');
INSERT INTO `sys_role_permission` VALUES ('34', '6');
INSERT INTO `sys_role_permission` VALUES ('35', '6');
INSERT INTO `sys_role_permission` VALUES ('36', '6');
INSERT INTO `sys_role_permission` VALUES ('38', '6');
INSERT INTO `sys_role_permission` VALUES ('39', '6');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `real_name` varchar(100) NOT NULL COMMENT '账户名',
  `user_name` varchar(50) DEFAULT '' COMMENT '用户名',
  `sex` tinyint(1) NOT NULL DEFAULT '0',
  `telephone` varchar(15) DEFAULT '' COMMENT '手机号',
  `email` varchar(50) DEFAULT '' COMMENT '邮箱',
  `password` varchar(50) DEFAULT '' COMMENT '密码',
  `create_user` varchar(11) DEFAULT NULL COMMENT '新增用户',
  `create_time` datetime DEFAULT NULL COMMENT '注册时间',
  `update_user` varchar(11) DEFAULT NULL COMMENT '修改用户',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_del` tinyint(1) DEFAULT '0' COMMENT '是否删除（1正常；0已删）',
  `is_job` tinyint(1) DEFAULT '0' COMMENT '是否在职（1正常；0离职）',
  `version` int(10) DEFAULT '0' COMMENT '更新版本',
  PRIMARY KEY (`id`,`real_name`),
  KEY `username` (`user_name`) USING BTREE,
  KEY `id` (`id`) USING BTREE,
  KEY `telephone` (`telephone`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COMMENT='系统用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', 'admin', '1', '12333333333', '22@abc.com', 'e10adc3949ba59abbe56e057f20f883e', null, '2020-03-09 15:19:15', 'tt', '2020-03-20 17:34:23', '0', '0', '0');
INSERT INTO `sys_user` VALUES ('2', '1', '1', '0', '1', '1', '1', '1', null, null, '2020-03-27 16:25:24', '1', '1', '0');
INSERT INTO `sys_user` VALUES ('3', 'jiandequn', 'jiandequn', '0', 'jiandequn', 'jiandequn', 'e10adc3949ba59abbe56e057f20f883e', null, null, null, null, '0', '0', '0');
INSERT INTO `sys_user` VALUES ('4', '4', '4', '0', '4', '4', '4', '4', '2020-03-18 15:49:21', null, null, '0', '0', '0');
INSERT INTO `sys_user` VALUES ('5', '5', '5', '0', '13692218271', 'abc@ppfun.com', '96e79218965eb72c92a549dd5a330112', '5', null, 'tt', '2020-03-20 17:26:02', '0', '0', '0');
INSERT INTO `sys_user` VALUES ('6', '简单', 'test1', '0', '11111111111', '5@ppfuns.com', '6', '6', null, 'tt', '2020-03-20 17:28:06', '0', '0', '0');
INSERT INTO `sys_user` VALUES ('29', '2', '22', '0', '13692218270', '111@abc.com', 'e10adc3949ba59abbe56e057f20f883e', 't', '2020-03-18 17:02:18', 'tt', '2020-03-18 17:02:18', '0', '0', '0');
INSERT INTO `sys_user` VALUES ('30', '21', '21', '0', '12345678901', '1', 'e10adc3949ba59abbe56e057f20f883e', 't', '2020-03-18 17:03:12', 'tt', '2020-03-18 17:03:12', '0', '0', '0');
INSERT INTO `sys_user` VALUES ('31', '11', '11', '0', '111111111', '11', 'e10adc3949ba59abbe56e057f20f883e', 't', '2020-03-19 09:59:24', 'tt', '2020-03-19 09:59:24', '0', '0', '0');
INSERT INTO `sys_user` VALUES ('32', '12', '12', '0', '111', '', 'e10adc3949ba59abbe56e057f20f883e', 't', '2020-03-19 09:59:37', 'tt', '2020-03-19 09:59:37', '0', '0', '0');
INSERT INTO `sys_user` VALUES ('33', '1211', '1211', '0', '1211', '121', 'aaa42296669b958c3cee6c0475c8093e', 't', '2020-03-19 10:00:10', 'tt', '2020-03-19 10:00:10', '0', '0', '0');

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
INSERT INTO `sys_user_role` VALUES ('1', '1');
INSERT INTO `sys_user_role` VALUES ('1', '2');
INSERT INTO `sys_user_role` VALUES ('3', '6');
INSERT INTO `sys_user_role` VALUES ('12', '5');
INSERT INTO `sys_user_role` VALUES ('19', '3');
INSERT INTO `sys_user_role` VALUES ('20', '2');
INSERT INTO `sys_user_role` VALUES ('21', '4');
INSERT INTO `sys_user_role` VALUES ('22', '5');
INSERT INTO `sys_user_role` VALUES ('23', '3');
INSERT INTO `sys_user_role` VALUES ('24', '5');
INSERT INTO `sys_user_role` VALUES ('25', '2');
INSERT INTO `sys_user_role` VALUES ('26', '5');
INSERT INTO `sys_user_role` VALUES ('27', '5');
INSERT INTO `sys_user_role` VALUES ('28', '6');
INSERT INTO `sys_user_role` VALUES ('29', '1');
INSERT INTO `sys_user_role` VALUES ('30', '1');
INSERT INTO `sys_user_role` VALUES ('31', '1');
INSERT INTO `sys_user_role` VALUES ('32', '1');
INSERT INTO `sys_user_role` VALUES ('32', '2');
INSERT INTO `sys_user_role` VALUES ('33', '7');
