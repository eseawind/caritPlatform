TRUNCATE TABLE t_upload_location;

ALTER TABLE `t_upload_location`
ADD COLUMN `account_id`  int NOT NULL COMMENT '账号Id' AFTER `device_id`;

ALTER TABLE `t_upload_location`
ADD INDEX `idx_upload_location_device_id_account_id` (`device_id`, `account_id`) ;

ALTER TABLE `t_upload_location` ADD CONSTRAINT fk_upload_location_account_id FOREIGN KEY (account_id) REFERENCES t_account_info(id);