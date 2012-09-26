/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50524
Source Host           : localhost:3306
Source Database       : platform

Target Server Type    : MYSQL
Target Server Version : 50524
File Encoding         : 65001

Date: 2012-08-21 14:49:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_account`
-- ----------------------------
DROP TABLE IF EXISTS `t_account`;
CREATE TABLE `t_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(30) NOT NULL COMMENT '邮箱',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `nick_name` varchar(50) DEFAULT NULL COMMENT '昵称',
  `gender` tinyint(4) NOT NULL DEFAULT '0' COMMENT '性别 0：女 1：男 2：保密',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `photo` varchar(200) DEFAULT NULL COMMENT '头像',
  `thumb_photo` varchar(200) DEFAULT NULL,
  `balance` double DEFAULT '0' COMMENT '余额',
  `real_name` varchar(20) DEFAULT NULL COMMENT '真实姓名',
  `id_card` varchar(18) DEFAULT NULL COMMENT '身份证',
  `office_phone` varchar(18) DEFAULT NULL COMMENT '办公电话',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机号码',
  `address` varchar(200) DEFAULT NULL COMMENT '联系地址',
  `last_login_ip` varchar(18) DEFAULT NULL COMMENT '最后登录Ip',
  `last_login_time` timestamp NULL DEFAULT NULL COMMENT '最后登录时间',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态：0 停用；0x1 启用；0x2：禁止登录（密码错误次数过多，一定时间内禁止登录）；0x4：封号',
  `update_time` timestamp NULL DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT '2012-08-14 00:00:00',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_t_account_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_account
-- ----------------------------
INSERT INTO `t_account` VALUES ('1', 'xiegc@carit.com.cn', 'eb4475fa6839e3253bc5ade5bbdd2a72', '谢庚才', '1', null, 'http://android-in-car.com/photos/user_21_1368533818823896.jpg', 'http://android-in-car.com/photos/user_21_1368533818823896_thumb.jpg', '0', '谢庚才', null, '111222', null, null, '127.0.0.1', '2012-08-21 14:12:16', '1', '2012-08-21 14:12:16', '2012-06-06 16:34:00');
INSERT INTO `t_account` VALUES ('2', 'test@test.com', 'f1a10f73fb49cf19e04cfcd3dd49f0c9', 'test', '0', null, 'http://localhost/photos/user_2_7124369995754.jpg', 'http://localhost/photos/user_2_7124369995754_thumb.jpg', '0', null, null, null, null, null, '127.0.0.1', '2012-08-20 11:00:25', '1', '2012-08-20 11:00:25', '2012-08-20 10:16:01');

