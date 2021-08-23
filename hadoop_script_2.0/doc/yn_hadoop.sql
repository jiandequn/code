/*
Navicat MySQL Data Transfer

Source Server         : 10.131.45.116
Source Server Version : 50633
Source Host           : 10.131.45.116:3306
Source Database       : yn_hadoop

Target Server Type    : MYSQL
Target Server Version : 50633
File Encoding         : 65001

Date: 2020-08-25 15:27:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for album_play_count_rank_day
-- ----------------------------
DROP TABLE IF EXISTS `album_play_count_rank_day`;
CREATE TABLE `album_play_count_rank_day` (
  `t_date` varchar(255) NOT NULL,
  `parent_column_id` varchar(255) NOT NULL,
  `album_id` int(11) NOT NULL,
  `album_name` varchar(255) DEFAULT NULL,
  `content_type` varchar(11) DEFAULT NULL,
  `content_type_name` varchar(255) DEFAULT NULL,
  `play_count` bigint(20) DEFAULT NULL,
  UNIQUE KEY `t_date` (`t_date`,`parent_column_id`,`album_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='专辑播放次数排行榜';

-- ----------------------------
-- Table structure for album_play_count_rank_week
-- ----------------------------
DROP TABLE IF EXISTS `album_play_count_rank_week`;
CREATE TABLE `album_play_count_rank_week` (
  `y` int(4) NOT NULL,
  `w` int(2) NOT NULL,
  `parent_column_id` varchar(255) NOT NULL,
  `album_id` int(11) NOT NULL,
  `album_name` varchar(255) DEFAULT NULL,
  `content_type` int(11) DEFAULT NULL,
  `content_type_name` varchar(255) DEFAULT NULL,
  `play_count` bigint(20) DEFAULT NULL,
  UNIQUE KEY `t_date` (`y`,`w`,`parent_column_id`,`album_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='专辑播放次数排行榜';

-- ----------------------------
-- Table structure for album_play_log
-- ----------------------------
DROP TABLE IF EXISTS `album_play_log`;
CREATE TABLE `album_play_log` (
  `parent_column_id` varchar(64) DEFAULT NULL COMMENT '父栏目',
  `user_type` char(3) DEFAULT NULL COMMENT '用户类型',
  `mac` varchar(64) DEFAULT NULL COMMENT 'MAC',
  `sn` varchar(64) DEFAULT NULL COMMENT 'SN',
  `user_id` varchar(64) DEFAULT NULL COMMENT '用户ID',
  `album_id` varchar(64) DEFAULT NULL COMMENT '专辑ID',
  `album_name` varchar(255) DEFAULT NULL COMMENT '专辑名称',
  `content_type` varchar(64) DEFAULT NULL COMMENT '专辑类型',
  `content_type_name` varchar(64) DEFAULT NULL COMMENT '专辑类型名称',
  `labels` varchar(64) DEFAULT NULL COMMENT '二级类型',
  `video_id` varchar(64) DEFAULT NULL COMMENT '剧集ID',
  `video_name` varchar(255) DEFAULT NULL COMMENT '剧集名称',
  `video_duration` varchar(100) DEFAULT NULL COMMENT '剧集时长',
  `time_position` varchar(100) DEFAULT NULL COMMENT '播放时长',
  `create_time` varchar(64) DEFAULT NULL COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='历史播放日志';

-- ----------------------------
-- Table structure for album_user_count_rank_day
-- ----------------------------
DROP TABLE IF EXISTS `album_user_count_rank_day`;
CREATE TABLE `album_user_count_rank_day` (
  `t_date` varchar(255) NOT NULL,
  `parent_column_id` varchar(255) NOT NULL,
  `album_id` int(11) NOT NULL,
  `album_name` varchar(255) DEFAULT NULL,
  `content_type` varchar(11) DEFAULT NULL,
  `content_type_name` varchar(255) DEFAULT NULL,
  `user_count` bigint(20) DEFAULT NULL,
  UNIQUE KEY `t_date` (`t_date`,`parent_column_id`,`album_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='专辑播放用户数排行榜';

-- ----------------------------
-- Table structure for album_user_count_rank_week
-- ----------------------------
DROP TABLE IF EXISTS `album_user_count_rank_week`;
CREATE TABLE `album_user_count_rank_week` (
  `y` int(4) NOT NULL,
  `w` int(2) NOT NULL,
  `parent_column_id` varchar(255) NOT NULL,
  `album_id` int(11) NOT NULL,
  `album_name` varchar(255) DEFAULT NULL,
  `content_type` int(11) DEFAULT NULL,
  `content_type_name` varchar(255) DEFAULT NULL,
  `user_count` bigint(20) DEFAULT NULL,
  UNIQUE KEY `t_date` (`y`,`w`,`parent_column_id`,`album_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='专辑播放用户数排行榜';

-- ----------------------------
-- Table structure for app_stay_duration
-- ----------------------------
DROP TABLE IF EXISTS `app_stay_duration`;
CREATE TABLE `app_stay_duration` (
  `t_date` varchar(255) NOT NULL COMMENT '日期',
  `parent_column_Id` varchar(255) NOT NULL,
  `user_count` int(11) DEFAULT '0' COMMENT '用户数',
  `stay_duration` bigint(20) DEFAULT '0' COMMENT '停留时长',
  UNIQUE KEY `t_date` (`t_date`,`parent_column_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='APP停留时长';

-- ----------------------------
-- Table structure for app_stay_duration_day
-- ----------------------------
DROP TABLE IF EXISTS `app_stay_duration_day`;
CREATE TABLE `app_stay_duration_day` (
  `t_date` varchar(255) NOT NULL COMMENT '日期',
  `parent_column_Id` varchar(255) NOT NULL,
  `user_count` int(11) DEFAULT '0' COMMENT '用户数',
  `stay_duration` bigint(20) DEFAULT '0' COMMENT '停留时长',
  UNIQUE KEY `t_date` (`t_date`,`parent_column_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='按日APP停留时长';

-- ----------------------------
-- Table structure for app_stay_duration_week
-- ----------------------------
DROP TABLE IF EXISTS `app_stay_duration_week`;
CREATE TABLE `app_stay_duration_week` (
  `y` int(4) NOT NULL COMMENT '年份',
  `w` int(4) NOT NULL COMMENT '周',
  `parent_column_Id` varchar(255) NOT NULL,
  `user_count` int(11) DEFAULT '0' COMMENT '用户数',
  `stay_duration` bigint(20) DEFAULT '0' COMMENT '停留时长',
  UNIQUE KEY `tt` (`y`,`w`,`parent_column_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='按周APP停留时长';

-- ----------------------------
-- Table structure for app_user_stay_duration_week
-- ----------------------------
DROP TABLE IF EXISTS `app_user_stay_duration_week`;
CREATE TABLE `app_user_stay_duration_week` (
  `year` int(11) DEFAULT NULL COMMENT '年',
  `week` int(11) DEFAULT NULL COMMENT '周',
  `parent_column_id` varchar(255) DEFAULT NULL COMMENT '专区ID',
  `user_type` varchar(255) DEFAULT NULL COMMENT '用户类型',
  `mac` varchar(255) DEFAULT NULL COMMENT 'MAC',
  `sn` varchar(255) DEFAULT NULL COMMENT 'SN',
  `user_id` varchar(50) DEFAULT NULL COMMENT '用户ID',
  `area_code` varchar(50) DEFAULT NULL COMMENT '用户ID',
  `stay_duration` decimal(25,0) DEFAULT NULL COMMENT '逗留时长(毫秒)'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户专区周停留时长累计';

-- ----------------------------
-- Table structure for app_visit_count_day
-- ----------------------------
DROP TABLE IF EXISTS `app_visit_count_day`;
CREATE TABLE `app_visit_count_day` (
  `t_date` char(10) NOT NULL COMMENT '日期',
  `parent_column_id` varchar(255) NOT NULL COMMENT '专区ID',
  `user_type` varchar(255) NOT NULL COMMENT '用户类型',
  `area_code` varchar(255) DEFAULT NULL,
  `page_user_count` int(11) NOT NULL DEFAULT '0' COMMENT '访问用户数',
  `play_user_count` int(11) NOT NULL DEFAULT '0' COMMENT '播放用户数',
  `play_count` bigint(20) NOT NULL DEFAULT '0' COMMENT '播放次数',
  `duration` bigint(20) NOT NULL DEFAULT '0' COMMENT '累计播放时长',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY `t_date` (`t_date`,`parent_column_id`,`user_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='专区按天统计信息';

-- ----------------------------
-- Table structure for app_visit_count_week
-- ----------------------------
DROP TABLE IF EXISTS `app_visit_count_week`;
CREATE TABLE `app_visit_count_week` (
  `y` char(10) NOT NULL COMMENT '年份',
  `w` char(10) NOT NULL COMMENT '周',
  `parent_column_id` varchar(255) NOT NULL COMMENT '专区ID',
  `user_type` varchar(10) NOT NULL COMMENT '用户类型',
  `area_code` varchar(10) DEFAULT NULL COMMENT '区域码',
  `page_user_count` int(11) NOT NULL DEFAULT '0' COMMENT '访问用户数',
  `play_user_count` int(11) NOT NULL DEFAULT '0' COMMENT '播放用户数',
  `play_count` bigint(20) NOT NULL DEFAULT '0' COMMENT '播放次数',
  `duration` bigint(20) NOT NULL DEFAULT '0' COMMENT '累计播放时长',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY `uk_user` (`y`,`w`,`parent_column_id`,`user_type`,`area_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='专区按周统计信息';

-- ----------------------------
-- Table structure for app_visit_user_source_day
-- ----------------------------
DROP TABLE IF EXISTS `app_visit_user_source_day`;
CREATE TABLE `app_visit_user_source_day` (
  `t_date` varchar(255) NOT NULL COMMENT '日期',
  `parent_column_id` varchar(255) NOT NULL COMMENT '专区ID',
  `column_code` varchar(255) NOT NULL COMMENT '入口code',
  `user_count` int(11) NOT NULL COMMENT '用户数',
  `visit_count` bigint(20) NOT NULL COMMENT '点击次数'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户来源统计';

-- ----------------------------
-- Table structure for app_visit_user_source_week
-- ----------------------------
DROP TABLE IF EXISTS `app_visit_user_source_week`;
CREATE TABLE `app_visit_user_source_week` (
  `y` int(4) NOT NULL COMMENT '年份',
  `w` int(2) NOT NULL COMMENT '周',
  `parent_column_id` varchar(255) NOT NULL COMMENT '专区ID',
  `column_code` varchar(255) NOT NULL COMMENT '入口code',
  `user_count` int(11) NOT NULL COMMENT '用户数',
  `visit_count` bigint(20) NOT NULL COMMENT '点击次数'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='按周用户来源统计';

-- ----------------------------
-- Table structure for auth_product_user
-- ----------------------------
DROP TABLE IF EXISTS `auth_product_user`;
CREATE TABLE `auth_product_user` (
  `user_id` varchar(255) DEFAULT NULL COMMENT '用户ID',
  `mac` varchar(255) DEFAULT NULL COMMENT 'MAC',
  `sn` varchar(255) DEFAULT NULL COMMENT 'SN',
  `user_type` varchar(255) DEFAULT NULL COMMENT '用户类型',
  `area_code` varchar(255) DEFAULT NULL COMMENT '区域码',
  `third_product_code` varchar(255) DEFAULT NULL COMMENT '鉴权产品Code',
  `create_time` varchar(255) DEFAULT NULL COMMENT '时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='鉴权用户信息';

-- ----------------------------
-- Table structure for bookmark_rank
-- ----------------------------
DROP TABLE IF EXISTS `bookmark_rank`;
CREATE TABLE `bookmark_rank` (
  `t_date` varchar(10) NOT NULL COMMENT '统计日期',
  `parent_column_id` varchar(255) NOT NULL COMMENT '栏目产品ID',
  `album_id` int(11) NOT NULL COMMENT '专辑ID',
  `album_name` varchar(255) DEFAULT NULL COMMENT '专辑名称',
  `content_type` int(11) DEFAULT NULL COMMENT '专辑内容类型',
  `content_type_name` varchar(255) DEFAULT NULL COMMENT '专辑内容名称',
  `count` int(11) NOT NULL COMMENT '统计访问量',
  UNIQUE KEY `t_date` (`t_date`,`parent_column_id`,`album_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='收藏排行榜';

-- ----------------------------
-- Table structure for bookmark_rank_day
-- ----------------------------
DROP TABLE IF EXISTS `bookmark_rank_day`;
CREATE TABLE `bookmark_rank_day` (
  `t_date` varchar(10) NOT NULL COMMENT '日期',
  `parent_column_id` varchar(255) NOT NULL COMMENT '栏目产品ID',
  `album_id` int(11) NOT NULL COMMENT '专辑ID',
  `album_name` varchar(255) DEFAULT NULL COMMENT '专辑名称',
  `content_type` int(11) DEFAULT NULL COMMENT '专辑内容类型',
  `content_type_name` varchar(255) DEFAULT NULL COMMENT '专辑内容名称',
  `count` int(11) NOT NULL COMMENT '统计访问量',
  UNIQUE KEY `t_date` (`t_date`,`parent_column_id`,`album_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='按日收藏排行榜';

-- ----------------------------
-- Table structure for bookmark_rank_week
-- ----------------------------
DROP TABLE IF EXISTS `bookmark_rank_week`;
CREATE TABLE `bookmark_rank_week` (
  `y` int(4) NOT NULL COMMENT '年',
  `w` int(3) NOT NULL COMMENT '周',
  `parent_column_id` varchar(255) NOT NULL COMMENT '栏目产品ID',
  `album_id` int(11) NOT NULL COMMENT '专辑ID',
  `album_name` varchar(255) DEFAULT NULL COMMENT '专辑名称',
  `content_type` int(11) DEFAULT NULL COMMENT '专辑内容类型',
  `content_type_name` varchar(255) DEFAULT NULL COMMENT '专辑内容名称',
  `count` int(11) NOT NULL COMMENT '统计访问量',
  UNIQUE KEY `t_date` (`y`,`w`,`parent_column_id`,`album_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='按周收藏排行榜';

-- ----------------------------
-- Table structure for clean_user
-- ----------------------------
DROP TABLE IF EXISTS `clean_user`;
CREATE TABLE `clean_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) DEFAULT NULL COMMENT '用户ID',
  `is_effective` tinyint(4) DEFAULT '1' COMMENT '是否启用 0禁用 1启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='日志中需清理用户ID表';

-- ----------------------------
-- Table structure for column_stay_duration
-- ----------------------------
DROP TABLE IF EXISTS `column_stay_duration`;
CREATE TABLE `column_stay_duration` (
  `t_date` varchar(255) NOT NULL COMMENT '日期',
  `parent_column_Id` varchar(10) NOT NULL,
  `before_column_id` varchar(10) DEFAULT NULL COMMENT '栏目ID',
  `before_column_code` varchar(255) DEFAULT NULL COMMENT '栏目ID',
  `before_column_name` varchar(100) DEFAULT NULL COMMENT '栏目名称',
  `user_count` int(11) DEFAULT '0' COMMENT '用户数',
  `stay_duration` bigint(20) DEFAULT '0' COMMENT '停留时长',
  UNIQUE KEY `t_date` (`t_date`,`parent_column_Id`,`before_column_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='栏目停留时长';

-- ----------------------------
-- Table structure for column_stay_duration_day
-- ----------------------------
DROP TABLE IF EXISTS `column_stay_duration_day`;
CREATE TABLE `column_stay_duration_day` (
  `t_date` varchar(255) NOT NULL COMMENT '日期',
  `parent_column_Id` varchar(10) NOT NULL,
  `before_column_id` varchar(10) DEFAULT NULL COMMENT '栏目ID',
  `before_column_code` varchar(255) DEFAULT NULL COMMENT '栏目ID',
  `before_column_name` varchar(100) DEFAULT NULL COMMENT '栏目名称',
  `user_count` int(11) DEFAULT '0' COMMENT '用户数',
  `stay_duration` bigint(20) DEFAULT '0' COMMENT '停留时长',
  UNIQUE KEY `t_date` (`t_date`,`parent_column_Id`,`before_column_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='栏目按日停留时长';

-- ----------------------------
-- Table structure for column_stay_duration_week
-- ----------------------------
DROP TABLE IF EXISTS `column_stay_duration_week`;
CREATE TABLE `column_stay_duration_week` (
  `y` int(4) NOT NULL COMMENT '年份',
  `w` int(4) NOT NULL COMMENT '周',
  `parent_column_Id` varchar(10) NOT NULL,
  `before_column_id` varchar(10) DEFAULT NULL COMMENT '栏目ID',
  `before_column_code` varchar(255) DEFAULT NULL COMMENT '栏目ID',
  `before_column_name` varchar(100) DEFAULT NULL COMMENT '栏目名称',
  `user_count` int(11) DEFAULT '0' COMMENT '用户数',
  `stay_duration` bigint(20) DEFAULT '0' COMMENT '停留时长',
  UNIQUE KEY `t_date` (`y`,`w`,`parent_column_Id`,`before_column_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='按周栏目停留时长';

-- ----------------------------
-- Table structure for detail_page_log
-- ----------------------------
DROP TABLE IF EXISTS `detail_page_log`;
CREATE TABLE `detail_page_log` (
  `parent_column_id` varchar(255) DEFAULT NULL COMMENT '父栏目',
  `user_type` varchar(255) DEFAULT NULL COMMENT '用户类型',
  `mac` varchar(255) DEFAULT NULL COMMENT 'MAC',
  `sn` varchar(255) DEFAULT NULL COMMENT 'SN',
  `user_id` varchar(255) DEFAULT NULL COMMENT '用户ID',
  `column_id` varchar(255) DEFAULT NULL COMMENT '栏目ID',
  `album_id` varchar(255) DEFAULT NULL COMMENT '专辑ID',
  `content_type` varchar(255) DEFAULT NULL COMMENT '内容类型1专辑 2专题',
  `after_column_id` varchar(255) DEFAULT NULL COMMENT '来源栏目ID',
  `after_column_code` varchar(255) DEFAULT NULL COMMENT '来源栏目code',
  `after_column_name` varchar(100) DEFAULT NULL COMMENT '来源栏目名称',
  `area_code` varchar(255) DEFAULT NULL COMMENT '区域码',
  `create_time` varchar(255) DEFAULT NULL COMMENT '创建时间',
  `album_name` varchar(255) DEFAULT NULL COMMENT '专辑名称',
  `album_content_type_name` varchar(255) DEFAULT NULL COMMENT '专辑类型名称',
  `labels` varchar(255) DEFAULT NULL COMMENT '二级分类'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='详情页日志';

-- ----------------------------
-- Table structure for detail_page_visit_user
-- ----------------------------
DROP TABLE IF EXISTS `detail_page_visit_user`;
CREATE TABLE `detail_page_visit_user` (
  `t_date` varchar(255) DEFAULT NULL COMMENT '统计日志以前的数据',
  `parent_column_id` varchar(255) DEFAULT NULL COMMENT '专区ID',
  `user_type` varchar(255) DEFAULT NULL COMMENT '用户类型',
  `user_count` bigint(20) DEFAULT '0' COMMENT '用户数',
  `visit_count` bigint(20) DEFAULT '0' COMMENT '点击数',
  UNIQUE KEY `t_date` (`t_date`,`parent_column_id`,`user_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for detail_page_visit_user_day
-- ----------------------------
DROP TABLE IF EXISTS `detail_page_visit_user_day`;
CREATE TABLE `detail_page_visit_user_day` (
  `t_date` varchar(255) DEFAULT NULL COMMENT '日期',
  `parent_column_id` varchar(255) DEFAULT NULL COMMENT '专区ID',
  `user_type` varchar(255) DEFAULT NULL COMMENT '用户类型',
  `user_count` bigint(20) DEFAULT '0' COMMENT '用户数',
  `visit_count` bigint(20) DEFAULT '0' COMMENT '点击数',
  UNIQUE KEY `t_date` (`t_date`,`parent_column_id`,`user_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='按日统计详情访问';

-- ----------------------------
-- Table structure for detail_page_visit_user_week
-- ----------------------------
DROP TABLE IF EXISTS `detail_page_visit_user_week`;
CREATE TABLE `detail_page_visit_user_week` (
  `y` int(4) DEFAULT NULL COMMENT '年份',
  `w` int(4) DEFAULT NULL COMMENT '周',
  `parent_column_id` varchar(255) DEFAULT NULL COMMENT '专区ID',
  `user_type` varchar(255) DEFAULT NULL COMMENT '用户类型',
  `user_count` bigint(20) DEFAULT '0' COMMENT '用户数',
  `visit_count` bigint(20) DEFAULT '0' COMMENT '点击数',
  UNIQUE KEY `t_date` (`y`,`w`,`parent_column_id`,`user_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='按周统计详情访问';

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
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COMMENT='导出table维护信息';

-- ----------------------------
-- Table structure for login_page_log
-- ----------------------------
DROP TABLE IF EXISTS `login_page_log`;
CREATE TABLE `login_page_log` (
  `parent_column_id` varchar(255) DEFAULT NULL COMMENT '父栏目',
  `user_type` varchar(255) DEFAULT NULL COMMENT '用户类型',
  `mac` varchar(255) DEFAULT NULL COMMENT 'MAC',
  `sn` varchar(255) DEFAULT NULL COMMENT 'SN',
  `user_id` varchar(255) DEFAULT NULL COMMENT '用户ID',
  `column_id` varchar(255) DEFAULT NULL COMMENT '栏目ID',
  `after_column_id` varchar(255) DEFAULT NULL COMMENT '父栏目ID',
  `after_column_code` varchar(255) DEFAULT NULL COMMENT '父栏目Code',
  `after_column_name` varchar(100) DEFAULT NULL COMMENT '来源栏目名称',
  `area_code` varchar(255) DEFAULT NULL COMMENT '区域code',
  `create_time` varchar(255) DEFAULT NULL COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='首页访问日志';

-- ----------------------------
-- Table structure for product_column
-- ----------------------------
DROP TABLE IF EXISTS `product_column`;
CREATE TABLE `product_column` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `column_id` varchar(255) NOT NULL COMMENT '栏目ID',
  `column_name` varchar(255) DEFAULT NULL COMMENT '栏目名称',
  `is_effective` tinyint(1) DEFAULT '1' COMMENT '1有效 0禁用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `column_id` (`column_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='现网产品统计';

-- ----------------------------
-- Table structure for retention_user_count_day
-- ----------------------------
DROP TABLE IF EXISTS `retention_user_count_day`;
CREATE TABLE `retention_user_count_day` (
  `t_date` varchar(255) NOT NULL COMMENT '日期',
  `parent_column_id` varchar(255) NOT NULL,
  `user_type` varchar(255) NOT NULL,
  `user_2day_count` int(10) DEFAULT '0' COMMENT '2日留存用户数',
  `user_3day_count` int(10) DEFAULT '0' COMMENT '3日留存用户数',
  `add_user_2day_count` int(10) DEFAULT '0' COMMENT '2日新增用户留存数',
  UNIQUE KEY `t_date` (`t_date`,`parent_column_id`,`user_type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='留存用户数统计';

-- ----------------------------
-- Table structure for search_album_rank
-- ----------------------------
DROP TABLE IF EXISTS `search_album_rank`;
CREATE TABLE `search_album_rank` (
  `t_date` varchar(10) NOT NULL COMMENT '统计日期',
  `parent_column_id` varchar(20) NOT NULL COMMENT '栏目产品ID',
  `album_id` int(11) NOT NULL COMMENT '专辑ID',
  `album_name` varchar(50) DEFAULT NULL COMMENT '专辑名称',
  `content_type` int(11) DEFAULT NULL COMMENT '专辑内容类型',
  `content_type_name` varchar(20) DEFAULT NULL COMMENT '专辑内容名称',
  `count` int(11) NOT NULL COMMENT '统计访问量',
  UNIQUE KEY `t_date` (`t_date`,`parent_column_id`,`album_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='搜索专辑排行榜';

-- ----------------------------
-- Table structure for search_album_rank_day
-- ----------------------------
DROP TABLE IF EXISTS `search_album_rank_day`;
CREATE TABLE `search_album_rank_day` (
  `t_date` varchar(10) NOT NULL COMMENT '日期',
  `parent_column_id` varchar(20) NOT NULL COMMENT '栏目产品ID',
  `album_id` int(11) NOT NULL COMMENT '专辑ID',
  `album_name` varchar(50) DEFAULT NULL COMMENT '专辑名称',
  `content_type` int(11) DEFAULT NULL COMMENT '专辑内容类型',
  `content_type_name` varchar(20) DEFAULT NULL COMMENT '专辑内容名称',
  `count` int(11) NOT NULL COMMENT '统计访问量',
  UNIQUE KEY `t_date` (`t_date`,`parent_column_id`,`album_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='搜索专辑按日排行榜';

-- ----------------------------
-- Table structure for search_album_rank_week
-- ----------------------------
DROP TABLE IF EXISTS `search_album_rank_week`;
CREATE TABLE `search_album_rank_week` (
  `y` int(4) NOT NULL COMMENT '年',
  `w` int(3) NOT NULL COMMENT '周',
  `parent_column_id` varchar(20) NOT NULL COMMENT '栏目产品ID',
  `album_id` int(11) NOT NULL COMMENT '专辑ID',
  `album_name` varchar(50) DEFAULT NULL COMMENT '专辑名称',
  `content_type` int(11) DEFAULT NULL COMMENT '专辑内容类型',
  `content_type_name` varchar(20) DEFAULT NULL COMMENT '专辑内容名称',
  `count` int(11) NOT NULL COMMENT '统计访问量',
  UNIQUE KEY `t_date` (`y`,`w`,`parent_column_id`,`album_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='搜索专辑按周排行榜';

-- ----------------------------
-- Table structure for subscribe_label_count
-- ----------------------------
DROP TABLE IF EXISTS `subscribe_label_count`;
CREATE TABLE `subscribe_label_count` (
  `t_date` varchar(255) NOT NULL COMMENT '日期',
  `parent_column_id` varchar(255) NOT NULL COMMENT '专区栏目ID',
  `label_id` int(11) NOT NULL COMMENT '标签ID',
  `label_name` varchar(255) NOT NULL COMMENT '标签名称',
  `subscribe_count` int(11) NOT NULL DEFAULT '0' COMMENT '累计订阅数',
  UNIQUE KEY `t_date` (`t_date`,`parent_column_id`,`label_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='统计周期累计订阅数';

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
) ENGINE=InnoDB AUTO_INCREMENT=214 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `parent_column_id` varchar(255) NOT NULL COMMENT '专区ID',
  `user_type` varchar(255) NOT NULL COMMENT '用户类型',
  `user_id` varchar(255) NOT NULL COMMENT '用户ID',
  `area_code` varchar(100) DEFAULT '' COMMENT '区域码',
  `create_time` varchar(255) DEFAULT NULL COMMENT '创建时间',
  UNIQUE KEY `parent_column_id` (`parent_column_id`,`user_type`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息';

-- ----------------------------
-- Table structure for video_play_count_rank_day
-- ----------------------------
DROP TABLE IF EXISTS `video_play_count_rank_day`;
CREATE TABLE `video_play_count_rank_day` (
  `t_date` varchar(255) NOT NULL,
  `parent_column_id` varchar(255) NOT NULL,
  `album_id` int(11) NOT NULL,
  `album_name` varchar(255) DEFAULT NULL,
  `content_type` int(11) DEFAULT NULL,
  `content_type_name` varchar(255) DEFAULT NULL,
  `video_id` int(11) DEFAULT NULL,
  `video_name` varchar(255) DEFAULT NULL,
  `play_count` bigint(20) DEFAULT NULL,
  UNIQUE KEY `t_date` (`t_date`,`parent_column_id`,`album_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='专辑播放次数排行榜';

-- ----------------------------
-- Table structure for video_play_count_rank_week
-- ----------------------------
DROP TABLE IF EXISTS `video_play_count_rank_week`;
CREATE TABLE `video_play_count_rank_week` (
  `y` int(4) NOT NULL,
  `w` int(2) NOT NULL,
  `parent_column_id` varchar(255) NOT NULL,
  `album_id` int(11) NOT NULL,
  `album_name` varchar(255) DEFAULT NULL,
  `content_type` int(11) DEFAULT NULL,
  `content_type_name` varchar(255) DEFAULT NULL,
  `video_id` int(11) DEFAULT NULL,
  `video_name` varchar(255) DEFAULT NULL,
  `play_count` bigint(20) DEFAULT NULL,
  UNIQUE KEY `y_w` (`y`,`w`,`parent_column_id`,`album_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='专辑播放次数排行榜';

-- ----------------------------
-- Table structure for video_user_count_rank_day
-- ----------------------------
DROP TABLE IF EXISTS `video_user_count_rank_day`;
CREATE TABLE `video_user_count_rank_day` (
  `t_date` varchar(255) NOT NULL,
  `parent_column_id` varchar(255) NOT NULL,
  `album_id` int(11) NOT NULL,
  `album_name` varchar(255) DEFAULT NULL,
  `content_type` int(11) DEFAULT NULL,
  `content_type_name` varchar(255) DEFAULT NULL,
  `video_id` int(11) DEFAULT NULL,
  `video_name` varchar(255) DEFAULT NULL,
  `user_count` bigint(20) DEFAULT NULL,
  UNIQUE KEY `t_date` (`t_date`,`parent_column_id`,`album_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='专辑播放用户数排行榜';

-- ----------------------------
-- Table structure for video_user_count_rank_week
-- ----------------------------
DROP TABLE IF EXISTS `video_user_count_rank_week`;
CREATE TABLE `video_user_count_rank_week` (
  `y` int(4) NOT NULL,
  `w` int(2) NOT NULL,
  `parent_column_id` varchar(255) NOT NULL,
  `album_id` int(11) NOT NULL,
  `album_name` varchar(255) DEFAULT NULL,
  `content_type` int(11) DEFAULT NULL,
  `content_type_name` varchar(255) DEFAULT NULL,
  `video_id` int(11) DEFAULT NULL,
  `video_name` varchar(255) DEFAULT NULL,
  `user_count` bigint(20) DEFAULT NULL,
  UNIQUE KEY `y_w` (`y`,`w`,`parent_column_id`,`album_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='剧集播放用户数排行榜';

DROP TABLE IF EXISTS `t_area_info`;
CREATE TABLE `t_area_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` char(3) NOT NULL DEFAULT 'ott' COMMENT 'ott,vod',
  `area_no` varchar(255) NOT NULL COMMENT '地区编码',
  `area_name` varchar(255) DEFAULT NULL COMMENT '地区名称',
  `city_no` varchar(255) DEFAULT NULL COMMENT '城市编码',
  `city_name` varchar(255) DEFAULT NULL COMMENT '城市名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `area_no_type` (`type`,`area_no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Procedure structure for insert_or_update_user
-- ----------------------------
DROP PROCEDURE IF EXISTS `insert_or_update_user`;
DELIMITER ;;
CREATE DEFINER=`kfyw`@`%` PROCEDURE `insert_or_update_user`(IN i_parent_column_id VARCHAR(100),IN i_user_type VARCHAR(100),IN i_user_id VARCHAR(100),IN i_area_code VARCHAR(100),IN i_create_time VARCHAR(100))
BEGIN
  DECLARE insert_time VARCHAR(100);
  DECLARE insert_area_code VARCHAR(100);
	SELECT create_time,area_code INTO insert_time,insert_area_code FROM user_info where parent_column_id=i_parent_column_id and user_type=i_user_type and user_id=i_user_id;
  IF insert_time IS NOT NULL THEN
    IF insert_time> i_create_time THEN
        set insert_time=i_create_time;
    END IF;
    SELECT IFNULL(i_area_code,insert_area_code) INTO insert_area_code;
		UPDATE user_info SET area_code=insert_area_code,create_time=insert_time where parent_column_id=i_parent_column_id and user_type=i_user_type and user_id=i_user_id; 
  ELSE
    INSERT INTO user_info (parent_column_id, user_type,user_id,area_code,create_time) VALUES (i_parent_column_id, i_user_type, i_user_id, i_area_code, i_create_time);
  END IF;
END
;;
DELIMITER ;


DROP PROCEDURE IF EXISTS `update_area_to_user`;
DELIMITER ;;
CREATE DEFINER=`kfyw`@`%` PROCEDURE `update_area_to_user`(IN i_user_id VARCHAR(100),IN i_user_type VARCHAR(100),IN i_area_code VARCHAR(100))
BEGIN
  IF i_area_code IS NOT NULL THEN
    UPDATE user_info u SET u.area_code=i_area_code where u.user_type=i_user_type and u.user_id=i_user_id and (u.area_code='' or u.area_code IS NULL);
  END IF;
END
;;
DELIMITER ;