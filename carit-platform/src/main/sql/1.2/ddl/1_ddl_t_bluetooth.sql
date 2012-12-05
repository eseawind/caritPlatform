ALTER TABLE `t_bluetooth_contact`
MODIFY COLUMN `device_id`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '车机设备ID' AFTER `id`,
ADD COLUMN `bluetooth_id`  varchar(50) NOT NULL COMMENT '蓝牙设备ID' AFTER `device_id`;