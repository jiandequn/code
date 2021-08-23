/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50711
Source Host           : localhost:3306
Source Database       : quartz

Target Server Type    : MYSQL
Target Server Version : 50711
File Encoding         : 65001

Date: 2020-03-06 17:56:54
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
  `parent_ids` varchar(255) DEFAULT NULL COMMENT '父类ID组',
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
INSERT INTO `permission` VALUES ('1', '系统管理', '0', null, '100', '0', '系统管理', 'system', '', '/', '2017-12-20 16:22:43', '2019-05-22 16:39:44');
INSERT INTO `permission` VALUES ('2', '用户管理', '1', null, '1100', '0', '用户管理', 'usermanage', '', '/user/userList', '2017-12-20 16:27:03', '2019-05-22 11:10:09');
INSERT INTO `permission` VALUES ('3', '角色管理', '1', null, '1200', '0', '角色管理', 'rolemanage', '', '/auth/roleManage', '2017-12-20 16:27:03', '2018-01-09 19:26:42');
INSERT INTO `permission` VALUES ('4', '权限管理', '1', null, '1300', '0', '权限管理', 'permmanage', null, '/auth/permList', '2017-12-30 19:17:32', '2018-01-09 19:26:48');
INSERT INTO `permission` VALUES ('15', '提交权限', '2', null, '1', '1', '', 'sys:addUser', null, '/', '2019-05-22 11:44:08', '2019-05-22 17:07:19');
INSERT INTO `permission` VALUES ('16', '定时任务管理', '0', null, '1', '0', '', 'quartz', null, '/', '2019-05-27 10:52:18', '2019-05-27 10:52:34');
INSERT INTO `permission` VALUES ('17', '任务管理', '16', null, '1', '0', '', 'schedule', null, '/quartz/list', '2019-05-27 10:53:09', '2019-05-28 14:11:39');
INSERT INTO `permission` VALUES ('18', '查询', '17', null, '1', '1', '', 'quartz:getList', null, '/', '2019-05-28 14:17:54', '2019-05-28 14:18:02');
INSERT INTO `permission` VALUES ('19', '新增', '18', null, '2', '1', '', 'quartz:add', null, '/', '2019-05-28 14:18:23', null);
INSERT INTO `permission` VALUES ('20', '系统日志管理', '1', null, '0', '0', '', 'sys.log:query', null, '/sys/log/toPage', '2019-06-05 10:25:15', '2019-06-05 10:26:46');

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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '超级管理', '超级管理员', 'superman', null, '2018-01-09 19:28:53', '2019-06-06 14:13:41');
INSERT INTO `role` VALUES ('2', '高级管理员', '高级管理员', 'highmanage', null, '2018-01-17 13:53:23', '2018-01-18 13:39:29');
INSERT INTO `role` VALUES ('6', '普通角色', '', 'common_role', null, '2019-05-22 11:33:36', '2019-05-27 10:53:53');
INSERT INTO `role` VALUES ('7', '22', '', '22', null, '2020-03-06 15:32:46', null);

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
INSERT INTO `role_permission` VALUES ('16', '7');
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
INSERT INTO `schedule_job` VALUES ('1', '测试2', '测试', '1', '1/10 * * * * ?', 'HttpScheduleMethod', 'com.example.schedule.method.HttpScheduleMethod', 'test', '0', '1', null, '2020-03-03 09:42:32', '2020-03-03 09:42:34', '0');
INSERT INTO `schedule_job` VALUES ('2', '测试1', '测试', null, '1/5 * * * * ?', null, 'com.example.schedule.method.HttpScheduleMethod', 'test1', '-1', '0', null, null, '2020-03-05 15:35:37', null);
INSERT INTO `schedule_job` VALUES ('3', '测试3', '测试', null, '2', null, '2', '2', null, '0', null, '2020-03-05 15:16:26', '2020-03-05 15:27:05', null);
INSERT INTO `schedule_job` VALUES ('4', '1', '1', null, '2', null, '2', '', null, '0', null, '2020-03-05 15:44:36', '2020-03-05 15:44:36', null);
INSERT INTO `schedule_job` VALUES ('5', '2', '测试', null, '2', null, '2', '', null, '0', null, '2020-03-05 15:44:54', '2020-03-05 15:44:54', null);
INSERT INTO `schedule_job` VALUES ('6', '3', '测试', null, '3', null, '3', '', null, '0', null, '2020-03-05 15:45:08', '2020-03-05 15:45:08', null);
INSERT INTO `schedule_job` VALUES ('7', '4', '测试', null, '4', null, '4', '4', null, '0', null, '2020-03-05 15:45:20', '2020-03-05 15:45:20', null);
INSERT INTO `schedule_job` VALUES ('8', '5', '测试', null, '6', null, '66', '6', null, '0', null, '2020-03-05 15:45:29', '2020-03-05 15:45:29', null);
INSERT INTO `schedule_job` VALUES ('9', '7', '测试', null, '6', null, '6', '7', null, '0', null, '2020-03-05 15:45:42', '2020-03-05 15:45:42', null);
INSERT INTO `schedule_job` VALUES ('10', '8', '测试', null, '8', null, '8', '8', null, '0', null, '2020-03-05 15:45:58', '2020-03-05 15:50:24', null);
INSERT INTO `schedule_job` VALUES ('11', '9', '测试', null, '9', null, '9', '9', null, '0', null, '2020-03-05 15:46:08', '2020-03-05 15:56:17', null);
INSERT INTO `schedule_job` VALUES ('12', '10', '测试', null, '101', null, '10', '', null, '0', null, '2020-03-05 15:46:49', '2020-03-05 15:53:54', null);

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
) ENGINE=MyISAM AUTO_INCREMENT=221 DEFAULT CHARSET=utf8 COMMENT='系统操作日志';

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
INSERT INTO `t_sys_log` VALUES ('47', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-06 17:40:11');
INSERT INTO `t_sys_log` VALUES ('48', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-06 17:42:36');
INSERT INTO `t_sys_log` VALUES ('49', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-06 17:46:18');
INSERT INTO `t_sys_log` VALUES ('50', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-06 17:48:42');
INSERT INTO `t_sys_log` VALUES ('51', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-06 17:50:20');
INSERT INTO `t_sys_log` VALUES ('52', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-06 17:56:36');
INSERT INTO `t_sys_log` VALUES ('53', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-06 17:59:20');
INSERT INTO `t_sys_log` VALUES ('54', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-06 18:00:46');
INSERT INTO `t_sys_log` VALUES ('55', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-06 18:09:03');
INSERT INTO `t_sys_log` VALUES ('56', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-06 18:11:36');
INSERT INTO `t_sys_log` VALUES ('57', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-06 18:13:30');
INSERT INTO `t_sys_log` VALUES ('58', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-06-06 18:17:29');
INSERT INTO `t_sys_log` VALUES ('59', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-07-15 18:15:02');
INSERT INTO `t_sys_log` VALUES ('60', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-07-15 18:15:02');
INSERT INTO `t_sys_log` VALUES ('61', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-08-26 17:20:45');
INSERT INTO `t_sys_log` VALUES ('62', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-08-26 17:41:45');
INSERT INTO `t_sys_log` VALUES ('63', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-08-26 18:03:33');
INSERT INTO `t_sys_log` VALUES ('64', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2019-08-26 18:10:06');
INSERT INTO `t_sys_log` VALUES ('65', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-02 16:53:13');
INSERT INTO `t_sys_log` VALUES ('66', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-02 16:53:48');
INSERT INTO `t_sys_log` VALUES ('67', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-02 17:11:33');
INSERT INTO `t_sys_log` VALUES ('68', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-02 17:21:47');
INSERT INTO `t_sys_log` VALUES ('69', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-02 17:23:50');
INSERT INTO `t_sys_log` VALUES ('70', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-02 17:27:19');
INSERT INTO `t_sys_log` VALUES ('71', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-02 17:29:35');
INSERT INTO `t_sys_log` VALUES ('72', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-02 17:35:58');
INSERT INTO `t_sys_log` VALUES ('73', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-02 17:39:04');
INSERT INTO `t_sys_log` VALUES ('74', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-02 17:40:47');
INSERT INTO `t_sys_log` VALUES ('75', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-02 17:42:32');
INSERT INTO `t_sys_log` VALUES ('76', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-03 09:34:10');
INSERT INTO `t_sys_log` VALUES ('77', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-03 09:53:55');
INSERT INTO `t_sys_log` VALUES ('78', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-03 09:56:36');
INSERT INTO `t_sys_log` VALUES ('79', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-03 09:57:56');
INSERT INTO `t_sys_log` VALUES ('80', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-03 09:59:09');
INSERT INTO `t_sys_log` VALUES ('81', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-03 10:01:11');
INSERT INTO `t_sys_log` VALUES ('82', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-03 10:07:26');
INSERT INTO `t_sys_log` VALUES ('83', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-03 10:13:42');
INSERT INTO `t_sys_log` VALUES ('84', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-03 10:17:19');
INSERT INTO `t_sys_log` VALUES ('85', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-03 10:50:30');
INSERT INTO `t_sys_log` VALUES ('86', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-03 11:07:18');
INSERT INTO `t_sys_log` VALUES ('87', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-03 11:10:06');
INSERT INTO `t_sys_log` VALUES ('88', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-03 11:19:03');
INSERT INTO `t_sys_log` VALUES ('89', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-03 11:23:47');
INSERT INTO `t_sys_log` VALUES ('90', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-03 11:27:49');
INSERT INTO `t_sys_log` VALUES ('91', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-03 11:29:26');
INSERT INTO `t_sys_log` VALUES ('92', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-03 11:32:16');
INSERT INTO `t_sys_log` VALUES ('93', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-03 11:32:44');
INSERT INTO `t_sys_log` VALUES ('94', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-03 11:33:46');
INSERT INTO `t_sys_log` VALUES ('95', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-03 11:34:30');
INSERT INTO `t_sys_log` VALUES ('96', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-03 11:35:02');
INSERT INTO `t_sys_log` VALUES ('97', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-03 15:32:14');
INSERT INTO `t_sys_log` VALUES ('98', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-03 15:40:52');
INSERT INTO `t_sys_log` VALUES ('99', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-03 15:45:02');
INSERT INTO `t_sys_log` VALUES ('100', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-03 15:47:12');
INSERT INTO `t_sys_log` VALUES ('101', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-03 16:15:37');
INSERT INTO `t_sys_log` VALUES ('102', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-03 16:19:55');
INSERT INTO `t_sys_log` VALUES ('103', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-03 16:23:47');
INSERT INTO `t_sys_log` VALUES ('104', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-03 16:31:24');
INSERT INTO `t_sys_log` VALUES ('105', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-03 16:34:29');
INSERT INTO `t_sys_log` VALUES ('106', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-03 16:36:03');
INSERT INTO `t_sys_log` VALUES ('107', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-03 16:40:02');
INSERT INTO `t_sys_log` VALUES ('108', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-03 16:51:46');
INSERT INTO `t_sys_log` VALUES ('109', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-03 16:58:37');
INSERT INTO `t_sys_log` VALUES ('110', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-03 17:02:37');
INSERT INTO `t_sys_log` VALUES ('111', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-03 17:15:26');
INSERT INTO `t_sys_log` VALUES ('112', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-03 17:17:04');
INSERT INTO `t_sys_log` VALUES ('113', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-03 17:56:57');
INSERT INTO `t_sys_log` VALUES ('114', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-04 09:25:03');
INSERT INTO `t_sys_log` VALUES ('115', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-04 09:27:26');
INSERT INTO `t_sys_log` VALUES ('116', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-04 09:59:30');
INSERT INTO `t_sys_log` VALUES ('117', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-04 10:06:41');
INSERT INTO `t_sys_log` VALUES ('118', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-04 10:08:22');
INSERT INTO `t_sys_log` VALUES ('119', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-04 10:09:41');
INSERT INTO `t_sys_log` VALUES ('120', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-04 10:14:28');
INSERT INTO `t_sys_log` VALUES ('121', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-04 10:22:01');
INSERT INTO `t_sys_log` VALUES ('122', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-04 10:34:13');
INSERT INTO `t_sys_log` VALUES ('123', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-04 10:47:02');
INSERT INTO `t_sys_log` VALUES ('124', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-04 11:09:48');
INSERT INTO `t_sys_log` VALUES ('125', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-04 11:09:48');
INSERT INTO `t_sys_log` VALUES ('126', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-04 11:09:45');
INSERT INTO `t_sys_log` VALUES ('127', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-04 11:09:48');
INSERT INTO `t_sys_log` VALUES ('128', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-04 11:11:10');
INSERT INTO `t_sys_log` VALUES ('129', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-04 11:40:25');
INSERT INTO `t_sys_log` VALUES ('130', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-04 11:50:11');
INSERT INTO `t_sys_log` VALUES ('131', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-04 11:59:03');
INSERT INTO `t_sys_log` VALUES ('132', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-04 11:59:07');
INSERT INTO `t_sys_log` VALUES ('133', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-04 11:59:07');
INSERT INTO `t_sys_log` VALUES ('134', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-04 11:59:07');
INSERT INTO `t_sys_log` VALUES ('135', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-04 11:59:07');
INSERT INTO `t_sys_log` VALUES ('136', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-04 11:59:07');
INSERT INTO `t_sys_log` VALUES ('137', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-04 11:59:07');
INSERT INTO `t_sys_log` VALUES ('138', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-04 12:00:19');
INSERT INTO `t_sys_log` VALUES ('139', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-04 14:01:37');
INSERT INTO `t_sys_log` VALUES ('140', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-04 14:15:26');
INSERT INTO `t_sys_log` VALUES ('141', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-04 14:42:54');
INSERT INTO `t_sys_log` VALUES ('142', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-04 14:47:01');
INSERT INTO `t_sys_log` VALUES ('143', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-04 15:10:00');
INSERT INTO `t_sys_log` VALUES ('144', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-04 15:11:13');
INSERT INTO `t_sys_log` VALUES ('145', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-04 15:11:52');
INSERT INTO `t_sys_log` VALUES ('146', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-04 15:12:41');
INSERT INTO `t_sys_log` VALUES ('147', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-04 15:22:03');
INSERT INTO `t_sys_log` VALUES ('148', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-04 15:30:43');
INSERT INTO `t_sys_log` VALUES ('149', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-05 11:11:42');
INSERT INTO `t_sys_log` VALUES ('150', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-05 11:15:56');
INSERT INTO `t_sys_log` VALUES ('151', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-05 11:25:32');
INSERT INTO `t_sys_log` VALUES ('152', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-05 11:28:15');
INSERT INTO `t_sys_log` VALUES ('153', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-05 11:31:47');
INSERT INTO `t_sys_log` VALUES ('154', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-05 11:32:52');
INSERT INTO `t_sys_log` VALUES ('155', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-05 11:36:42');
INSERT INTO `t_sys_log` VALUES ('156', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-05 11:38:12');
INSERT INTO `t_sys_log` VALUES ('157', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-05 11:40:08');
INSERT INTO `t_sys_log` VALUES ('158', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-05 11:41:24');
INSERT INTO `t_sys_log` VALUES ('159', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-05 11:42:28');
INSERT INTO `t_sys_log` VALUES ('160', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-05 11:43:19');
INSERT INTO `t_sys_log` VALUES ('161', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-05 11:43:48');
INSERT INTO `t_sys_log` VALUES ('162', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-05 11:45:13');
INSERT INTO `t_sys_log` VALUES ('163', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-05 11:46:21');
INSERT INTO `t_sys_log` VALUES ('164', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-05 11:50:39');
INSERT INTO `t_sys_log` VALUES ('165', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-05 15:10:45');
INSERT INTO `t_sys_log` VALUES ('166', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-05 15:15:12');
INSERT INTO `t_sys_log` VALUES ('167', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-05 15:20:39');
INSERT INTO `t_sys_log` VALUES ('168', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-05 15:25:52');
INSERT INTO `t_sys_log` VALUES ('169', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-05 15:35:18');
INSERT INTO `t_sys_log` VALUES ('170', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-05 15:44:21');
INSERT INTO `t_sys_log` VALUES ('171', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-05 15:50:03');
INSERT INTO `t_sys_log` VALUES ('172', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-05 15:53:47');
INSERT INTO `t_sys_log` VALUES ('173', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-05 15:56:06');
INSERT INTO `t_sys_log` VALUES ('174', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-05 15:58:12');
INSERT INTO `t_sys_log` VALUES ('175', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-05 16:18:19');
INSERT INTO `t_sys_log` VALUES ('176', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-05 16:23:04');
INSERT INTO `t_sys_log` VALUES ('177', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-05 16:24:29');
INSERT INTO `t_sys_log` VALUES ('178', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-05 16:26:50');
INSERT INTO `t_sys_log` VALUES ('179', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-05 16:27:38');
INSERT INTO `t_sys_log` VALUES ('180', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-05 16:36:49');
INSERT INTO `t_sys_log` VALUES ('181', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-05 16:39:15');
INSERT INTO `t_sys_log` VALUES ('182', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-05 17:01:12');
INSERT INTO `t_sys_log` VALUES ('183', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-05 17:06:12');
INSERT INTO `t_sys_log` VALUES ('184', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-05 17:07:48');
INSERT INTO `t_sys_log` VALUES ('185', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-05 17:13:04');
INSERT INTO `t_sys_log` VALUES ('186', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-05 17:15:55');
INSERT INTO `t_sys_log` VALUES ('187', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-05 17:17:57');
INSERT INTO `t_sys_log` VALUES ('188', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-05 17:19:41');
INSERT INTO `t_sys_log` VALUES ('189', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-05 17:22:56');
INSERT INTO `t_sys_log` VALUES ('190', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-05 17:23:39');
INSERT INTO `t_sys_log` VALUES ('191', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-05 17:25:10');
INSERT INTO `t_sys_log` VALUES ('192', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-05 17:30:57');
INSERT INTO `t_sys_log` VALUES ('193', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-05 17:32:38');
INSERT INTO `t_sys_log` VALUES ('194', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-05 17:33:18');
INSERT INTO `t_sys_log` VALUES ('195', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-06 09:53:51');
INSERT INTO `t_sys_log` VALUES ('196', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-06 10:02:21');
INSERT INTO `t_sys_log` VALUES ('197', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-06 10:08:10');
INSERT INTO `t_sys_log` VALUES ('198', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-06 10:09:09');
INSERT INTO `t_sys_log` VALUES ('199', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-06 11:15:41');
INSERT INTO `t_sys_log` VALUES ('200', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-06 11:42:03');
INSERT INTO `t_sys_log` VALUES ('201', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-06 12:03:49');
INSERT INTO `t_sys_log` VALUES ('202', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-06 12:10:56');
INSERT INTO `t_sys_log` VALUES ('203', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-06 13:51:03');
INSERT INTO `t_sys_log` VALUES ('204', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-06 14:02:47');
INSERT INTO `t_sys_log` VALUES ('205', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-06 14:04:04');
INSERT INTO `t_sys_log` VALUES ('206', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-06 14:05:31');
INSERT INTO `t_sys_log` VALUES ('207', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-06 14:08:00');
INSERT INTO `t_sys_log` VALUES ('208', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-06 14:21:15');
INSERT INTO `t_sys_log` VALUES ('209', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-06 14:24:25');
INSERT INTO `t_sys_log` VALUES ('210', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-06 14:29:01');
INSERT INTO `t_sys_log` VALUES ('211', '28', 'jiandequn', 'jiandequn', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-06 14:32:09');
INSERT INTO `t_sys_log` VALUES ('212', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-06 14:33:24');
INSERT INTO `t_sys_log` VALUES ('213', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-06 14:48:36');
INSERT INTO `t_sys_log` VALUES ('214', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-06 14:50:48');
INSERT INTO `t_sys_log` VALUES ('215', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-06 14:54:07');
INSERT INTO `t_sys_log` VALUES ('216', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-06 14:55:42');
INSERT INTO `t_sys_log` VALUES ('217', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-06 15:19:40');
INSERT INTO `t_sys_log` VALUES ('218', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-06 15:23:03');
INSERT INTO `t_sys_log` VALUES ('219', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-06 15:28:34');
INSERT INTO `t_sys_log` VALUES ('220', '1', 'wyait', 'admin', 'Chrome', '用户登录', '1', '本地', '/user/login', '1', '2020-03-06 15:31:25');

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
INSERT INTO `user` VALUES ('1', 'admin', 'wyait', '12316596566', 'aaa', 'c33367701511b4f6020ec61ded352059', '28', '2017-12-29 17:27:23', '2020-03-03 11:34:46', '0', '0', '181907', '2018-01-17 13:42:45', '4');
INSERT INTO `user` VALUES ('28', '简德群', 'jiandequn', '13692218270', 'abc22@com.pp', 'e10adc3949ba59abbe56e057f20f883e', '28', '2019-05-22 11:34:28', '2020-03-06 14:32:22', '0', '0', null, null, '23');

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
