ALTER TABLE `store`.`cartitem`
ADD INDEX `fk_cartItem_cart_idx` (`cartId` ASC) VISIBLE;
;
ALTER TABLE `store`.`cartitem`
ADD CONSTRAINT `fk_cartItem_cart`
  FOREIGN KEY (`cartId`)
  REFERENCES `store`.`cart` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;
