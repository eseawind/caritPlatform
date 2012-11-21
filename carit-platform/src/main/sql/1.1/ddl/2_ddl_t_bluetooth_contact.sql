CREATE TABLE `t_bluetooth_contact` (
`id` int(11) NOT NULL auto_increment COMMENT '代理主键' ,
`device_id`  varchar(50) NOT NULL COMMENT '蓝牙设备ID' ,
`account_id`  int NOT NULL COMMENT '账号ID' ,
`call_name`  varchar(50) NOT NULL COMMENT '姓名' ,
`call_num`  varchar(50) NOT NULL COMMENT '电话号码' ,
`call_name_key`  varchar(10) NOT NULL COMMENT '姓名拼音索引' ,
`call_type`  varchar(10) NULL COMMENT '号码类型' ,
`status` int NOT NULL COMMENT '状态：0 停用， 1 启用',
`create_time` timestamp NOT NULL default '2012-05-08 11:53:35',
`update_time` timestamp NOT NULL default '2012-05-08 11:53:35',
PRIMARY KEY (`id`)
) COMMENT='蓝牙电话簿';