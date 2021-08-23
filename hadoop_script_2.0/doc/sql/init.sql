/*
Navicat MySQL Data Transfer

Source Server         : 公司云南数据采集开发环境
Source Server Version : 50730
Source Host           : 192.168.15.227:3306
Source Database       : yn_hadoop2

Target Server Type    : MYSQL
Target Server Version : 50730
File Encoding         : 65001

Date: 2020-10-14 15:09:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for app_album_play_count_rank_day
-- ----------------------------
DROP TABLE IF EXISTS `app_album_play_count_rank_day`;
CREATE TABLE `app_album_play_count_rank_day` (
  `t_date` varchar(255) NOT NULL,
  `parent_column_id` varchar(255) NOT NULL,
  `album_id` int(11) NOT NULL,
  `album_name` varchar(255) DEFAULT NULL,
  `content_type` varchar(11) DEFAULT NULL,
  `content_type_name` varchar(255) DEFAULT NULL,
  `play_count` bigint(20) DEFAULT NULL,
  UNIQUE KEY `t_date` (`t_date`,`parent_column_id`,`album_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='专区专辑播放次数排行榜';

-- ----------------------------
-- Table structure for app_album_play_count_rank_week
-- ----------------------------
DROP TABLE IF EXISTS `app_album_play_count_rank_week`;
CREATE TABLE `app_album_play_count_rank_week` (
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
-- Table structure for app_album_user_count_rank_day
-- ----------------------------
DROP TABLE IF EXISTS `app_album_user_count_rank_day`;
CREATE TABLE `app_album_user_count_rank_day` (
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
-- Table structure for app_album_user_count_rank_week
-- ----------------------------
DROP TABLE IF EXISTS `app_album_user_count_rank_week`;
CREATE TABLE `app_album_user_count_rank_week` (
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
-- Table structure for app_area_visit_count_day
-- ----------------------------
DROP TABLE IF EXISTS `app_area_visit_count_day`;
CREATE TABLE `app_area_visit_count_day` (
  `t_date` char(10) NOT NULL COMMENT '日期',
  `parent_column_id` varchar(255) NOT NULL COMMENT '专区ID',
  `user_type` varchar(255) NOT NULL COMMENT '用户类型',
  `area_code` varchar(255) DEFAULT NULL,
  `page_user_count` int(11) NOT NULL DEFAULT '0' COMMENT '访问用户数',
  `play_user_count` int(11) NOT NULL DEFAULT '0' COMMENT '播放用户数',
  `play_count` bigint(20) NOT NULL DEFAULT '0' COMMENT '播放次数',
  `duration` bigint(20) NOT NULL DEFAULT '0' COMMENT '累计播放时长',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY `t_date` (`t_date`,`parent_column_id`,`user_type`,`area_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='专区按天统计信息';

-- ----------------------------
-- Table structure for app_area_visit_count_month
-- ----------------------------
DROP TABLE IF EXISTS `app_area_visit_count_month`;
CREATE TABLE `app_area_visit_count_month` (
  `y` char(10) NOT NULL COMMENT '年份',
  `m` char(10) NOT NULL COMMENT '月',
  `parent_column_id` varchar(255) NOT NULL COMMENT '专区ID',
  `user_type` varchar(10) NOT NULL COMMENT '用户类型',
  `area_code` varchar(10) DEFAULT NULL COMMENT '区域码',
  `page_user_count` int(11) NOT NULL DEFAULT '0' COMMENT '访问用户数',
  `play_user_count` int(11) NOT NULL DEFAULT '0' COMMENT '播放用户数',
  `play_count` bigint(20) NOT NULL DEFAULT '0' COMMENT '播放次数',
  `duration` bigint(20) NOT NULL DEFAULT '0' COMMENT '累计播放时长',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY `uk_user` (`y`,`m`,`parent_column_id`,`user_type`,`area_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='专区按月统计信息';

-- ----------------------------
-- Table structure for app_area_visit_count_week
-- ----------------------------
DROP TABLE IF EXISTS `app_area_visit_count_week`;
CREATE TABLE `app_area_visit_count_week` (
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
-- Table structure for app_bookmark_rank
-- ----------------------------
DROP TABLE IF EXISTS `app_bookmark_rank`;
CREATE TABLE `app_bookmark_rank` (
  `t_date` varchar(10) NOT NULL COMMENT '统计日期',
  `parent_column_id` varchar(255) NOT NULL COMMENT '栏目产品ID',
  `album_id` int(11) NOT NULL COMMENT '专辑ID',
  `album_name` varchar(255) DEFAULT NULL COMMENT '专辑名称',
  `content_type` int(11) DEFAULT NULL COMMENT '专辑内容类型',
  `content_type_name` varchar(255) DEFAULT NULL COMMENT '专辑内容名称',
  `count` int(11) NOT NULL COMMENT '统计访问量',
  UNIQUE KEY `t_date` (`t_date`,`parent_column_id`,`album_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='专区总收藏排行榜';

-- ----------------------------
-- Table structure for app_bookmark_rank_day
-- ----------------------------
DROP TABLE IF EXISTS `app_bookmark_rank_day`;
CREATE TABLE `app_bookmark_rank_day` (
  `t_date` varchar(10) NOT NULL COMMENT '日期',
  `parent_column_id` varchar(255) NOT NULL COMMENT '栏目产品ID',
  `album_id` int(11) NOT NULL COMMENT '专辑ID',
  `album_name` varchar(255) DEFAULT NULL COMMENT '专辑名称',
  `content_type` int(11) DEFAULT NULL COMMENT '专辑内容类型',
  `content_type_name` varchar(255) DEFAULT NULL COMMENT '专辑内容名称',
  `count` int(11) NOT NULL COMMENT '统计访问量',
  UNIQUE KEY `t_date` (`t_date`,`parent_column_id`,`album_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='专区按日收藏排行榜';

-- ----------------------------
-- Table structure for app_bookmark_rank_month
-- ----------------------------
DROP TABLE IF EXISTS `app_bookmark_rank_month`;
CREATE TABLE `app_bookmark_rank_month` (
  `y` int(4) NOT NULL COMMENT '年',
  `m` int(3) NOT NULL COMMENT '周',
  `parent_column_id` varchar(255) NOT NULL COMMENT '栏目产品ID',
  `album_id` int(11) NOT NULL COMMENT '专辑ID',
  `album_name` varchar(255) DEFAULT NULL COMMENT '专辑名称',
  `content_type` int(11) DEFAULT NULL COMMENT '专辑内容类型',
  `content_type_name` varchar(255) DEFAULT NULL COMMENT '专辑内容名称',
  `count` int(11) NOT NULL COMMENT '统计访问量',
  UNIQUE KEY `t_date` (`y`,`m`,`parent_column_id`,`album_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='专区按月收藏排行榜';

-- ----------------------------
-- Table structure for app_bookmark_rank_week
-- ----------------------------
DROP TABLE IF EXISTS `app_bookmark_rank_week`;
CREATE TABLE `app_bookmark_rank_week` (
  `y` int(4) NOT NULL COMMENT '年',
  `w` int(3) NOT NULL COMMENT '周',
  `parent_column_id` varchar(255) NOT NULL COMMENT '栏目产品ID',
  `album_id` int(11) NOT NULL COMMENT '专辑ID',
  `album_name` varchar(255) DEFAULT NULL COMMENT '专辑名称',
  `content_type` int(11) DEFAULT NULL COMMENT '专辑内容类型',
  `content_type_name` varchar(255) DEFAULT NULL COMMENT '专辑内容名称',
  `count` int(11) NOT NULL COMMENT '统计访问量',
  UNIQUE KEY `t_date` (`y`,`w`,`parent_column_id`,`album_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='专区按周收藏排行榜';

-- ----------------------------
-- Table structure for app_column_stay_duration
-- ----------------------------
DROP TABLE IF EXISTS `app_column_stay_duration`;
CREATE TABLE `app_column_stay_duration` (
  `t_date` varchar(255) NOT NULL COMMENT '日期',
  `parent_column_Id` varchar(10) NOT NULL,
  `before_column_id` varchar(10) DEFAULT NULL COMMENT '栏目ID',
  `before_column_code` varchar(255) DEFAULT NULL COMMENT '栏目ID',
  `before_column_name` varchar(100) DEFAULT NULL COMMENT '栏目名称',
  `user_count` int(11) DEFAULT '0' COMMENT '用户数',
  `stay_duration` bigint(20) DEFAULT '0' COMMENT '停留时长',
  UNIQUE KEY `t_date` (`t_date`,`parent_column_Id`,`before_column_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='专区栏目停留时长';

-- ----------------------------
-- Table structure for app_column_stay_duration_day
-- ----------------------------
DROP TABLE IF EXISTS `app_column_stay_duration_day`;
CREATE TABLE `app_column_stay_duration_day` (
  `t_date` varchar(255) NOT NULL COMMENT '日期',
  `parent_column_Id` varchar(10) NOT NULL,
  `before_column_id` varchar(10) DEFAULT NULL COMMENT '栏目ID',
  `before_column_code` varchar(255) DEFAULT NULL COMMENT '栏目ID',
  `before_column_name` varchar(100) DEFAULT NULL COMMENT '栏目名称',
  `user_count` int(11) DEFAULT '0' COMMENT '用户数',
  `stay_duration` bigint(20) DEFAULT '0' COMMENT '停留时长',
  UNIQUE KEY `t_date` (`t_date`,`parent_column_Id`,`before_column_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='专区栏目按日停留时长';

-- ----------------------------
-- Table structure for app_column_stay_duration_week
-- ----------------------------
DROP TABLE IF EXISTS `app_column_stay_duration_week`;
CREATE TABLE `app_column_stay_duration_week` (
  `y` int(4) NOT NULL COMMENT '年份',
  `w` int(4) NOT NULL COMMENT '周',
  `parent_column_Id` varchar(10) NOT NULL,
  `before_column_id` varchar(10) DEFAULT NULL COMMENT '栏目ID',
  `before_column_code` varchar(255) DEFAULT NULL COMMENT '栏目ID',
  `before_column_name` varchar(100) DEFAULT NULL COMMENT '栏目名称',
  `user_count` int(11) DEFAULT '0' COMMENT '用户数',
  `stay_duration` bigint(20) DEFAULT '0' COMMENT '停留时长',
  UNIQUE KEY `t_date` (`y`,`w`,`parent_column_Id`,`before_column_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='专区按周栏目停留时长';

-- ----------------------------
-- Table structure for app_detail_page_count
-- ----------------------------
DROP TABLE IF EXISTS `app_detail_page_count`;
CREATE TABLE `app_detail_page_count` (
  `t_date` varchar(255) DEFAULT NULL COMMENT '统计日志以前的数据',
  `parent_column_id` varchar(255) DEFAULT NULL COMMENT '专区ID',
  `user_type` varchar(255) DEFAULT NULL COMMENT '用户类型',
  `user_count` bigint(20) DEFAULT '0' COMMENT '用户数',
  `visit_count` bigint(20) DEFAULT '0' COMMENT '点击数',
  UNIQUE KEY `t_date` (`t_date`,`parent_column_id`,`user_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='累计统计详情访问';

-- ----------------------------
-- Table structure for app_detail_page_count_day
-- ----------------------------
DROP TABLE IF EXISTS `app_detail_page_count_day`;
CREATE TABLE `app_detail_page_count_day` (
  `t_date` varchar(255) DEFAULT NULL COMMENT '日期',
  `parent_column_id` varchar(255) DEFAULT NULL COMMENT '专区ID',
  `user_type` varchar(255) DEFAULT NULL COMMENT '用户类型',
  `user_count` bigint(20) DEFAULT '0' COMMENT '用户数',
  `visit_count` bigint(20) DEFAULT '0' COMMENT '点击数',
  UNIQUE KEY `t_date` (`t_date`,`parent_column_id`,`user_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='按日统计详情访问';

-- ----------------------------
-- Table structure for app_detail_page_count_week
-- ----------------------------
DROP TABLE IF EXISTS `app_detail_page_count_week`;
CREATE TABLE `app_detail_page_count_week` (
  `y` int(4) DEFAULT NULL COMMENT '年份',
  `w` int(4) DEFAULT NULL COMMENT '周',
  `parent_column_id` varchar(255) DEFAULT NULL COMMENT '专区ID',
  `user_type` varchar(255) DEFAULT NULL COMMENT '用户类型',
  `user_count` bigint(20) DEFAULT '0' COMMENT '用户数',
  `visit_count` bigint(20) DEFAULT '0' COMMENT '点击数',
  UNIQUE KEY `t_date` (`y`,`w`,`parent_column_id`,`user_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='按周统计详情访问';

-- ----------------------------
-- Table structure for app_retention_user_count_day
-- ----------------------------
DROP TABLE IF EXISTS `app_retention_user_count_day`;
CREATE TABLE `app_retention_user_count_day` (
  `t_date` varchar(255) NOT NULL COMMENT '日期',
  `parent_column_id` varchar(255) NOT NULL,
  `user_type` varchar(255) NOT NULL,
  `user_2day_count` int(10) DEFAULT '0' COMMENT '2日留存用户数',
  `user_3day_count` int(10) DEFAULT '0' COMMENT '3日留存用户数',
  `add_user_2day_count` int(10) DEFAULT '0' COMMENT '2日新增用户留存数',
  UNIQUE KEY `t_date` (`t_date`,`parent_column_id`,`user_type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='留存用户数统计';

-- ----------------------------
-- Table structure for app_search_album_rank
-- ----------------------------
DROP TABLE IF EXISTS `app_search_album_rank`;
CREATE TABLE `app_search_album_rank` (
  `t_date` varchar(10) NOT NULL COMMENT '统计日期',
  `parent_column_id` varchar(20) NOT NULL COMMENT '栏目产品ID',
  `album_id` int(11) NOT NULL COMMENT '专辑ID',
  `album_name` varchar(50) DEFAULT NULL COMMENT '专辑名称',
  `content_type` int(11) DEFAULT NULL COMMENT '专辑内容类型',
  `content_type_name` varchar(20) DEFAULT NULL COMMENT '专辑内容名称',
  `count` int(11) NOT NULL COMMENT '统计访问量',
  UNIQUE KEY `t_date` (`t_date`,`parent_column_id`,`album_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='专区搜索专辑总排行榜';

-- ----------------------------
-- Table structure for app_search_album_rank_day
-- ----------------------------
DROP TABLE IF EXISTS `app_search_album_rank_day`;
CREATE TABLE `app_search_album_rank_day` (
  `t_date` varchar(10) NOT NULL COMMENT '日期',
  `parent_column_id` varchar(20) NOT NULL COMMENT '栏目产品ID',
  `album_id` int(11) NOT NULL COMMENT '专辑ID',
  `album_name` varchar(50) DEFAULT NULL COMMENT '专辑名称',
  `content_type` int(11) DEFAULT NULL COMMENT '专辑内容类型',
  `content_type_name` varchar(20) DEFAULT NULL COMMENT '专辑内容名称',
  `count` int(11) NOT NULL COMMENT '统计访问量',
  UNIQUE KEY `t_date` (`t_date`,`parent_column_id`,`album_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='专区搜索专辑按日排行榜';

-- ----------------------------
-- Table structure for app_search_album_rank_week
-- ----------------------------
DROP TABLE IF EXISTS `app_search_album_rank_week`;
CREATE TABLE `app_search_album_rank_week` (
  `y` int(4) NOT NULL COMMENT '年',
  `w` int(3) NOT NULL COMMENT '周',
  `parent_column_id` varchar(20) NOT NULL COMMENT '栏目产品ID',
  `album_id` int(11) NOT NULL COMMENT '专辑ID',
  `album_name` varchar(50) DEFAULT NULL COMMENT '专辑名称',
  `content_type` int(11) DEFAULT NULL COMMENT '专辑内容类型',
  `content_type_name` varchar(20) DEFAULT NULL COMMENT '专辑内容名称',
  `count` int(11) NOT NULL COMMENT '统计访问量',
  UNIQUE KEY `t_date` (`y`,`w`,`parent_column_id`,`album_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='专区搜索专辑按周排行榜';

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='专区APP停留时长';

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='专区按日APP停留时长';

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='专区按周APP停留时长';

-- ----------------------------
-- Table structure for app_subscribe_label_count
-- ----------------------------
DROP TABLE IF EXISTS `app_subscribe_label_count`;
CREATE TABLE `app_subscribe_label_count` (
  `t_date` varchar(255) NOT NULL COMMENT '日期',
  `parent_column_id` varchar(255) NOT NULL COMMENT '专区栏目ID',
  `label_id` int(11) NOT NULL COMMENT '标签ID',
  `label_name` varchar(255) NOT NULL COMMENT '标签名称',
  `subscribe_count` int(11) NOT NULL DEFAULT '0' COMMENT '累计订阅数',
  UNIQUE KEY `t_date` (`t_date`,`parent_column_id`,`label_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='统计周期累计订阅数';

-- ----------------------------
-- Table structure for app_video_play_count_rank_day
-- ----------------------------
DROP TABLE IF EXISTS `app_video_play_count_rank_day`;
CREATE TABLE `app_video_play_count_rank_day` (
  `t_date` varchar(255) NOT NULL,
  `parent_column_id` varchar(255) NOT NULL,
  `album_id` int(11) NOT NULL,
  `album_name` varchar(255) DEFAULT NULL,
  `content_type` int(11) DEFAULT NULL,
  `content_type_name` varchar(255) DEFAULT NULL,
  `video_id` int(11) DEFAULT NULL,
  `video_name` varchar(255) DEFAULT NULL,
  `play_count` bigint(20) DEFAULT NULL,
  UNIQUE KEY `t_date` (`t_date`,`parent_column_id`,`album_id`,`video_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='专区剧集播放次数排行榜';

-- ----------------------------
-- Table structure for app_video_play_count_rank_week
-- ----------------------------
DROP TABLE IF EXISTS `app_video_play_count_rank_week`;
CREATE TABLE `app_video_play_count_rank_week` (
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
  UNIQUE KEY `y_w` (`y`,`w`,`parent_column_id`,`album_id`,`video_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='专辑播放次数排行榜';

-- ----------------------------
-- Table structure for app_video_user_count_rank_day
-- ----------------------------
DROP TABLE IF EXISTS `app_video_user_count_rank_day`;
CREATE TABLE `app_video_user_count_rank_day` (
  `t_date` varchar(255) NOT NULL,
  `parent_column_id` varchar(255) NOT NULL,
  `album_id` int(11) NOT NULL,
  `album_name` varchar(255) DEFAULT NULL,
  `content_type` int(11) DEFAULT NULL,
  `content_type_name` varchar(255) DEFAULT NULL,
  `video_id` int(11) DEFAULT NULL,
  `video_name` varchar(255) DEFAULT NULL,
  `user_count` bigint(20) DEFAULT NULL,
  UNIQUE KEY `t_date` (`t_date`,`parent_column_id`,`album_id`,`video_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='专辑播放用户数排行榜';

-- ----------------------------
-- Table structure for app_video_user_count_rank_week
-- ----------------------------
DROP TABLE IF EXISTS `app_video_user_count_rank_week`;
CREATE TABLE `app_video_user_count_rank_week` (
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
  UNIQUE KEY `y_w` (`y`,`w`,`parent_column_id`,`album_id`,`video_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='剧集播放用户数排行榜';

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户来源按日统计';

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户来源按周统计';

-- ----------------------------
-- Table structure for clean_user
-- ----------------------------
DROP TABLE IF EXISTS `clean_user`;
CREATE TABLE `clean_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) DEFAULT NULL COMMENT '用户ID',
  `user_type` varchar(255) DEFAULT NULL COMMENT '类型VOD和OTT',
  `is_effective` tinyint(4) DEFAULT '1' COMMENT '是否启用 0禁用 1启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='日志中需清理用户ID表';

-- ----------------------------
-- Table structure for noapp_area_visit_count_day
-- ----------------------------
DROP TABLE IF EXISTS `noapp_area_visit_count_day`;
CREATE TABLE `noapp_area_visit_count_day` (
  `t_date` char(10) NOT NULL COMMENT '日期',
  `user_type` varchar(255) NOT NULL COMMENT '用户类型',
  `area_code` varchar(255) DEFAULT NULL,
  `page_user_count` int(11) NOT NULL DEFAULT '0' COMMENT '访问用户数',
  `play_user_count` int(11) NOT NULL DEFAULT '0' COMMENT '播放用户数',
  `play_count` bigint(20) NOT NULL DEFAULT '0' COMMENT '播放次数',
  `duration` bigint(20) NOT NULL DEFAULT '0' COMMENT '累计播放时长',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY `t_date` (`t_date`,`user_type`,`area_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='非专区按天统计信息';

-- ----------------------------
-- Table structure for noapp_area_visit_count_week
-- ----------------------------
DROP TABLE IF EXISTS `noapp_area_visit_count_week`;
CREATE TABLE `noapp_area_visit_count_week` (
  `y` char(10) NOT NULL COMMENT '年份',
  `w` char(10) NOT NULL COMMENT '周',
  `user_type` varchar(10) NOT NULL COMMENT '用户类型',
  `area_code` varchar(10) DEFAULT NULL COMMENT '区域码',
  `page_user_count` int(11) NOT NULL DEFAULT '0' COMMENT '访问用户数',
  `play_user_count` int(11) NOT NULL DEFAULT '0' COMMENT '播放用户数',
  `play_count` bigint(20) NOT NULL DEFAULT '0' COMMENT '播放次数',
  `duration` bigint(20) NOT NULL DEFAULT '0' COMMENT '累计播放时长',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY `uk_user` (`y`,`w`,`user_type`,`area_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='非专区按周统计信息';

drop table IF EXISTS app_album_content_type_month;
create table IF NOT EXISTS app_album_content_type_month(
     y char(4) COMMENT '年',
     m char(2) COMMENT '月',
     user_type char(3) COMMENT '用户类型',
     parent_column_Id varchar(20) COMMENT '专区ID',
     content_type varchar(20) COMMENT '专辑类型',
     content_type_name varchar(50) COMMENT '专辑类型名称',
     user_count INT COMMENT '用户数',
     play_count BIGINT COMMENT '播放次数',
     duration BIGINT COMMENT '播放时长',
  KEY u_idx (y,m,user_type,parent_column_id,content_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='专辑类型月统计';


drop table IF EXISTS app_album_content_type_week;
create table IF NOT EXISTS app_album_content_type_week(
     y char(4) COMMENT '年',
     w char(2) COMMENT '月',
     user_type char(3) COMMENT '用户类型',
     parent_column_Id varchar(20) COMMENT '专区ID',
     content_type varchar(20) COMMENT '专辑类型',
     content_type_name varchar(50) COMMENT '专辑类型名称',
     user_count INT COMMENT '用户数',
     play_count BIGINT COMMENT '播放次数',
     duration BIGINT COMMENT '播放时长',
  KEY u_idx (y,w,user_type,parent_column_id,content_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='专辑类型周统计';


drop table IF EXISTS app_album_content_type_day;
create table IF NOT EXISTS app_album_content_type_day(
     t_date char(12) COMMENT '日期',
     user_type char(3) COMMENT '用户类型',
     parent_column_Id varchar(20) COMMENT '专区ID',
     content_type varchar(20) COMMENT '专辑类型',
     content_type_name varchar(50) COMMENT '专辑类型名称',
     user_count INT COMMENT '用户数',
     play_count BIGINT COMMENT '播放次数',
     duration BIGINT COMMENT '播放时长',
  KEY u_idx (t_date,user_type,parent_column_id,content_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='专辑类型日统计';

-- ----------------------------
-- Table structure for product_column
-- ----------------------------
DROP TABLE IF EXISTS `product_column`;
CREATE TABLE `product_column` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `column_id` varchar(255) NOT NULL COMMENT '栏目ID',
  `column_name` varchar(255) DEFAULT NULL COMMENT '栏目名称',
  `user_type` varchar(255) DEFAULT NULL COMMENT '专区用户类型，用来修正接口错误传值',
  `is_effective` tinyint(1) DEFAULT '1' COMMENT '1有效 0禁用',
  `start_date` date DEFAULT NULL COMMENT '启用时间',
  `end_date` date DEFAULT NULL COMMENT '终止日期',
  PRIMARY KEY (`id`),
  UNIQUE KEY `column_id` (`column_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='现网产品统计';

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
  KEY `uid` (`user_id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=221 DEFAULT CHARSET=utf8 COMMENT='系统操作日志';

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
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 COMMENT='系统用户表';

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
-- Table structure for t_area_info
-- ----------------------------
DROP TABLE IF EXISTS `t_area_info`;
CREATE TABLE `t_area_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` char(3) NOT NULL DEFAULT 'ott' COMMENT 'ott,vod',
  `area_no` varchar(255) NOT NULL COMMENT '地区编码',
  `area_name` varchar(255) DEFAULT NULL COMMENT '地区名称',
  `city_no` varchar(255) DEFAULT NULL COMMENT '城市编码',
  `city_name` varchar(255) DEFAULT NULL COMMENT '城市名称',
  `is_effective` int(2) DEFAULT '1' COMMENT '状态',
  PRIMARY KEY (`id`),
  UNIQUE KEY `area_no_type` (`type`,`area_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

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
  UNIQUE KEY `parent_column_id` (`parent_column_id`,`user_type`,`user_id`),
  KEY `user_type` (`user_type`,`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息';

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
) ENGINE=InnoDB AUTO_INCREMENT=971 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8 COMMENT='导出table维护信息';

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

-- ----------------------------
-- Procedure structure for update_area_to_user
-- ----------------------------
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
