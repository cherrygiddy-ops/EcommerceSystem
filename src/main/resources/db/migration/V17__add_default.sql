ALTER TABLE `store`.`cart`
CHANGE COLUMN `id` `id` BINARY(16) NOT NULL DEFAULT (uuid_to_bin(uuid())) ;