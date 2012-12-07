CREATE TABLE `t_partner` (
`id`  int NOT NULL AUTO_INCREMENT ,
`firm_name`  varchar(100) NOT NULL COMMENT '企业名称' ,
`city`  varchar(50) NOT NULL ,
`addr`  varchar(200) NOT NULL ,
`contact_person`  varchar(50) NOT NULL COMMENT '联系人' ,
`phone`  varchar(50) NOT NULL COMMENT '联系电话' ,
`email`  varchar(50) NOT NULL ,
`last_login_time`  timestamp NULL DEFAULT NULL COMMENT '最后登录时间',
`last_login_ip`  varchar(18) NULL DEFAULT NULL COMMENT '最后登录Ip',
`status`  int(11) NOT NULL DEFAULT 1 COMMENT '状态：0 停用， 1 启用' ,
`create_time`  timestamp NOT NULL DEFAULT '2012-05-08 11:53:35' COMMENT '创建时间' ,
`update_time`  timestamp NOT NULL DEFAULT '2012-05-08 11:53:35' COMMENT '更新时间' ,
PRIMARY KEY (`id`),
UNIQUE KEY `idx_t_partner_firm_name` (`firm_name`)
)COMMENT='合作企业表';