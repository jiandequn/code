DROP TABLE IF EXISTS login_page_log;
CREATE TABLE IF NOT EXISTS `login_page_log` (
  `parent_column_id` varchar(255) DEFAULT NULL COMMENT '???',
  `user_type` varchar(255) DEFAULT NULL COMMENT '????',
  `mac` varchar(255) DEFAULT NULL COMMENT 'MAC',
  `sn` varchar(255) DEFAULT NULL COMMENT 'SN',
  `user_id` varchar(255) DEFAULT NULL COMMENT '??ID',
  `column_id` varchar(255) DEFAULT NULL COMMENT '??ID',
  `after_column_id` varchar(255) DEFAULT NULL COMMENT '???ID',
  `after_column_code` varchar(255) DEFAULT NULL COMMENT '???Code',
  `area_code` varchar(255) DEFAULT NULL COMMENT '??code',
  `create_time` varchar(255) DEFAULT NULL COMMENT '????'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='??????';

DROP TABLE IF EXISTS album_play_log;
CREATE TABLE IF NOT EXISTS `album_play_log` (
  `parent_column_id` varchar(64) DEFAULT NULL COMMENT '???',
  `user_type` char(3) DEFAULT NULL COMMENT '????',
  `mac` varchar(64) DEFAULT NULL COMMENT 'MAC',
  `sn` varchar(64) DEFAULT NULL COMMENT 'SN',
  `user_id` varchar(64) DEFAULT NULL COMMENT '??ID',
  `album_id` varchar(64) DEFAULT NULL COMMENT '??ID',
  `album_name` varchar(255) DEFAULT NULL COMMENT '????',
  `content_type` varchar(64) DEFAULT NULL COMMENT '????',
  `content_type_name` varchar(64) DEFAULT NULL COMMENT '??????',
   `labels` varchar(64) DEFAULT NULL COMMENT '????',
  `video_id` varchar(64) DEFAULT NULL COMMENT '??ID',
  video_name varchar(255) DEFAULT NULL COMMENT '????',
   video_duration varchar(100) DEFAULT NULL comment '????',
  `time_position` varchar(100) DEFAULT NULL COMMENT '????',
  `create_time` varchar(64) DEFAULT NULL COMMENT '????'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='??????';

DROP TABLE IF EXISTS detail_page_log;
CREATE TABLE IF NOT EXISTS `detail_page_log` (
  `parent_column_id` INT DEFAULT NULL COMMENT '???',
  `user_type` char(3) DEFAULT NULL COMMENT '????',
  `mac` varchar(20) DEFAULT NULL COMMENT 'MAC',
  `sn` varchar(40) DEFAULT NULL COMMENT 'SN',
  `user_id` varchar(64) DEFAULT NULL COMMENT '??ID',
  `column_id` varchar(64) DEFAULT NULL COMMENT '??ID',
  `album_id` varchar(64) DEFAULT NULL COMMENT '??ID',
  `content_type` varchar(10) DEFAULT NULL COMMENT '????1?? 2??',
  `after_column_id` varchar(64) DEFAULT NULL COMMENT '????ID',
  `after_column_code` varchar(255) DEFAULT NULL COMMENT '????code',
  `area_code` varchar(20) DEFAULT NULL COMMENT '???',
  `create_time` varchar(64) DEFAULT NULL COMMENT '????',
  `album_name` varchar(255) DEFAULT NULL COMMENT '????',
  `album_content_type_name` varchar(64) DEFAULT NULL COMMENT '??????',
  `labels` varchar(64) DEFAULT NULL COMMENT '????'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='?????';

DROP TABLE IF EXISTS detail_page_log;
CREATE TABLE `detail_page_log` (
  `parent_column_id` varchar(255) DEFAULT NULL COMMENT '父栏目',
  `user_type` varchar(255)  DEFAULT NULL COMMENT '用户类型',
  `mac` varchar(255)  DEFAULT NULL COMMENT 'MAC',
  `sn` varchar(255)  DEFAULT NULL COMMENT 'SN',
  `user_id` varchar(255)  DEFAULT NULL COMMENT '用户ID',
  `column_id` varchar(255)  DEFAULT NULL COMMENT '栏目ID',
  `album_id` varchar(255)  DEFAULT NULL COMMENT '专辑ID',
  `content_type` varchar(255)  DEFAULT NULL COMMENT '内容类型1专辑 2专题',
  `after_column_id` varchar(255)  DEFAULT NULL COMMENT '来源栏目ID',
  `after_column_code` varchar(255) DEFAULT NULL COMMENT '来源栏目code',
  `area_code` varchar(255)  DEFAULT NULL COMMENT '区域码',
  `create_time` varchar(255)  DEFAULT NULL COMMENT '创建时间',
  `album_name` varchar(255) DEFAULT NULL COMMENT '专辑名称',
  `album_content_type_name` varchar(255)  DEFAULT NULL COMMENT '专辑类型名称',
  `labels` varchar(255)  DEFAULT NULL COMMENT '二级分类'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='详情页日志';

DROP TABLE IF EXISTS auth_product_user;
CREATE TABLE `auth_product_user` (
  `user_id` varchar(255)  DEFAULT NULL COMMENT '用户ID',
  `mac` varchar(255)  DEFAULT NULL COMMENT 'MAC',
  `sn` varchar(255)  DEFAULT NULL COMMENT 'SN',
  `user_type` varchar(255)  DEFAULT NULL COMMENT '用户类型',
  `area_code` varchar(255)  DEFAULT NULL COMMENT '区域码',
  `third_product_code` varchar(255)  DEFAULT NULL COMMENT '鉴权产品Code',
  `create_time` varchar(255) DEFAULT NULL COMMENT '时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='鉴权用户信息';

DROP TABLE IF EXISTS app_user_stay_duration_week;
CREATE TABLE app_user_stay_duration_week (
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

alter table app_visit_count_week drop index `t_date`;
alter table app_visit_count_week add unique key `uk_user` (`y`,`w`,`parent_column_id`,`user_type`,`area_code`);