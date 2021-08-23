/*
Navicat MySQL Data Transfer

Source Server         : 陕西2.0数据采集
Source Server Version : 50633
Source Host           : 10.43.127.204:3306
Source Database       : sx_hadoop2

Target Server Type    : MYSQL
Target Server Version : 50633
File Encoding         : 65001

Date: 2020-10-23 10:01:33
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
) ENGINE=InnoDB AUTO_INCREMENT=213 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_area_info
-- ----------------------------
INSERT INTO `t_area_info` VALUES ('107', 'OTT', '1001', '西安本级', 'XA', '西安', '1');
INSERT INTO `t_area_info` VALUES ('108', 'OTT', '1002', '西安临潼支公司', 'XA', '西安', '1');
INSERT INTO `t_area_info` VALUES ('109', 'OTT', '1003', '西安飞机城支公司', 'XA', '西安', '1');
INSERT INTO `t_area_info` VALUES ('110', 'OTT', '1004', '西安高陵支公司', 'XA', '西安', '1');
INSERT INTO `t_area_info` VALUES ('111', 'OTT', '1005', '西安户县支公司', 'XA', '西安', '1');
INSERT INTO `t_area_info` VALUES ('112', 'OTT', '1006', '西安周至支公司', 'XA', '西安', '1');
INSERT INTO `t_area_info` VALUES ('113', 'OTT', '1007', '西安蓝田支公司', 'XA', '西安', '1');
INSERT INTO `t_area_info` VALUES ('115', 'OTT', '1100', '宝鸡本级', 'BJ', '宝鸡', '1');
INSERT INTO `t_area_info` VALUES ('116', 'OTT', '1101', '宝鸡凤县支公司', 'BJ', '宝鸡', '1');
INSERT INTO `t_area_info` VALUES ('117', 'OTT', '1102', '宝鸡凤翔支公司', 'BJ', '宝鸡', '1');
INSERT INTO `t_area_info` VALUES ('118', 'OTT', '1103', '宝鸡扶风支公司', 'BJ', '宝鸡', '1');
INSERT INTO `t_area_info` VALUES ('119', 'OTT', '1104', '宝鸡麟游支公司', 'BJ', '宝鸡', '1');
INSERT INTO `t_area_info` VALUES ('120', 'OTT', '1105', '宝鸡陇县支公司', 'BJ', '宝鸡', '1');
INSERT INTO `t_area_info` VALUES ('121', 'OTT', '1106', '宝鸡眉县支公司', 'BJ', '宝鸡', '1');
INSERT INTO `t_area_info` VALUES ('122', 'OTT', '1107', '宝鸡岐山支公司', 'BJ', '宝鸡', '1');
INSERT INTO `t_area_info` VALUES ('123', 'OTT', '1108', '宝鸡千阳支公司', 'BJ', '宝鸡', '1');
INSERT INTO `t_area_info` VALUES ('124', 'OTT', '1109', '宝鸡太白支公司', 'BJ', '宝鸡', '1');
INSERT INTO `t_area_info` VALUES ('125', 'OTT', '1110', '杨凌公司', 'BJ', '宝鸡', '1');
INSERT INTO `t_area_info` VALUES ('126', 'OTT', '1200', '咸阳本级', 'XY', '咸阳', '1');
INSERT INTO `t_area_info` VALUES ('127', 'OTT', '1201', '咸阳兴平支公司', 'XY', '咸阳', '1');
INSERT INTO `t_area_info` VALUES ('128', 'OTT', '1202', '咸阳旬邑支公司', 'XY', '咸阳', '1');
INSERT INTO `t_area_info` VALUES ('129', 'OTT', '1203', '咸阳长武支公司', 'XY', '咸阳', '1');
INSERT INTO `t_area_info` VALUES ('130', 'OTT', '1204', '咸阳淳化支公司', 'XY', '咸阳', '1');
INSERT INTO `t_area_info` VALUES ('131', 'OTT', '1205', '咸阳彬县支公司', 'XY', '咸阳', '1');
INSERT INTO `t_area_info` VALUES ('132', 'OTT', '1206', '咸阳三原支公司', 'XY', '咸阳', '1');
INSERT INTO `t_area_info` VALUES ('133', 'OTT', '1207', '咸阳永寿支公司', 'XY', '咸阳', '1');
INSERT INTO `t_area_info` VALUES ('134', 'OTT', '1208', '咸阳乾县支公司', 'XY', '咸阳', '1');
INSERT INTO `t_area_info` VALUES ('135', 'OTT', '1209', '咸阳武功支公司', 'XY', '咸阳', '1');
INSERT INTO `t_area_info` VALUES ('136', 'OTT', '1210', '咸阳礼泉支公司', 'XY', '咸阳', '1');
INSERT INTO `t_area_info` VALUES ('137', 'OTT', '1300', '渭南本级', 'WN', '渭南', '1');
INSERT INTO `t_area_info` VALUES ('138', 'OTT', '1301', '渭南临渭支公司', 'WN', '渭南', '1');
INSERT INTO `t_area_info` VALUES ('139', 'OTT', '1302', '渭南白水支公司', 'WN', '渭南', '1');
INSERT INTO `t_area_info` VALUES ('140', 'OTT', '1303', '渭南富平支公司', 'WN', '渭南', '1');
INSERT INTO `t_area_info` VALUES ('141', 'OTT', '1304', '渭南蒲城支公司', 'WN', '渭南', '1');
INSERT INTO `t_area_info` VALUES ('142', 'OTT', '1306', '渭南合阳支公司', 'WN', '渭南', '1');
INSERT INTO `t_area_info` VALUES ('143', 'OTT', '1307', '渭南澄城支公司', 'WN', '渭南', '1');
INSERT INTO `t_area_info` VALUES ('144', 'OTT', '1308', '渭南大荔支公司', 'WN', '渭南', '1');
INSERT INTO `t_area_info` VALUES ('145', 'OTT', '1309', '渭南华阴支公司', 'WN', '渭南', '1');
INSERT INTO `t_area_info` VALUES ('146', 'OTT', '1310', '渭南华州区支公司', 'WN', '渭南', '1');
INSERT INTO `t_area_info` VALUES ('147', 'OTT', '1311', '渭南潼关支公司', 'WN', '渭南', '1');
INSERT INTO `t_area_info` VALUES ('148', 'OTT', '1400', '铜川本级', 'TC', '铜川', '1');
INSERT INTO `t_area_info` VALUES ('149', 'OTT', '1401', '铜川宜君县', 'TC', '铜川', '1');
INSERT INTO `t_area_info` VALUES ('150', 'OTT', '1402', '铜川新区', 'TC', '铜川', '1');
INSERT INTO `t_area_info` VALUES ('151', 'OTT', '1403', '铜川耀州区', 'TC', '铜川', '1');
INSERT INTO `t_area_info` VALUES ('152', 'OTT', '1500', '延安本级', 'YA', '延安', '1');
INSERT INTO `t_area_info` VALUES ('153', 'OTT', '1501', '延安宝塔支公司', 'YA', '延安', '1');
INSERT INTO `t_area_info` VALUES ('154', 'OTT', '1502', '延安富县支公司', 'YA', '延安', '1');
INSERT INTO `t_area_info` VALUES ('155', 'OTT', '1503', '延安子长支公司', 'YA', '延安', '1');
INSERT INTO `t_area_info` VALUES ('156', 'OTT', '1504', '延安黄陵支公司', 'YA', '延安', '1');
INSERT INTO `t_area_info` VALUES ('157', 'OTT', '1505', '延安洛川支公司', 'YA', '延安', '1');
INSERT INTO `t_area_info` VALUES ('158', 'OTT', '1506', '延安延川支公司', 'YA', '延安', '1');
INSERT INTO `t_area_info` VALUES ('159', 'OTT', '1507', '延安延长支公司', 'YA', '延安', '1');
INSERT INTO `t_area_info` VALUES ('160', 'OTT', '1508', '延安宜川支公司', 'YA', '延安', '1');
INSERT INTO `t_area_info` VALUES ('161', 'OTT', '1509', '延安黄龙支公司', 'YA', '延安', '1');
INSERT INTO `t_area_info` VALUES ('162', 'OTT', '1510', '延安安塞支公司', 'YA', '延安', '1');
INSERT INTO `t_area_info` VALUES ('163', 'OTT', '1511', '延安甘泉支公司', 'YA', '延安', '1');
INSERT INTO `t_area_info` VALUES ('164', 'OTT', '1512', '延安志丹支公司', 'YA', '延安', '1');
INSERT INTO `t_area_info` VALUES ('165', 'OTT', '1513', '延安吴起支公司', 'YA', '延安', '1');
INSERT INTO `t_area_info` VALUES ('166', 'OTT', '1600', '榆林本级', 'YL', '榆林', '1');
INSERT INTO `t_area_info` VALUES ('167', 'OTT', '1601', '榆林靖边支公司', 'YL', '榆林', '1');
INSERT INTO `t_area_info` VALUES ('168', 'OTT', '1602', '榆林定边支公司', 'YL', '榆林', '1');
INSERT INTO `t_area_info` VALUES ('169', 'OTT', '1603', '榆林米脂支公司', 'YL', '榆林', '1');
INSERT INTO `t_area_info` VALUES ('170', 'OTT', '1604', '榆林清涧支公司', 'YL', '榆林', '1');
INSERT INTO `t_area_info` VALUES ('171', 'OTT', '1605', '榆林吴堡支公司', 'YL', '榆林', '1');
INSERT INTO `t_area_info` VALUES ('172', 'OTT', '1606', '榆林神木支公司', 'YL', '榆林', '1');
INSERT INTO `t_area_info` VALUES ('173', 'OTT', '1607', '榆林府谷支公司', 'YL', '榆林', '1');
INSERT INTO `t_area_info` VALUES ('174', 'OTT', '1608', '榆林横山支公司', 'YL', '榆林', '1');
INSERT INTO `t_area_info` VALUES ('175', 'OTT', '1609', '榆林绥德支公司', 'YL', '榆林', '1');
INSERT INTO `t_area_info` VALUES ('176', 'OTT', '1610', '榆林子洲支公司', 'YL', '榆林', '1');
INSERT INTO `t_area_info` VALUES ('177', 'OTT', '1611', '榆林佳县支公司', 'YL', '榆林', '1');
INSERT INTO `t_area_info` VALUES ('178', 'OTT', '1700', '汉中本级', 'HZ', '汉中', '1');
INSERT INTO `t_area_info` VALUES ('179', 'OTT', '1701', '汉中城固支公司', 'HZ', '汉中', '1');
INSERT INTO `t_area_info` VALUES ('180', 'OTT', '1702', '汉中佛坪支公司', 'HZ', '汉中', '1');
INSERT INTO `t_area_info` VALUES ('181', 'OTT', '1703', '汉中勉县支公司', 'HZ', '汉中', '1');
INSERT INTO `t_area_info` VALUES ('182', 'OTT', '1704', '汉中宁强支公司', 'HZ', '汉中', '1');
INSERT INTO `t_area_info` VALUES ('183', 'OTT', '1705', '汉中洋县支公司', 'HZ', '汉中', '1');
INSERT INTO `t_area_info` VALUES ('184', 'OTT', '1706', '汉中留坝支公司', 'HZ', '汉中', '1');
INSERT INTO `t_area_info` VALUES ('185', 'OTT', '1707', '汉中镇巴支公司', 'HZ', '汉中', '1');
INSERT INTO `t_area_info` VALUES ('186', 'OTT', '1708', '汉中略阳支公司', 'HZ', '汉中', '1');
INSERT INTO `t_area_info` VALUES ('187', 'OTT', '1709', '汉中南郑支公司', 'HZ', '汉中', '1');
INSERT INTO `t_area_info` VALUES ('188', 'OTT', '1710', '汉中西乡支公司', 'HZ', '汉中', '1');
INSERT INTO `t_area_info` VALUES ('189', 'OTT', '1800', '安康本级', 'AK', '安康', '1');
INSERT INTO `t_area_info` VALUES ('190', 'OTT', '1801', '安康石泉支公司', 'AK', '安康', '1');
INSERT INTO `t_area_info` VALUES ('191', 'OTT', '1802', '安康宁陕支公司', 'AK', '安康', '1');
INSERT INTO `t_area_info` VALUES ('192', 'OTT', '1803', '安康汉阴支公司', 'AK', '安康', '1');
INSERT INTO `t_area_info` VALUES ('193', 'OTT', '1804', '安康紫阳支公司', 'AK', '安康', '1');
INSERT INTO `t_area_info` VALUES ('194', 'OTT', '1805', '安康旬阳支公司', 'AK', '安康', '1');
INSERT INTO `t_area_info` VALUES ('195', 'OTT', '1806', '安康平利支公司', 'AK', '安康', '1');
INSERT INTO `t_area_info` VALUES ('196', 'OTT', '1807', '安康岚皋支公司', 'AK', '安康', '1');
INSERT INTO `t_area_info` VALUES ('197', 'OTT', '1808', '安康白河支公司', 'AK', '安康', '1');
INSERT INTO `t_area_info` VALUES ('198', 'OTT', '1809', '安康镇坪支公司', 'AK', '安康', '1');
INSERT INTO `t_area_info` VALUES ('199', 'OTT', '1900', '商洛本级', 'SL', '商洛', '1');
INSERT INTO `t_area_info` VALUES ('200', 'OTT', '1901', '商洛商州支公司', 'SL', '商洛', '1');
INSERT INTO `t_area_info` VALUES ('201', 'OTT', '1902', '商洛山阳支公司', 'SL', '商洛', '1');
INSERT INTO `t_area_info` VALUES ('202', 'OTT', '1903', '商洛柞水支公司', 'SL', '商洛', '1');
INSERT INTO `t_area_info` VALUES ('203', 'OTT', '1904', '商洛镇安支公司', 'SL', '商洛', '1');
INSERT INTO `t_area_info` VALUES ('204', 'OTT', '1905', '商洛丹凤支公司', 'SL', '商洛', '1');
INSERT INTO `t_area_info` VALUES ('205', 'OTT', '1906', '商洛商南支公司', 'SL', '商洛', '1');
INSERT INTO `t_area_info` VALUES ('206', 'OTT', '1907', '商洛洛南支公司', 'SL', '商洛', '1');
INSERT INTO `t_area_info` VALUES ('207', 'OTT', '2000', '西咸西安', 'XX', '西咸', '1');
INSERT INTO `t_area_info` VALUES ('208', 'OTT', '2001', '西咸咸阳', 'XX', '西咸', '1');
INSERT INTO `t_area_info` VALUES ('209', 'OTT', '1000', '省公司', 'SX', '陕西', '1');
INSERT INTO `t_area_info` VALUES ('210', 'OTT', '2200', '陈仓直属公司', 'CC', '陈仓', '1');
INSERT INTO `t_area_info` VALUES ('211', 'OTT', '2300', '长安直属公司', 'CA', '长安', '1');
INSERT INTO `t_area_info` VALUES ('212', 'OTT', '2400', '韩城直属公司', 'HC', '韩城', '1');
