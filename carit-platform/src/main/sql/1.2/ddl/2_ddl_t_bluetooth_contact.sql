ALTER TABLE `t_bluetooth`
MODIFY COLUMN `device_id`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '车机设备ID' FIRST ,
CHANGE COLUMN `device_name` `bluetooth_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '蓝牙名称' AFTER `account_id`,
ADD COLUMN `bluetooth_id`  varchar(50) NOT NULL COMMENT '蓝牙设备ID' AFTER `account_id`,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`device_id`, `account_id`, `bluetooth_id`);