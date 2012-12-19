CREATE TABLE `t_partner_equipment` (
`id`  int NOT NULL AUTO_INCREMENT ,
`partner_id`  int NOT NULL ,
`device_id`  varchar(50) NULL ,
PRIMARY KEY (`id`),
INDEX `idx_t_partner_equipment_partner_id` (`partner_id`) 
);