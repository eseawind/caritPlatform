-- ----------------------------
-- Table structure for `t_catalog`
-- ----------------------------
DROP TABLE IF EXISTS `t_nav_catalog`;
CREATE TABLE `t_nav_catalog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL COMMENT '分类名称',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  `display_index` int(11) NOT NULL COMMENT '显示顺序',
  `status` tinyint(4) NOT NULL COMMENT '状态：0 停用， 1 启用',
  `create_time` timestamp NOT NULL DEFAULT '2012-05-08 11:53:35',
  `update_time` timestamp NOT NULL DEFAULT '2012-05-08 11:53:35',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_t_catalog_name` (`name`)
);

-- ----------------------------
-- Records of t_catalog
-- ----------------------------
INSERT INTO `t_nav_catalog` VALUES ('1', '车载路况', '车载路况', '1', '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_nav_catalog` VALUES ('2', '新闻咨询', '新闻咨询', '2', '1', '2012-08-13 16:46:05', '2012-08-13 16:46:21');
INSERT INTO `t_nav_catalog` VALUES ('3', '网络视听', '网络视听', '3', '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');

-- ----------------------------
-- Records of t_favorite
-- ----------------------------

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
);

-- ----------------------------
-- Records of t_navigation
-- ----------------------------
INSERT INTO `t_navigation` VALUES ('1', '新浪新闻', '2', 'http://news.sina.com.cn/', 'orange_200_100', '1', 'http://i2.sinaimg.cn/dy/deco/2012/0406/sina_logo_2012_olympic_01.png', '2', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_navigation` VALUES ('2', 'CNTV', '2', 'http://www.cntv.cn/index.shtml', 'yellow_200_100', '2', 'http://p1.img.cctvpic.com/nettv/Olympic/2012716/20120720/images/101193_3028_1343387080700.jpg', '2', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_navigation` VALUES ('3', '网易新闻', '2', 'http://news.163.com/', 'blue_200_100', '3', null, '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_navigation` VALUES ('4', '环球网', '2', 'http://www.huanqiu.com/', 'ndigo_100_100', '4', null, '2', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_navigation` VALUES ('5', '凤凰网', '2', 'http://news.ifeng.com/', 'purple_200_100', '5', 'http://res.img.ifeng.com/8cbe73a7378dafdb/2011/0426/ifengLogo.gif', '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_navigation` VALUES ('6', '人民网', '2', 'http://www.people.com.cn/', 'yellow_100_100', '6', 'http://www.people.com.cn/img/2011people/images/people_logo.gif', '2', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_navigation` VALUES ('7', '南都网', '2', 'http://nd.oeeee.com/', 'ndigo_200_100', '7', 'http://nd.oeeee.com/images/20111228_nd2011_logo1.png', '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_navigation` VALUES ('8', '联合早报', '2', 'http://www.zaobao.com/', 'red_200_100', '8', 'http://www.zaobao.com/ssi/images1/zblogo_original.gif', '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_navigation` VALUES ('9', '腾讯新闻', '2', 'http://news.qq.com/', 'purple_100_100', '9', 'http://mat1.gtimg.com/www/images/channel_logo/news_logo.png', '2', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_navigation` VALUES ('10', '新华网', '2', 'http://www.xinhuanet.com/', 'green_100_100', '10', null, '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_navigation` VALUES ('11', '百度音乐', '3', 'http://mp3.baidu.com/', 'blue_200_100', '11', 'http://list.mp3.baidu.com/images/bXAzaW5kZXg-/v2_0/mp3-logo.png?v=20120625', '2', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_navigation` VALUES ('12', '一听音乐', '3', 'http://www.1ting.com/', 'yellow_200_100', '12', 'http://www.1ting.com/v5/r4/images/top_logo.png', '2', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_navigation` VALUES ('13', '音悦台', '3', 'http://www.yinyuetai.com/', 'ndigo_200_100', '13', 'http://www.yinyuetai.com/images/core/logo.$30991.png', '2', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_navigation` VALUES ('14', '爱奇艺', '3', 'http://www.iqiyi.com/', 'ndigo_100_100', '14', 'http://www.qiyipic.com/common/fix/index_images/logonew.png', '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_navigation` VALUES ('15', '土豆网', '3', 'http://www.tudou.com/', 'orange_200_100', '15', null, '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_navigation` VALUES ('16', '优酷网', '3', 'http://www.youku.com', 'green_200_100', '16', null, '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_navigation` VALUES ('17', '搜狐听书', '3', 'http://ting.m.sohu.com/', 'purple_200_100', '17', 'http://img.mms.sohu.com/m/tingnew/images/logo.jpg', '2', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_navigation` VALUES ('18', '天方听书网', '3', 'http://www.tingbook.com/', 'blue_200_100', '18', 'http://www.tingbook.com/images/logo.gif', '2', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_navigation` VALUES ('19', '艺术中国', '3', 'http://sy.artx.cn/', 'orange_200_100', '19', 'http://sy.artx.cn/images/logo_240_60.gif', '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_navigation` VALUES ('20', '馨雨听书网', '3', 'http://www.xytsw.com/', 'red_200_100', '20', 'http://www.xytsw.com/template/xytsw/images/logo.gif', '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_navigation` VALUES ('21', '百度地图', '1', 'http://map.baidu.com/fwmap/zt/traffic/index.html?city=shenzhen', 'blue_200_100', '21', null, '2', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_navigation` VALUES ('22', '图吧实时路况', '1', 'http://www.mapbar.com/traffic/shenzhen_alive.html', 'orange_200_100', '22', null, '2', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_navigation` VALUES ('23', '导航地图', '1', 'http://lu.ditu6.com/sz/', 'ndigo_200_100', '23', 'http://lu.ditu6.com/logo.gif', '2', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_navigation` VALUES ('24', '搜狗地图-电子眼', '1', 'http://map.sogou.com/store/shenzhen+m=_6444_50cf_5934&q=102325#hb=0,1&c=12694437.5,2562500,11', 'orange_200_100', '24', null, '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_navigation` VALUES ('25', '搜狗地图-加油站', '0', 'http://map.sogou.com/store/shenzhen+m=_52a0_6cb9_7ad9&q=101731#hb=0,1&c=12694437.5,2562500,11', 'blue_200_100', '25', null, '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');