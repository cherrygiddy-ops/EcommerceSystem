CREATE TABLE `store`.`orders` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `customer_id` BIGINT NULL,
  `status` VARCHAR(45) NULL,
  `created_at` DATETIME NULL,
  `total_price` DECIMAL(10,2) NULL,
  PRIMARY KEY (`id`));