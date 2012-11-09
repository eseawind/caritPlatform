TRUNCATE TABLE t_obd_data;

ALTER TABLE `t_obd_data`
ADD COLUMN `account_id`  int NOT NULL COMMENT '账号Id' AFTER `device_id`;

ALTER TABLE `t_obd_data`
ADD INDEX `idx_odb_data_device_id_account_id` (`device_id`, `account_id`) ;


ALTER TABLE `t_obd_data`
        ADD CONSTRAINT fk_obd_data_account_id
        FOREIGN KEY (account_id)
        REFERENCES t_account_info(id);