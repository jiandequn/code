/*
Navicat MySQL Data Transfer

Source Server         : 陕西2.0数据采集
Source Server Version : 50633
Source Host           : 10.43.127.204:3306
Source Database       : sx_hadoop

Target Server Type    : MYSQL
Target Server Version : 50633
File Encoding         : 65001

Date: 2020-10-23 09:50:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for export_table_info
-- ----------------------------
DROP TABLE IF EXISTS `export_table_info`;
CREATE TABLE `export_table_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `table_id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL COMMENT '导出文件格式名称',
  `query_sql` text,
  `file_format` varchar(255) DEFAULT NULL COMMENT '格式',
  `is_effective` tinyint(2) DEFAULT '1',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='导出table维护信息';

-- ----------------------------
-- Records of export_table_info
-- ----------------------------
INSERT INTO `export_table_info` VALUES ('1', '1', '日新增用户数量', 'SELECT\r\n	SUBSTR(create_time ,1, 10) AS t_date,\r\n	parent_column_id,\r\n	user_type,\r\n	count(user_id) AS user_count\r\nFROM\r\n	user_info\r\nGROUP BY\r\n	t_date,\r\n	parent_column_id,\r\n	user_type', 'csv', '1', '2020-05-07 10:45:56');
INSERT INTO `export_table_info` VALUES ('2', '1', '新增用户信息', '', 'csv', '1', '2020-05-07 10:46:57');
INSERT INTO `export_table_info` VALUES ('3', '1', '累计访问用户数', 'SELECT\r\n	parent_column_id,\r\n	user_type,\r\n	count(1) user_count\r\nFROM\r\n	user_info\r\nGROUP BY\r\n	parent_column_id,\r\n	user_type', 'xlsx', '1', '2020-05-07 10:49:00');
INSERT INTO `export_table_info` VALUES ('5', '2', '专区周访问用户数', 'select parent_column_id,y,w,user_type,sum(page_user_count) as pge_user_count from app_visit_count_week group by parent_column_id,y,w,user_type;', 'csv', '1', '2020-09-16 13:56:17');

-- ----------------------------
-- Table structure for table_column_info
-- ----------------------------
DROP TABLE IF EXISTS `table_column_info`;
CREATE TABLE `table_column_info` (
  `column_id` int(11) NOT NULL AUTO_INCREMENT,
  `export_table_id` int(11) DEFAULT NULL,
  `column_name` varchar(255) NOT NULL,
  `column_comment` varchar(255) DEFAULT NULL,
  `is_effective` tinyint(4) DEFAULT '1',
  `seq` int(11) DEFAULT '0' COMMENT '顺序',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`column_id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of table_column_info
-- ----------------------------
INSERT INTO `table_column_info` VALUES ('5', '1', 't_date', '日期', '1', '1', '2020-05-07 10:46:11');
INSERT INTO `table_column_info` VALUES ('6', '1', 'parent_column_id', '专区栏目ID', '1', '2', '2020-05-07 10:46:11');
INSERT INTO `table_column_info` VALUES ('7', '1', 'user_type', '用户类型', '1', '3', '2020-05-07 10:46:11');
INSERT INTO `table_column_info` VALUES ('8', '1', 'user_count', '用户数', '1', '4', '2020-05-07 10:46:11');
INSERT INTO `table_column_info` VALUES ('9', '2', 'parent_column_id', '专区ID', '1', '1', '2020-05-07 10:46:57');
INSERT INTO `table_column_info` VALUES ('10', '2', 'user_type', '用户类型', '1', '2', '2020-05-07 10:46:57');
INSERT INTO `table_column_info` VALUES ('11', '2', 'user_id', '用户ID', '1', '3', '2020-05-07 10:46:57');
INSERT INTO `table_column_info` VALUES ('12', '2', 'area_code', '区域码', '1', '4', '2020-05-07 10:46:57');
INSERT INTO `table_column_info` VALUES ('13', '2', 'create_time', '创建时间', '1', '5', '2020-05-07 10:46:57');
INSERT INTO `table_column_info` VALUES ('17', '3', 'parent_column_id', '专区ID', '1', '1', '2020-05-07 10:50:51');
INSERT INTO `table_column_info` VALUES ('18', '3', 'user_type', '用户类型', '1', '2', '2020-05-07 10:50:51');
INSERT INTO `table_column_info` VALUES ('19', '3', 'user_count', '累计访问用户数', '1', '3', '2020-05-07 10:50:51');
INSERT INTO `table_column_info` VALUES ('36', '5', 'y', '年份', '1', '1', '2020-09-16 13:59:47');
INSERT INTO `table_column_info` VALUES ('37', '5', 'w', '周', '1', '2', '2020-09-16 13:59:47');
INSERT INTO `table_column_info` VALUES ('38', '5', 'parent_column_id', '专区ID', '1', '3', '2020-09-16 13:59:47');
INSERT INTO `table_column_info` VALUES ('39', '5', 'user_type', '用户类型', '1', '4', '2020-09-16 13:59:47');
INSERT INTO `table_column_info` VALUES ('40', '5', 'page_user_count', '访问用户数', '1', '5', '2020-09-16 13:59:47');

-- ----------------------------
-- Table structure for table_info
-- ----------------------------
DROP TABLE IF EXISTS `table_info`;
CREATE TABLE `table_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `table_name` varchar(255) NOT NULL,
  `table_comment` varchar(255) DEFAULT NULL,
  `is_effective` tinyint(2) NOT NULL DEFAULT '1',
  `update_sql` text COMMENT '更新sql',
  `create_time` datetime DEFAULT NULL,
  `del_flag` tinyint(4) DEFAULT '1' COMMENT '-1已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of table_info
-- ----------------------------
INSERT INTO `table_info` VALUES ('1', 'user_info', '用户信息', '1', '', '2020-05-07 10:43:44', '1');
INSERT INTO `table_info` VALUES ('2', 'app_visit_count_week', '专区按周统计信息', '1', '', '2020-09-16 13:54:40', '1');
