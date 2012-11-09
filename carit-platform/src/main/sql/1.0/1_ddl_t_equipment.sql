ALTER TABLE `t_equipment` DROP COLUMN `account_id`;

CREATE TABLE `t_account_equipment` (
	`account_id`  int NOT NULL COMMENT '账号ID' ,
	`device_id`  varchar(50) NOT NULL COMMENT '设备ID' ,
	PRIMARY KEY (`device_id`, `account_id`)
) COMMENT='账号设备关联表';
