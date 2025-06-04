-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema maidai
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `maidai` DEFAULT CHARACTER SET utf8 ;
USE `maidai` ;

-- -----------------------------------------------------
-- Table `maidai`.`empresas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maidai`.`empresas` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(255) NOT NULL,
  `created_at` DATETIME(6) NOT NULL,
  `updated_at` DATETIME(6) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `nome_UNIQUE` (`nome` ASC) VISIBLE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `maidai`.`tipos_pessoa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maidai`.`tipos_pessoa` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `tipo` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `tipo_UNIQUE` (`tipo` ASC) VISIBLE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `maidai`.`pessoas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maidai`.`pessoas` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(255) NOT NULL,
  `telefone` VARCHAR(255) NULL,
  `email` VARCHAR(255) NULL,
  `created_at` DATETIME(6) NOT NULL,
  `updated_at` DATETIME(6) NULL,
  `tipos_pessoa_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_pessoas_tipos_pessoa1_idx` (`tipos_pessoa_id` ASC) VISIBLE,
  CONSTRAINT `fk_pessoas_tipos_pessoa1`
    FOREIGN KEY (`tipos_pessoa_id`)
    REFERENCES `maidai`.`tipos_pessoa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `maidai`.`ppg`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maidai`.`ppg` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(255) NOT NULL,
  `created_at` DATETIME(6) NOT NULL,
  `updated_at` DATETIME(6) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `nome_UNIQUE` (`nome` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `maidai`.`orientadores`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maidai`.`orientadores` (
  `pessoas_id` INT UNSIGNED NOT NULL,
  `ppg_id` INT UNSIGNED NULL,
  PRIMARY KEY (`pessoas_id`),
  INDEX `fk_orientadores_ppg1_idx` (`ppg_id` ASC) VISIBLE,
  CONSTRAINT `fk_orientadores_pessoas1`
    FOREIGN KEY (`pessoas_id`)
    REFERENCES `maidai`.`pessoas` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_orientadores_ppg1`
    FOREIGN KEY (`ppg_id`)
    REFERENCES `maidai`.`ppg` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `maidai`.`projetos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maidai`.`projetos` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(255) NOT NULL,
  `inicio` DATE NOT NULL,
  `fim` DATE NOT NULL,
  `created_at` DATETIME(6) NOT NULL,
  `updated_at` DATETIME(6) NULL,
  `empresas_id` INT UNSIGNED NOT NULL,
  `orientador` INT UNSIGNED NOT NULL,
  `coorientador` INT UNSIGNED NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_projetos_empresas1_idx` (`empresas_id` ASC) VISIBLE,
  UNIQUE INDEX `nome_UNIQUE` (`nome` ASC) VISIBLE,
  INDEX `fk_projetos_orientadores1_idx` (`orientador` ASC) VISIBLE,
  INDEX `fk_projetos_orientadores2_idx` (`coorientador` ASC) VISIBLE,
  CONSTRAINT `fk_projetos_empresas1`
    FOREIGN KEY (`empresas_id`)
    REFERENCES `maidai`.`empresas` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_projetos_orientadores1`
    FOREIGN KEY (`orientador`)
    REFERENCES `maidai`.`orientadores` (`pessoas_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_projetos_orientadores2`
    FOREIGN KEY (`coorientador`)
    REFERENCES `maidai`.`orientadores` (`pessoas_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- First, add the new pessoas_id column if it doesn't exist
ALTER TABLE `maidai`.`projetos`
ADD COLUMN `pessoas_id` INT UNSIGNED NULL AFTER `empresas_id`;

-- Copy data from orientador to pessoas_id (they reference the same people)
-- Disable safe update mode for this session
SET SQL_SAFE_UPDATES = 0;

-- Run your update statement
UPDATE `maidai`.`projetos` SET `pessoas_id` = `orientador` WHERE id IS NOT NULL;

-- Re-enable safe update mode (recommended for safety)
SET SQL_SAFE_UPDATES = 1;


-- Make the column NOT NULL if all rows have values
ALTER TABLE `maidai`.`projetos`
MODIFY COLUMN `pessoas_id` INT UNSIGNED NOT NULL;

-- Add the foreign key constraint
ALTER TABLE `maidai`.`projetos`
ADD CONSTRAINT `fk_projetos_pessoas1`
  FOREIGN KEY (`pessoas_id`)
  REFERENCES `maidai`.`pessoas` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

  -- Modify the columns to allow NULL
  ALTER TABLE `maidai`.`projetos`
  MODIFY COLUMN `inicio` DATE NULL,
  MODIFY COLUMN `fim` DATE NULL;

  -- Add the new column
  ALTER TABLE `maidai`.`projetos`
  ADD COLUMN `ano_edital` YEAR NULL AFTER `fim`;

-- -----------------------------------------------------
-- Table `maidai`.`coorientadores`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maidai`.`coorientadores` (
  `pessoas_id` INT UNSIGNED NOT NULL,
  `projetos_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`pessoas_id`, `projetos_id`),
  INDEX `fk_pessoas_has_projetos_projetos1_idx` (`projetos_id` ASC) VISIBLE,
  INDEX `fk_pessoas_has_projetos_pessoas1_idx` (`pessoas_id` ASC) VISIBLE,
  CONSTRAINT `fk_pessoas_has_projetos_pessoas1`
    FOREIGN KEY (`pessoas_id`)
    REFERENCES `maidai`.`pessoas` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_pessoas_has_projetos_projetos1`
    FOREIGN KEY (`projetos_id`)
    REFERENCES `maidai`.`projetos` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- Migrate existing coorientador relationships
INSERT IGNORE INTO `maidai`.`coorientadores` (`pessoas_id`, `projetos_id`)
SELECT `coorientador`, `id`
FROM `maidai`.`projetos`
WHERE `coorientador` IS NOT NULL;

---- If you need to keep the old structure temporarily during transition, you can rename columns instead of dropping them:
/*
ALTER TABLE `maidai`.`projetos`
CHANGE COLUMN `orientador` `deprecated_orientador` INT UNSIGNED NULL,
CHANGE COLUMN `coorientador` `deprecated_coorientador` INT UNSIGNED NULL;
*/

---- Remove the old foreign key constraints first
ALTER TABLE `maidai`.`projetos`
DROP FOREIGN KEY `fk_projetos_orientadores1`,
DROP FOREIGN KEY `fk_projetos_orientadores2`;

-- Then drop the columns
ALTER TABLE `maidai`.`projetos`
DROP COLUMN `orientador`,
DROP COLUMN `coorientador`;

-- -----------------------------------------------------
-- Table `maidai`.`tipos_bolsa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maidai`.`tipos_bolsa` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `tipo` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `tipo_UNIQUE` (`tipo` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `maidai`.`bolsas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maidai`.`bolsas` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `inicio` DATE NULL,
  `fim` DATE NULL,
  `created_at` DATETIME(6) NOT NULL,
  `updated_at` DATETIME(6) NULL,
  `projetos_id` INT UNSIGNED NOT NULL,
  `tipos_bolsa_id` INT UNSIGNED NOT NULL,
  `pessoas_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_bolsas_projetos1_idx` (`projetos_id` ASC) VISIBLE,
  INDEX `fk_bolsas_tipos_bolsa1_idx` (`tipos_bolsa_id` ASC) VISIBLE,
  INDEX `fk_bolsas_pessoas1_idx` (`pessoas_id` ASC) VISIBLE,
  UNIQUE INDEX `pessoas_id_UNIQUE` (`pessoas_id` ASC) VISIBLE,
  CONSTRAINT `fk_bolsas_projetos1`
    FOREIGN KEY (`projetos_id`)
    REFERENCES `maidai`.`projetos` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_bolsas_tipos_bolsa1`
    FOREIGN KEY (`tipos_bolsa_id`)
    REFERENCES `maidai`.`tipos_bolsa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_bolsas_pessoas1`
    FOREIGN KEY (`pessoas_id`)
    REFERENCES `maidai`.`pessoas` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `maidai`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maidai`.`users` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `is_active` BIT(1) NOT NULL,
  `created_at` DATETIME(6) NOT NULL,
  `updated_at` DATETIME(6) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `maidai`.`roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maidai`.`roles` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `maidai`.`users_roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maidai`.`users_roles` (
  `users_id` INT UNSIGNED NOT NULL,
  `roles_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`users_id`, `roles_id`),
  INDEX `fk_users_has_roles_roles1_idx` (`roles_id` ASC) VISIBLE,
  INDEX `fk_users_has_roles_users1_idx` (`users_id` ASC) VISIBLE,
  CONSTRAINT `fk_users_has_roles_users1`
    FOREIGN KEY (`users_id`)
    REFERENCES `maidai`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_has_roles_roles1`
    FOREIGN KEY (`roles_id`)
    REFERENCES `maidai`.`roles` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `maidai`.`pessoas_ppg`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maidai`.`pessoas_ppg` (
  `pessoas_id` INT UNSIGNED NOT NULL,
  `ppg_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`pessoas_id`, `ppg_id`),
  INDEX `fk_pessoas_has_ppg_ppg1_idx` (`ppg_id` ASC) VISIBLE,
  INDEX `fk_pessoas_has_ppg_pessoas1_idx` (`pessoas_id` ASC) VISIBLE,
  CONSTRAINT `fk_pessoas_has_ppg_pessoas1`
    FOREIGN KEY (`pessoas_id`)
    REFERENCES `maidai`.`pessoas` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_pessoas_has_ppg_ppg1`
    FOREIGN KEY (`ppg_id`)
    REFERENCES `maidai`.`ppg` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

ALTER TABLE `maidai`.`pessoas_ppg`
ADD CONSTRAINT `uq_pessoa_ppg` UNIQUE (`pessoas_id`, `ppg_id`);

-- Disable safe updates if needed
SET SQL_SAFE_UPDATES = 0;

-- Migrate all records where ppg_id is not null
INSERT INTO `maidai`.`pessoas_ppg` (`pessoas_id`, `ppg_id`)
SELECT `pessoas_id`, `ppg_id`
FROM `maidai`.`orientadores`
WHERE `ppg_id` IS NOT NULL
ON DUPLICATE KEY UPDATE `ppg_id` = VALUES(`ppg_id`);


-- Then drop the old table
DROP TABLE IF EXISTS `maidai`.`orientadores`;


-- Re-enable safe updates
SET SQL_SAFE_UPDATES = 1;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

/*
INSERT INTO `roles` VALUES (1,'administrador'),(2,'colaborador');

INSERT INTO `tipos_pessoa` VALUES (2,'bolsista'),(1,'orientador');

INSERT INTO `users` VALUES (1,'admin','$2a$12$Ft5sNY69V4hCkxZbfqz6ZOHCVFMbDrNtz1CT3SezCOnoP9Cqd5lGm','admin',_binary '','2025-03-21 14:01:45.785000','2025-03-27 16:26:40.895000'),(2,'colaborator','$2a$12$MuBN9tvDJfAsvQaebCEjfeYvplptlPriPthD25r4j.7i8LV9xGrj6','colaborator',_binary '','2025-03-27 15:33:21.285000','2025-03-27 17:28:05.988000');

INSERT INTO `users_roles` VALUES (1,1),(2,2);
*/