-- ----------------------------
-- Table structure for `t_admin_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_admin_user`;
CREATE TABLE `t_admin_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(30) NOT NULL COMMENT '邮箱',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `nick_name` varchar(20) DEFAULT NULL COMMENT '昵称',
  `real_name` varchar(20) DEFAULT NULL COMMENT '真实姓名',
  `gender` tinyint(4) NOT NULL COMMENT '性别 0：保密；1：男；2：女',
  `update_time` timestamp NULL DEFAULT '2012-05-08 11:53:35',
  `create_time` timestamp NOT NULL DEFAULT '2012-05-08 11:53:35',
  `status` int(11) NOT NULL COMMENT '状态：0 停用；0x1 启用；0x2：禁止登录（密码错误次数过多，一定时间内禁止登录）',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `last_login_ip` varchar(18) DEFAULT NULL COMMENT '最后登录Ip',
  `last_login_time` timestamp NULL DEFAULT NULL COMMENT '最后登录时间',
  `office_phone` varchar(18) DEFAULT NULL COMMENT '办公电话',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机号码',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_t_base_user_email` (`email`),
  UNIQUE KEY `idx_t_base_nick_name` (`nick_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='后台管理用户';

-- ----------------------------
-- Records of t_admin_user
-- ----------------------------
INSERT INTO `t_admin_user` VALUES ('3', 'admin@admin.com', 'fd8922228b92a18e611567b3195126ac', '系统管理员', null, '1', '2012-08-21 11:42:37', '2012-08-03 17:56:47', '1', null, '127.0.0.1', '2012-08-21 11:42:37', null, '');

-- ----------------------------
-- Table structure for `t_catalog`
-- ----------------------------
DROP TABLE IF EXISTS `t_catalog`;
CREATE TABLE `t_catalog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL COMMENT '分类名称',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  `display_index` int(11) NOT NULL COMMENT '显示顺序',
  `status` tinyint(4) NOT NULL COMMENT '状态：0 停用， 1 启用',
  `create_time` timestamp NOT NULL DEFAULT '2012-05-08 11:53:35',
  `update_time` timestamp NOT NULL DEFAULT '2012-05-08 11:53:35',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_t_catalog_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_catalog
-- ----------------------------
INSERT INTO `t_catalog` VALUES ('1', '测试', '测试', '2', '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_catalog` VALUES ('5', '音乐视听', '音乐视听', '1', '1', '2012-08-13 16:46:05', '2012-08-13 16:46:21');

-- ----------------------------
-- Table structure for `t_favorite`
-- ----------------------------
DROP TABLE IF EXISTS `t_favorite`;
CREATE TABLE `t_favorite` (
  `account_id` int(11) NOT NULL,
  `navigation_id` int(11) NOT NULL,
  PRIMARY KEY (`account_id`,`navigation_id`),
  KEY `idx_t_favorite_account_id` (`account_id`),
  KEY `idx_t_favorite_account_navigation_id` (`navigation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_favorite
-- ----------------------------
INSERT INTO `t_favorite` VALUES ('1', '1');
INSERT INTO `t_favorite` VALUES ('1', '2');
INSERT INTO `t_favorite` VALUES ('1', '3');
INSERT INTO `t_favorite` VALUES ('1', '9');

-- ----------------------------
-- Table structure for `t_navigation`
-- ----------------------------
DROP TABLE IF EXISTS `t_navigation`;
CREATE TABLE `t_navigation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL COMMENT '名称',
  `catalog_id` int(11) NOT NULL COMMENT '所属分类',
  `url` varchar(100) NOT NULL,
  `css_class` varchar(30) DEFAULT NULL COMMENT 'CSS样式类',
  `display_index` int(11) NOT NULL COMMENT '显示顺序',
  `logo` varchar(100) DEFAULT NULL,
  `status` tinyint(4) NOT NULL COMMENT '状态：0 停用， 1 启用',
  `create_time` timestamp NOT NULL DEFAULT '2012-05-08 11:53:35',
  `update_time` timestamp NOT NULL DEFAULT '2012-05-08 11:53:35',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_navigation
-- ----------------------------
INSERT INTO `t_navigation` VALUES ('1', '百度音乐', '5', 'http://mp3.baidu.com', 'purple_300_100', '2', null, '2', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_navigation` VALUES ('2', '豆瓣电台', '5', 'http://music.douban.com/', 'ndigo_150_100', '3', null, '2', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_navigation` VALUES ('3', '网易视频', '1', 'http://v.163.com', 'blue_300_100', '4', 'http://img1.cache.netease.com/video/img11/movie/logo.png', '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_navigation` VALUES ('4', '腾讯视频', '1', 'http://v.qq.com/', 'green_150_100', '5', null, '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_navigation` VALUES ('8', '新浪科技', '5', 'http://www.sina.com', 'yellow_300_100', '1', 'http://i2.sinaimg.cn/dy/deco/2012/0406/sina_logo_2012_olympic_01.png', '1', '2012-08-14 16:07:22', '2012-08-21 11:45:49');
INSERT INTO `t_navigation` VALUES ('9', '优酷', '5', 'http://www.youku.com', 'orange_300_100', '5', 'images/f13a1f36afd820eaf9829428c9388c11.r.jpg', '2', '2012-08-16 15:26:05', '2012-08-16 15:29:46');
INSERT INTO `t_navigation` VALUES ('10', '土豆网', '5', 'http://www.tudou.com', 'red_150_100', '8', 'images/4c8d8717c1c68c354402dcdfc95216c2.r.jpg', '2', '2012-08-16 15:34:15', '2012-08-16 15:34:15');
