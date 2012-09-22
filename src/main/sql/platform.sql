/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50524
Source Host           : localhost:3306
Source Database       : platform

Target Server Type    : MYSQL
Target Server Version : 50524
File Encoding         : 65001

Date: 2012-09-21 18:06:54
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_account
-- ----------------------------
INSERT INTO `t_account` VALUES ('1', 'xiegc@carit.com.cn', 'eb4475fa6839e3253bc5ade5bbdd2a72', '谢庚才', '1', '1986-06-08', 'http://android-in-car.com/photos/user_21_1368533818823896.jpg', 'http://android-in-car.com/photos/user_21_1368533818823896_thumb.jpg', '0', '谢庚才', null, '111222', null, null, '127.0.0.1', '2012-09-21 14:48:08', '1', '2012-09-21 16:33:37', '2012-06-06 16:34:00');
INSERT INTO `t_account` VALUES ('2', 'test@test.com', 'f1a10f73fb49cf19e04cfcd3dd49f0c9', 'test', '0', null, 'http://localhost/photos/user_2_7124369995754.jpg', 'http://localhost/photos/user_2_7124369995754_thumb.jpg', '0', null, null, null, null, null, '127.0.0.1', '2012-08-20 11:00:25', '1', '2012-08-20 11:00:25', '2012-08-20 10:16:01');
INSERT INTO `t_account` VALUES ('5', 'xiegc@carit.com', 'bb64ccd7d678cd7de03a9dd26d46aff3', '風一樣的男子', '0', null, null, null, '0', null, null, null, null, null, null, null, '1', '2012-09-20 17:18:20', '2012-09-20 17:18:20');

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
-- Table structure for `t_app_secret`
-- ----------------------------
DROP TABLE IF EXISTS `t_app_secret`;
CREATE TABLE `t_app_secret` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_secret` varchar(36) NOT NULL,
  `app_name` varchar(100) DEFAULT NULL,
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态：0 停用；0x1 启用；',
  `create_time` timestamp NOT NULL DEFAULT '2012-08-14 00:00:00',
  `update_time` timestamp NOT NULL DEFAULT '2012-05-08 11:53:35',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_app_secret
-- ----------------------------
INSERT INTO `t_app_secret` VALUES ('1', 'abcdeabcdeabcdeabcdeabcde', null, '1', '2012-08-14 00:00:00', '2012-05-08 11:53:35');
INSERT INTO `t_app_secret` VALUES ('2', 'abcdeabcdeabcdeabcdeaaaaa', null, '1', '2012-08-14 00:00:00', '2012-05-08 11:53:35');
INSERT INTO `t_app_secret` VALUES ('3', 'abcdeabcdeabcdeabcdeaaaaa', null, '1', '2012-08-14 00:00:00', '2012-05-08 11:53:35');
