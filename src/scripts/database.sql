-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema maidai
-- -----------------------------------------------------

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


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
