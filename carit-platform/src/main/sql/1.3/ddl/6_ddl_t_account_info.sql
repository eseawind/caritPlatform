ALTER TABLE `t_account_info`
ADD COLUMN `partner_id`  int NOT NULL DEFAULT 0 COMMENT '用户来源' AFTER `last_login_time`,
ADD INDEX `idx_t_account_info_partner_id` (`partner_id`) ;