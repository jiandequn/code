/*
Navicat MySQL Data Transfer

Source Server         : 192.168.15.50
Source Server Version : 50633
Source Host           : 192.168.15.16:3306
Source Database       : yn_hadoop

Target Server Type    : MYSQL
Target Server Version : 50633
File Encoding         : 65001

Date: 2020-09-09 11:36:04
*/

SET FOREIGN_KEY_CHECKS=0;

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
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_area_info
-- ----------------------------
INSERT INTO `t_area_info` VALUES ('1', 'vod', '1', '昆明', '1', '昆明', '1');
INSERT INTO `t_area_info` VALUES ('3', 'vod', '2', '大理', '2', '大理', '1');
INSERT INTO `t_area_info` VALUES ('5', 'vod', '3', '保山', '3', '保山', '1');
INSERT INTO `t_area_info` VALUES ('7', 'vod', '4', '红河', '4', '红河', '1');
INSERT INTO `t_area_info` VALUES ('9', 'vod', '5', '文山', '5', '文山', '1');
INSERT INTO `t_area_info` VALUES ('11', 'vod', '6', '版纳', '6', '版纳', '1');
INSERT INTO `t_area_info` VALUES ('13', 'vod', '7', '曲靖', '7', '曲靖', '1');
INSERT INTO `t_area_info` VALUES ('15', 'vod', '8', '昭通', '8', '昭通', '1');
INSERT INTO `t_area_info` VALUES ('17', 'vod', '9', '临沧', '9', '临沧', '1');
INSERT INTO `t_area_info` VALUES ('19', 'vod', '10', '楚雄', '10', '楚雄', '1');
INSERT INTO `t_area_info` VALUES ('21', 'vod', 'A', '德宏', 'A', '德宏', '1');
INSERT INTO `t_area_info` VALUES ('23', 'vod', 'B', '普洱', 'B', '普洱', '1');
INSERT INTO `t_area_info` VALUES ('25', 'vod', 'C', '怒江', 'C', '怒江', '1');
INSERT INTO `t_area_info` VALUES ('27', 'vod', 'E', '迪庆', 'E', '迪庆', '1');
INSERT INTO `t_area_info` VALUES ('29', 'vod', '0', '昆明', '0', '昆明', '1');
INSERT INTO `t_area_info` VALUES ('30', 'vod', 'D', '玉溪', 'D', '玉溪', '1');
INSERT INTO `t_area_info` VALUES ('35', 'vod', 'undefined', '未知', '', '', '1');
