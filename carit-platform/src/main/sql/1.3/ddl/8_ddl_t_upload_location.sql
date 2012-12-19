
CREATE TABLE `t_upload_location_0` (
  `id` int(11) NOT NULL auto_increment,
  `device_id` varchar(32) NOT NULL COMMENT '设备Id',
  `account_id` int(11) NOT NULL COMMENT '账号Id',
  `lat` double NOT NULL COMMENT '经度',
  `lng` double NOT NULL COMMENT '经度',
  `create_time` timestamp NOT NULL default '2012-05-08 11:53:35' COMMENT '创建时间',
  `update_time` timestamp NOT NULL default '2012-05-08 11:53:35' COMMENT '更新时间',
  PRIMARY KEY  (`id`),
  KEY `fk_upload_location_account` (`account_id`),
  KEY `idx_t_upload_location_device_id_account_id` USING BTREE (`device_id`,`account_id`)
);

CREATE TABLE `t_upload_location_1` (
  `id` int(11) NOT NULL auto_increment,
  `device_id` varchar(32) NOT NULL COMMENT '设备Id',
  `account_id` int(11) NOT NULL COMMENT '账号Id',
  `lat` double NOT NULL COMMENT '经度',
  `lng` double NOT NULL COMMENT '经度',
  `create_time` timestamp NOT NULL default '2012-05-08 11:53:35' COMMENT '创建时间',
  `update_time` timestamp NOT NULL default '2012-05-08 11:53:35' COMMENT '更新时间',
  PRIMARY KEY  (`id`),
  KEY `fk_upload_location_account` (`account_id`),
  KEY `idx_t_upload_location_device_id_account_id` USING BTREE (`device_id`,`account_id`)
);

CREATE TABLE `t_upload_location_2` (
  `id` int(11) NOT NULL auto_increment,
  `device_id` varchar(32) NOT NULL COMMENT '设备Id',
  `account_id` int(11) NOT NULL COMMENT '账号Id',
  `lat` double NOT NULL COMMENT '经度',
  `lng` double NOT NULL COMMENT '经度',
  `create_time` timestamp NOT NULL default '2012-05-08 11:53:35' COMMENT '创建时间',
  `update_time` timestamp NOT NULL default '2012-05-08 11:53:35' COMMENT '更新时间',
  PRIMARY KEY  (`id`),
  KEY `fk_upload_location_account` (`account_id`),
  KEY `idx_t_upload_location_device_id_account_id` USING BTREE (`device_id`,`account_id`)
);

CREATE TABLE `t_upload_location_3` (
  `id` int(11) NOT NULL auto_increment,
  `device_id` varchar(32) NOT NULL COMMENT '设备Id',
  `account_id` int(11) NOT NULL COMMENT '账号Id',
  `lat` double NOT NULL COMMENT '经度',
  `lng` double NOT NULL COMMENT '经度',
  `create_time` timestamp NOT NULL default '2012-05-08 11:53:35' COMMENT '创建时间',
  `update_time` timestamp NOT NULL default '2012-05-08 11:53:35' COMMENT '更新时间',
  PRIMARY KEY  (`id`),
  KEY `fk_upload_location_account` (`account_id`),
  KEY `idx_t_upload_location_device_id_account_id` USING BTREE (`device_id`,`account_id`)
);

CREATE TABLE `t_upload_location_4` (
  `id` int(11) NOT NULL auto_increment,
  `device_id` varchar(32) NOT NULL COMMENT '设备Id',
  `account_id` int(11) NOT NULL COMMENT '账号Id',
  `lat` double NOT NULL COMMENT '经度',
  `lng` double NOT NULL COMMENT '经度',
  `create_time` timestamp NOT NULL default '2012-05-08 11:53:35' COMMENT '创建时间',
  `update_time` timestamp NOT NULL default '2012-05-08 11:53:35' COMMENT '更新时间',
  PRIMARY KEY  (`id`),
  KEY `fk_upload_location_account` (`account_id`),
  KEY `idx_t_upload_location_device_id_account_id` USING BTREE (`device_id`,`account_id`)
);

