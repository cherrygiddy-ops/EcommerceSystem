CREATE TABLE IF NOT EXISTS `store`.`cart` (
  `id` VARCHAR(36) NOT NULL ,
  `dateCreated` DATE NOT NULL,
  PRIMARY KEY (`id`))
    ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `store`.`cartItem` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `cartId` VARCHAR(36) NULL,
  `product_id` BIGINT NULL,
  `quantity` INT NULL,
   INDEX `fk_cartItem_product_idx` (`id` ASC) VISIBLE,
   PRIMARY KEY (`id`),
   CONSTRAINT `fk_cartItem_product`
     FOREIGN KEY (`product_id`)
     REFERENCES `store`.`products` (`id`)
     ON DELETE CASCADE
      ON UPDATE CASCADE)
      ENGINE = InnoDB;

