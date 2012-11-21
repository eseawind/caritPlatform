CREATE TABLE `t_bluetooth` (
`device_id`  varchar(50) NOT NULL COMMENT '蓝牙设备ID' ,
`account_id`  int(11) NOT NULL COMMENT '账号Id' ,
`device_name`  varchar(50) NOT NULL ,
PRIMARY KEY (`device_id`, `account_id`)
) COMMENT='蓝牙设备关联表';