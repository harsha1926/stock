CREATE TABLE `stock`.`customers` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `reference` VARCHAR(45) NULL,
  `address` VARCHAR(500) NULL,
  `phone` VARCHAR(15) NULL,
  `email` VARCHAR(45) NULL,
  `modified_by` VARCHAR(45) NOT NULL,
  `modified_on` DATETIME NOT NULL,
  PRIMARY KEY (`id`));

  CREATE TABLE `stock`.`suppliers` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `reference` VARCHAR(45) NULL,
  `address` VARCHAR(500) NOT NULL,
  `phone` VARCHAR(15) NOT NULL,
  `email` VARCHAR(45) NULL,
  `modified_by` VARCHAR(45) NOT NULL,
  `modified_on` DATETIME NOT NULL,
  PRIMARY KEY (`id`));

  CREATE TABLE `stock`.`products` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `category` VARCHAR(45) NULL,
  `modified_by` VARCHAR(45) NOT NULL,
  `modified_on` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE);

  CREATE TABLE `stock`.`financial_transactions` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `transaction_id` VARCHAR(45) NULL,
  `transaction_type` VARCHAR(45) NOT NULL,
  `amount` INT NOT NULL,
  `transaction_date` DATETIME NOT NULL,
  `bank_name` VARCHAR(45) NULL,
  `bank_ifsc_code` VARCHAR(45) NULL,
  `modified_by` VARCHAR(45) NOT NULL,
  `modified_on` DATETIME NOT NULL,
  `receipt_file` BLOB,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `transaction_id_UNIQUE` (`transaction_id` ASC) VISIBLE);

CREATE TABLE `stock`.`financial_transactions_details` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `financial_transaction_id` INT NOT NULL,
  `amount` INT NOT NULL,
  `modified_by` VARCHAR(45) NOT NULL,
  `modified_on` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `financial_transaction_id_idx` (`financial_transaction_id` ASC) VISIBLE,
  CONSTRAINT `financial_transaction_id`
    FOREIGN KEY (`financial_transaction_id`)
    REFERENCES `stock`.`financial_transactions` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `stock`.`suppliers_transactions` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `product_id` INT NOT NULL,
  `financial_transaction_details_id` INT NOT NULL,
  `supplier_id` INT NOT NULL,
  `quantity` INT NOT NULL,
  `discount_percentage` INT NOT NULL,
  `profit_percentage` INT NOT NULL,
  `modified_by` VARCHAR(45) NOT NULL,
  `modified_on` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `product_id_idx` (`product_id` ASC) VISIBLE,
  INDEX `supplier_id_idx` (`supplier_id` ASC) VISIBLE,
  INDEX `financial_transaction_details_id_idx` (`financial_transaction_details_id` ASC) VISIBLE,
  CONSTRAINT `product_id`
    FOREIGN KEY (`product_id`)
    REFERENCES `stock`.`products` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `supplier_id`
    FOREIGN KEY (`supplier_id`)
    REFERENCES `stock`.`suppliers` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `financial_transaction_details_id`
    FOREIGN KEY (`financial_transaction_details_id`)
    REFERENCES `stock`.`financial_transactions_details` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

    CREATE TABLE `stock`.`stock` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `suppliers_transactions_id` INT NOT NULL,
  `isSold` TINYINT NOT NULL DEFAULT 0,
  `modified_by` VARCHAR(45) NOT NULL,
  `modified_on` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `suppliers_transactions_id_idx` (`suppliers_transactions_id` ASC) VISIBLE,
  CONSTRAINT `suppliers_transactions_id`
    FOREIGN KEY (`suppliers_transactions_id`)
    REFERENCES `stock`.`suppliers_transactions` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

    CREATE TABLE `stock`.`suppliers_financial_transactions` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `supplier_transaction_id` INT NOT NULL,
  `financial_transaction_detail_id` INT NOT NULL,
  `modified_by` VARCHAR(45) NOT NULL,
  `modified_on` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `supplier_transaction_id_idx` (`supplier_transaction_id` ASC) VISIBLE,
  INDEX `financial_transaction_detail_id_idx` (`financial_transaction_detail_id` ASC) VISIBLE,
  CONSTRAINT `supplier_transaction_id`
    FOREIGN KEY (`supplier_transaction_id`)
    REFERENCES `stock`.`suppliers_transactions` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `financial_transaction_detail_id`
    FOREIGN KEY (`financial_transaction_detail_id`)
    REFERENCES `stock`.`financial_transactions_details` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