CREATE TABLE `t_upload_location_5` (
  `id` int(11) NOT NULL auto_increment,
  `device_id` varchar(32) NOT NULL COMMENT '设备Id',
  `account_id` int(11) NOT NULL COMMENT '账号Id',
  `lat` double NOT NULL COMMENT '经度',
  `lng` double NOT NULL COMMENT '经度',
  `create_time` timestamp NOT NULL default '2012-05-08 11:53:35' COMMENT '创建时间',
  `update_time` timestamp NOT NULL default '2012-05-08 11:53:35' COMMENT '更新时间',
  PRIMARY KEY  (`id`),
  KEY `fk_upload_location_account` (`account_id`),
  KEY `idx_t_upload_location_device_id_account_id` USING BTREE (`device_id`,`account_id`)
);

CREATE TABLE `t_upload_location_6` (
  `id` int(11) NOT NULL auto_increment,
  `device_id` varchar(32) NOT NULL COMMENT '设备Id',
  `account_id` int(11) NOT NULL COMMENT '账号Id',
  `lat` double NOT NULL COMMENT '经度',
  `lng` double NOT NULL COMMENT '经度',
  `create_time` timestamp NOT NULL default '2012-05-08 11:53:35' COMMENT '创建时间',
  `update_time` timestamp NOT NULL default '2012-05-08 11:53:35' COMMENT '更新时间',
  PRIMARY KEY  (`id`),
  KEY `fk_upload_location_account` (`account_id`),
  KEY `idx_t_upload_location_device_id_account_id` USING BTREE (`device_id`,`account_id`)
);

CREATE TABLE `t_upload_location_7` (
  `id` int(11) NOT NULL auto_increment,
  `device_id` varchar(32) NOT NULL COMMENT '设备Id',
  `account_id` int(11) NOT NULL COMMENT '账号Id',
  `lat` double NOT NULL COMMENT '经度',
  `lng` double NOT NULL COMMENT '经度',
  `create_time` timestamp NOT NULL default '2012-05-08 11:53:35' COMMENT '创建时间',
  `update_time` timestamp NOT NULL default '2012-05-08 11:53:35' COMMENT '更新时间',
  PRIMARY KEY  (`id`),
  KEY `fk_upload_location_account` (`account_id`),
  KEY `idx_t_upload_location_device_id_account_id` USING BTREE (`device_id`,`account_id`)
);

CREATE TABLE `t_upload_location_8` (
  `id` int(11) NOT NULL auto_increment,
  `device_id` varchar(32) NOT NULL COMMENT '设备Id',
  `account_id` int(11) NOT NULL COMMENT '账号Id',
  `lat` double NOT NULL COMMENT '经度',
  `lng` double NOT NULL COMMENT '经度',
  `create_time` timestamp NOT NULL default '2012-05-08 11:53:35' COMMENT '创建时间',
  `update_time` timestamp NOT NULL default '2012-05-08 11:53:35' COMMENT '更新时间',
  PRIMARY KEY  (`id`),
  KEY `fk_upload_location_account` (`account_id`),
  KEY `idx_t_upload_location_device_id_account_id` USING BTREE (`device_id`,`account_id`)
);

CREATE TABLE `t_upload_location_9` (
  `id` int(11) NOT NULL auto_increment,
  `device_id` varchar(32) NOT NULL COMMENT '设备Id',
  `account_id` int(11) NOT NULL COMMENT '账号Id',
  `lat` double NOT NULL COMMENT '经度',
  `lng` double NOT NULL COMMENT '经度',
  `create_time` timestamp NOT NULL default '2012-05-08 11:53:35' COMMENT '创建时间',
  `update_time` timestamp NOT NULL default '2012-05-08 11:53:35' COMMENT '更新时间',
  PRIMARY KEY  (`id`),
  KEY `fk_upload_location_account` (`account_id`),
  KEY `idx_t_upload_location_device_id_account_id` USING BTREE (`device_id`,`account_id`)
);