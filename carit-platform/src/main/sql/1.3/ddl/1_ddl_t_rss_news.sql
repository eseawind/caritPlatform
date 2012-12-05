CREATE TABLE `t_rss_news` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `catalog_id` int(11) NOT NULL COMMENT '分类ID',
  `source_url` varchar(200) NOT NULL COMMENT '来源地址',
  `title` varchar(200) NOT NULL COMMENT '标题',
  `pub_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '发布时间',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `img_src` varchar(400) DEFAULT NULL COMMENT '图片路径',
  `content` varchar(8000) DEFAULT NULL COMMENT '正文内容',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态：0 停用， 1 启用',
  `create_time` timestamp NOT NULL DEFAULT '2012-05-08 11:53:35' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '2012-05-08 11:53:35' COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_t_rss_news_source_url` (`source_url`),
  KEY `idx_t_rss_news_catalog_id` (`catalog_id`),
  KEY `idx_t_rss_news_pub_date` (`pub_date`) USING BTREE
);