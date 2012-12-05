CREATE TABLE `t_obd_data_value` (
`id`  int NOT NULL AUTO_INCREMENT ,
`data_id`  int NOT NULL ,
`index`  int NOT NULL COMMENT '数值顺位' ,
`value`  int NOT NULL COMMENT '数值' ,
PRIMARY KEY (`id`)
);
ALTER TABLE `t_obd_data_value`
ADD INDEX `idx_t_obd_data_value_data_id` (`data_id`) ,
ADD INDEX `idx_t_obd_data_value_index` (`index`) ;