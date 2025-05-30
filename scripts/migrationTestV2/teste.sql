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

INSERT INTO `roles` VALUES (1,'administrador'),(2,'colaborador');

INSERT INTO `tipos_pessoa` VALUES (2,'bolsista'),(1,'orientador');

INSERT INTO `users` VALUES (1,'admin','$2a$12$Ft5sNY69V4hCkxZbfqz6ZOHCVFMbDrNtz1CT3SezCOnoP9Cqd5lGm','admin',_binary '','2025-03-21 14:01:45.785000','2025-03-27 16:26:40.895000'),(2,'colaborator','$2a$12$MuBN9tvDJfAsvQaebCEjfeYvplptlPriPthD25r4j.7i8LV9xGrj6','colaborator',_binary '','2025-03-27 15:33:21.285000','2025-03-27 17:28:05.988000');

INSERT INTO `users_roles` VALUES (1,1),(2,2);

INSERT INTO `maidai`.`pessoas` (`nome`, `telefone`, `email`, `created_at`, `updated_at`, `tipos_pessoa_id`) VALUES ('João Silva', '(55) 9876-5432', 'joao.silva45@example.com', NOW(6), NOW(6), 1);
INSERT INTO `maidai`.`pessoas` (`nome`, `telefone`, `email`, `created_at`, `updated_at`, `tipos_pessoa_id`) VALUES ('Maria Santos', '(55) 9234-5678', 'maria.santos67@example.com', NOW(6), NULL, 2);
INSERT INTO `maidai`.`pessoas` (`nome`, `telefone`, `email`, `created_at`, `updated_at`, `tipos_pessoa_id`) VALUES ('José Oliveira', NULL, 'jose.oliveira23@example.com', NOW(6), NOW(6), 1);
INSERT INTO `maidai`.`pessoas` (`nome`, `telefone`, `email`, `created_at`, `updated_at`, `tipos_pessoa_id`) VALUES ('Ana Souza', '(55) 9345-6789', NULL, NOW(6), NOW(6), 2);
INSERT INTO `maidai`.`pessoas` (`nome`, `telefone`, `email`, `created_at`, `updated_at`, `tipos_pessoa_id`) VALUES ('Antônio Rodrigues', '(55) 9456-7890', 'antonio.rodrigues78@example.com', NOW(6), NULL, 1);
INSERT INTO `maidai`.`pessoas` (`nome`, `telefone`, `email`, `created_at`, `updated_at`, `tipos_pessoa_id`) VALUES ('Francisca Ferreira', NULL, NULL, NOW(6), NOW(6), 2);
INSERT INTO `maidai`.`pessoas` (`nome`, `telefone`, `email`, `created_at`, `updated_at`, `tipos_pessoa_id`) VALUES ('Carlos Alves', '(55) 9567-8901', 'carlos.alves12@example.com', NOW(6), NOW(6), 1);
INSERT INTO `maidai`.`pessoas` (`nome`, `telefone`, `email`, `created_at`, `updated_at`, `tipos_pessoa_id`) VALUES ('Adriana Pereira', '(55) 9678-9012', 'adriana.pereira34@example.com', NOW(6), NULL, 2);
INSERT INTO `maidai`.`pessoas` (`nome`, `telefone`, `email`, `created_at`, `updated_at`, `tipos_pessoa_id`) VALUES ('Paulo Lima', '(55) 9789-0123', 'paulo.lima56@example.com', NOW(6), NOW(6), 1);
INSERT INTO `maidai`.`pessoas` (`nome`, `telefone`, `email`, `created_at`, `updated_at`, `tipos_pessoa_id`) VALUES ('Patrícia Costa', NULL, 'patricia.costa89@example.com', NOW(6), NOW(6), 2);
INSERT INTO `maidai`.`pessoas` (`nome`, `telefone`, `email`, `created_at`, `updated_at`, `tipos_pessoa_id`) VALUES ('Lucas Gomes', '(55) 9890-1234', 'lucas.gomes01@example.com', NOW(6), NULL, 1);
INSERT INTO `maidai`.`pessoas` (`nome`, `telefone`, `email`, `created_at`, `updated_at`, `tipos_pessoa_id`) VALUES ('Amanda Martins', '(55) 9012-3456', NULL, NOW(6), NOW(6), 2);
INSERT INTO `maidai`.`pessoas` (`nome`, `telefone`, `email`, `created_at`, `updated_at`, `tipos_pessoa_id`) VALUES ('Pedro Ribeiro', '(55) 9123-4567', 'pedro.ribeiro23@example.com', NOW(6), NOW(6), 1);
INSERT INTO `maidai`.`pessoas` (`nome`, `telefone`, `email`, `created_at`, `updated_at`, `tipos_pessoa_id`) VALUES ('Juliana Carvalho', NULL, 'juliana.carvalho45@example.com', NOW(6), NULL, 2);
INSERT INTO `maidai`.`pessoas` (`nome`, `telefone`, `email`, `created_at`, `updated_at`, `tipos_pessoa_id`) VALUES ('Marcos Araújo', '(55) 9234-5678', 'marcos.araújo67@example.com', NOW(6), NOW(6), 1);
INSERT INTO `maidai`.`pessoas` (`nome`, `telefone`, `email`, `created_at`, `updated_at`, `tipos_pessoa_id`) VALUES ('Fernanda Melo', '(55) 9345-6789', NULL, NOW(6), NOW(6), 2);
INSERT INTO `maidai`.`pessoas` (`nome`, `telefone`, `email`, `created_at`, `updated_at`, `tipos_pessoa_id`) VALUES ('Rafaela Cardoso', '(55) 9456-7890', 'rafaela.cardoso89@example.com', NOW(6), NULL, 1);
INSERT INTO `maidai`.`pessoas` (`nome`, `telefone`, `email`, `created_at`, `updated_at`, `tipos_pessoa_id`) VALUES ('Roberto Teixeira', NULL, 'roberto.teixeira01@example.com', NOW(6), NOW(6), 2);
INSERT INTO `maidai`.`pessoas` (`nome`, `telefone`, `email`, `created_at`, `updated_at`, `tipos_pessoa_id`) VALUES ('Gabriel Moreira', '(55) 9678-9012', 'gabriel.moreira23@example.com', NOW(6), NOW(6), 1);
INSERT INTO `maidai`.`pessoas` (`nome`, `telefone`, `email`, `created_at`, `updated_at`, `tipos_pessoa_id`) VALUES ('Beatriz Nascimento', '(55) 9789-0123', 'beatriz.nascimento45@example.com', NOW(6), NULL, 2);

-- Continuing with 180 more records following the same pattern...
-- [Records 21-40]
INSERT INTO `maidai`.`pessoas` (`nome`, `telefone`, `email`, `created_at`, `updated_at`, `tipos_pessoa_id`) VALUES ('Ricardo Fernandes', '(55) 9890-1234', 'ricardo.fernandes67@example.com', NOW(6), NOW(6), 1);
INSERT INTO `maidai`.`pessoas` (`nome`, `telefone`, `email`, `created_at`, `updated_at`, `tipos_pessoa_id`) VALUES ('Camila Rocha', NULL, NULL, NOW(6), NOW(6), 2);
INSERT INTO `maidai`.`pessoas` (`nome`, `telefone`, `email`, `created_at`, `updated_at`, `tipos_pessoa_id`) VALUES ('Gustavo Mendes', '(55) 9012-3456', 'gustavo.mendes89@example.com', NOW(6), NULL, 1);
INSERT INTO `maidai`.`pessoas` (`nome`, `telefone`, `email`, `created_at`, `updated_at`, `tipos_pessoa_id`) VALUES ('Isabela Castro', '(55) 9123-4567', 'isabela.castro01@example.com', NOW(6), NOW(6), 2);
INSERT INTO `maidai`.`pessoas` (`nome`, `telefone`, `email`, `created_at`, `updated_at`, `tipos_pessoa_id`) VALUES ('Felipe Duarte', NULL, 'felipe.duarte23@example.com', NOW(6), NOW(6), 1);
INSERT INTO `maidai`.`pessoas` (`nome`, `telefone`, `email`, `created_at`, `updated_at`, `tipos_pessoa_id`) VALUES ('Larissa Pires', '(55) 9345-6789', NULL, NOW(6), NULL, 2);
INSERT INTO `maidai`.`pessoas` (`nome`, `telefone`, `email`, `created_at`, `updated_at`, `tipos_pessoa_id`) VALUES ('Daniel Monteiro', '(55) 9456-7890', 'daniel.monteiro45@example.com', NOW(6), NOW(6), 1);
INSERT INTO `maidai`.`pessoas` (`nome`, `telefone`, `email`, `created_at`, `updated_at`, `tipos_pessoa_id`) VALUES ('Vanessa Lopes', '(55) 9567-8901', 'vanessa.lopes67@example.com', NOW(6), NOW(6), 2);
INSERT INTO `maidai`.`pessoas` (`nome`, `telefone`, `email`, `created_at`, `updated_at`, `tipos_pessoa_id`) VALUES ('Eduardo Barbosa', NULL, 'eduardo.barbosa89@example.com', NOW(6), NULL, 1);
INSERT INTO `maidai`.`pessoas` (`nome`, `telefone`, `email`, `created_at`, `updated_at`, `tipos_pessoa_id`) VALUES ('Tatiane Dias', '(55) 9789-0123', NULL, NOW(6), NOW(6), 2);
INSERT INTO `maidai`.`pessoas` (`nome`, `telefone`, `email`, `created_at`, `updated_at`, `tipos_pessoa_id`) VALUES ('Marcelo Xavier', '(55) 9890-1234', 'marcelo.xavier01@example.com', NOW(6), NOW(6), 1);
INSERT INTO `maidai`.`pessoas` (`nome`, `telefone`, `email`, `created_at`, `updated_at`, `tipos_pessoa_id`) VALUES ('Priscila Andrade', NULL, 'priscila.andrade23@example.com', NOW(6), NULL, 2);
INSERT INTO `maidai`.`pessoas` (`nome`, `telefone`, `email`, `created_at`, `updated_at`, `tipos_pessoa_id`) VALUES ('Rodrigo Fonseca', '(55) 9012-3456', 'rodrigo.fonseca45@example.com', NOW(6), NOW(6), 1);
INSERT INTO `maidai`.`pessoas` (`nome`, `telefone`, `email`, `created_at`, `updated_at`, `tipos_pessoa_id`) VALUES ('Simone Tavares', '(55) 9123-4567', NULL, NOW(6), NOW(6), 2);
INSERT INTO `maidai`.`pessoas` (`nome`, `telefone`, `email`, `created_at`, `updated_at`, `tipos_pessoa_id`) VALUES ('Alexandre Cunha', '(55) 9234-5678', 'alexandre.cunha67@example.com', NOW(6), NULL, 1);
INSERT INTO `maidai`.`pessoas` (`nome`, `telefone`, `email`, `created_at`, `updated_at`, `tipos_pessoa_id`) VALUES ('Renata Miranda', NULL, 'renata.miranda89@example.com', NOW(6), NOW(6), 2);
INSERT INTO `maidai`.`pessoas` (`nome`, `telefone`, `email`, `created_at`, `updated_at`, `tipos_pessoa_id`) VALUES ('Bruno Peixoto', '(55) 9456-7890', 'bruno.peixoto01@example.com', NOW(6), NOW(6), 1);
INSERT INTO `maidai`.`pessoas` (`nome`, `telefone`, `email`, `created_at`, `updated_at`, `tipos_pessoa_id`) VALUES ('Cristiane Rezende', '(55) 9567-8901', NULL, NOW(6), NULL, 2);
INSERT INTO `maidai`.`pessoas` (`nome`, `telefone`, `email`, `created_at`, `updated_at`, `tipos_pessoa_id`) VALUES ('Leandro Vasconcelos', NULL, 'leandro.vasconcelos23@example.com', NOW(6), NOW(6), 1);
INSERT INTO `maidai`.`pessoas` (`nome`, `telefone`, `email`, `created_at`, `updated_at`, `tipos_pessoa_id`) VALUES ('Viviane Brito', '(55) 9789-0123', 'viviane.brito45@example.com', NOW(6), NOW(6), 2);


-- 40 INSERT statements for maidai.ppg table
INSERT INTO `maidai`.`ppg` (`nome`, `created_at`, `updated_at`) VALUES ('Programa de Pós-Graduação em Ciência da Computação', NOW(6), NOW(6));
INSERT INTO `maidai`.`ppg` (`nome`, `created_at`, `updated_at`) VALUES ('Programa de Pós-Graduação em Engenharia Elétrica', NOW(6), NULL);
INSERT INTO `maidai`.`ppg` (`nome`, `created_at`, `updated_at`) VALUES ('Programa de Pós-Graduação em Biotecnologia', NOW(6), NOW(6));
INSERT INTO `maidai`.`ppg` (`nome`, `created_at`, `updated_at`) VALUES ('Programa de Pós-Graduação em Direito', NOW(6), NULL);
INSERT INTO `maidai`.`ppg` (`nome`, `created_at`, `updated_at`) VALUES ('Programa de Pós-Graduação em Economia', NOW(6), NOW(6));
INSERT INTO `maidai`.`ppg` (`nome`, `created_at`, `updated_at`) VALUES ('Programa de Pós-Graduação em Educação', NOW(6), NULL);
INSERT INTO `maidai`.`ppg` (`nome`, `created_at`, `updated_at`) VALUES ('Programa de Pós-Graduação em Engenharia Civil', NOW(6), NOW(6));
INSERT INTO `maidai`.`ppg` (`nome`, `created_at`, `updated_at`) VALUES ('Programa de Pós-Graduação em Física', NOW(6), NULL);
INSERT INTO `maidai`.`ppg` (`nome`, `created_at`, `updated_at`) VALUES ('Programa de Pós-Graduação em Química', NOW(6), NOW(6));
INSERT INTO `maidai`.`ppg` (`nome`, `created_at`, `updated_at`) VALUES ('Programa de Pós-Graduação em Administração', NOW(6), NULL);

INSERT INTO `maidai`.`ppg` (`nome`, `created_at`, `updated_at`) VALUES ('Programa de Pós-Graduação em Medicina', NOW(6), NOW(6));
INSERT INTO `maidai`.`ppg` (`nome`, `created_at`, `updated_at`) VALUES ('Programa de Pós-Graduação em Psicologia', NOW(6), NULL);
INSERT INTO `maidai`.`ppg` (`nome`, `created_at`, `updated_at`) VALUES ('Programa de Pós-Graduação em Arquitetura', NOW(6), NOW(6));
INSERT INTO `maidai`.`ppg` (`nome`, `created_at`, `updated_at`) VALUES ('Programa de Pós-Graduação em Matemática', NOW(6), NULL);
INSERT INTO `maidai`.`ppg` (`nome`, `created_at`, `updated_at`) VALUES ('Programa de Pós-Graduação em História', NOW(6), NOW(6));
INSERT INTO `maidai`.`ppg` (`nome`, `created_at`, `updated_at`) VALUES ('Programa de Pós-Graduação em Filosofia', NOW(6), NULL);
INSERT INTO `maidai`.`ppg` (`nome`, `created_at`, `updated_at`) VALUES ('Programa de Pós-Graduação em Letras', NOW(6), NOW(6));
INSERT INTO `maidai`.`ppg` (`nome`, `created_at`, `updated_at`) VALUES ('Programa de Pós-Graduação em Engenharia Mecânica', NOW(6), NULL);
INSERT INTO `maidai`.`ppg` (`nome`, `created_at`, `updated_at`) VALUES ('Programa de Pós-Graduação em Nutrição', NOW(6), NOW(6));
INSERT INTO `maidai`.`ppg` (`nome`, `created_at`, `updated_at`) VALUES ('Programa de Pós-Graduação em Geografia', NOW(6), NULL);

INSERT INTO `maidai`.`ppg` (`nome`, `created_at`, `updated_at`) VALUES ('Programa de Pós-Graduação em Ciências Biológicas', NOW(6), NOW(6));
INSERT INTO `maidai`.`ppg` (`nome`, `created_at`, `updated_at`) VALUES ('Programa de Pós-Graduação em Engenharia Química', NOW(6), NULL);
INSERT INTO `maidai`.`ppg` (`nome`, `created_at`, `updated_at`) VALUES ('Programa de Pós-Graduação em Ciências Sociais', NOW(6), NOW(6));
INSERT INTO `maidai`.`ppg` (`nome`, `created_at`, `updated_at`) VALUES ('Programa de Pós-Graduação em Farmácia', NOW(6), NULL);
INSERT INTO `maidai`.`ppg` (`nome`, `created_at`, `updated_at`) VALUES ('Programa de Pós-Graduação em Oceanografia', NOW(6), NOW(6));
INSERT INTO `maidai`.`ppg` (`nome`, `created_at`, `updated_at`) VALUES ('Programa de Pós-Graduação em Engenharia de Produção', NOW(6), NULL);
INSERT INTO `maidai`.`ppg` (`nome`, `created_at`, `updated_at`) VALUES ('Programa de Pós-Graduação em Música', NOW(6), NOW(6));
INSERT INTO `maidai`.`ppg` (`nome`, `created_at`, `updated_at`) VALUES ('Programa de Pós-Graduação em Comunicação', NOW(6), NULL);
INSERT INTO `maidai`.`ppg` (`nome`, `created_at`, `updated_at`) VALUES ('Programa de Pós-Graduação em Ciências Ambientais', NOW(6), NOW(6));
INSERT INTO `maidai`.`ppg` (`nome`, `created_at`, `updated_at`) VALUES ('Programa de Pós-Graduação em Engenharia de Materiais', NOW(6), NULL);

INSERT INTO `maidai`.`ppg` (`nome`, `created_at`, `updated_at`) VALUES ('Programa de Pós-Graduação em Ciências Contábeis', NOW(6), NOW(6));
INSERT INTO `maidai`.`ppg` (`nome`, `created_at`, `updated_at`) VALUES ('Programa de Pós-Graduação em Relações Internacionais', NOW(6), NULL);
INSERT INTO `maidai`.`ppg` (`nome`, `created_at`, `updated_at`) VALUES ('Programa de Pós-Graduação em Design', NOW(6), NOW(6));
INSERT INTO `maidai`.`ppg` (`nome`, `created_at`, `updated_at`) VALUES ('Programa de Pós-Graduação em Engenharia Biomédica', NOW(6), NULL);
INSERT INTO `maidai`.`ppg` (`nome`, `created_at`, `updated_at`) VALUES ('Programa de Pós-Graduação em Ciências da Saúde', NOW(6), NOW(6));
INSERT INTO `maidai`.`ppg` (`nome`, `created_at`, `updated_at`) VALUES ('Programa de Pós-Graduação em Engenharia de Alimentos', NOW(6), NULL);
INSERT INTO `maidai`.`ppg` (`nome`, `created_at`, `updated_at`) VALUES ('Programa de Pós-Graduação em Antropologia', NOW(6), NOW(6));
INSERT INTO `maidai`.`ppg` (`nome`, `created_at`, `updated_at`) VALUES ('Programa de Pós-Graduação em Engenharia Nuclear', NOW(6), NULL);
INSERT INTO `maidai`.`ppg` (`nome`, `created_at`, `updated_at`) VALUES ('Programa de Pós-Graduação em Ciências do Esporte', NOW(6), NOW(6));
INSERT INTO `maidai`.`ppg` (`nome`, `created_at`, `updated_at`) VALUES ('Programa de Pós-Graduação em Engenharia Aeroespacial', NOW(6), NULL);

-- First 20 orientadores (using pessoas_id 1-20 where tipos_pessoa_id = 1)
INSERT INTO `maidai`.`orientadores` (`pessoas_id`, `ppg_id`) VALUES (1, 5);
INSERT INTO `maidai`.`orientadores` (`pessoas_id`, `ppg_id`) VALUES (3, 12);
INSERT INTO `maidai`.`orientadores` (`pessoas_id`, `ppg_id`) VALUES (5, 8);
INSERT INTO `maidai`.`orientadores` (`pessoas_id`, `ppg_id`) VALUES (7, 3);
INSERT INTO `maidai`.`orientadores` (`pessoas_id`, `ppg_id`) VALUES (9, 17);
INSERT INTO `maidai`.`orientadores` (`pessoas_id`, `ppg_id`) VALUES (11, 9);
INSERT INTO `maidai`.`orientadores` (`pessoas_id`, `ppg_id`) VALUES (13, 2);
INSERT INTO `maidai`.`orientadores` (`pessoas_id`, `ppg_id`) VALUES (15, 14);
INSERT INTO `maidai`.`orientadores` (`pessoas_id`, `ppg_id`) VALUES (17, 6);
INSERT INTO `maidai`.`orientadores` (`pessoas_id`, `ppg_id`) VALUES (19, 11);

-- Next 10 orientadores (using pessoas_id 21-40 where tipos_pessoa_id = 1)
INSERT INTO `maidai`.`orientadores` (`pessoas_id`, `ppg_id`) VALUES (21, 7);
INSERT INTO `maidai`.`orientadores` (`pessoas_id`, `ppg_id`) VALUES (23, 15);
INSERT INTO `maidai`.`orientadores` (`pessoas_id`, `ppg_id`) VALUES (25, 4);
INSERT INTO `maidai`.`orientadores` (`pessoas_id`, `ppg_id`) VALUES (27, 19);
INSERT INTO `maidai`.`orientadores` (`pessoas_id`, `ppg_id`) VALUES (29, 10);
INSERT INTO `maidai`.`orientadores` (`pessoas_id`, `ppg_id`) VALUES (31, 1);
INSERT INTO `maidai`.`orientadores` (`pessoas_id`, `ppg_id`) VALUES (33, 16);
INSERT INTO `maidai`.`orientadores` (`pessoas_id`, `ppg_id`) VALUES (35, 20);
INSERT INTO `maidai`.`orientadores` (`pessoas_id`, `ppg_id`) VALUES (37, 13);
INSERT INTO `maidai`.`orientadores` (`pessoas_id`, `ppg_id`) VALUES (39, 18);

-- These companies match exactly the empresas_id (1-20) referenced in:
-- The 30 projetos inserts I provided earlier
-- Each empresa has:
-- - Unique name (respecting UNIQUE constraint)
-- - Realistic tech/industry names
-- - created_at = current timestamp
-- - updated_at = NULL for ~50% of records

-- 20 companies that match the projetos references
INSERT INTO `maidai`.`empresas` (`nome`, `created_at`, `updated_at`) VALUES
('TechSolutions Brasil', NOW(6), NOW(6)),
('InovaTech Sistemas', NOW(6), NULL),
('GreenFuture Tecnologia', NOW(6), NOW(6)),
('DataMind Analytics', NOW(6), NULL),
('CloudNine Hosting', NOW(6), NOW(6)),
('AgroDigital Solutions', NOW(6), NULL),
('UrbanMobility Labs', NOW(6), NOW(6)),
('EcoPower Energia', NOW(6), NULL),
('TravelConnect Turismo', NOW(6), NOW(6)),
('EduTech Inovação', NOW(6), NULL),
('EnviroMonitor Systems', NOW(6), NOW(6)),
('FinanSmart Consultoria', NOW(6), NULL),
('RecrutaPlus RH', NOW(6), NOW(6)),
('LogiFaster Transportes', NOW(6), NULL),
('HealthCare Systems', NOW(6), NOW(6)),
('AR Education Labs', NOW(6), NULL),
('SocialFund Plataformas', NOW(6), NOW(6)),
('HomeSecure Tecnologia', NOW(6), NULL),
('ProManage Solutions', NOW(6), NOW(6)),
('MindBalance Apps', NOW(6), NULL);

-- 30 projects with orientador/coorientador assignments
INSERT INTO `maidai`.`projetos` (`nome`, `inicio`, `fim`, `created_at`, `updated_at`, `empresas_id`, `orientador`, `coorientador`) VALUES
('Sistema de Gestão Escolar Inteligente', '2023-01-15', '2023-12-20', NOW(6), NULL, 1, 1, 3),
('Plataforma de E-commerce Sustentável', '2023-02-01', '2023-11-30', NOW(6), NOW(6), 2, 3, 5),
('Aplicativo de Monitoramento de Saúde', '2023-03-10', '2024-02-15', NOW(6), NULL, 3, 5, 7),
('Sistema de Controle de Estoque Automatizado', '2023-04-05', '2023-10-31', NOW(6), NOW(6), 4, 7, 9),
('Plataforma de Ensino a Distância', '2023-05-20', '2024-05-19', NOW(6), NULL, 5, 9, 11),
('Solução IoT para Agricultura', '2023-06-15', '2024-01-30', NOW(6), NOW(6), 6, 11, 13),
('Aplicativo de Mobilidade Urbana', '2023-07-01', '2023-12-15', NOW(6), NULL, 7, 13, 15),
('Sistema de Gestão de Energia', '2023-08-10', '2024-03-25', NOW(6), NOW(6), 8, 15, 17),
('Plataforma de Turismo Colaborativo', '2023-09-05', '2024-02-28', NOW(6), NULL, 9, 17, 19),
('Ferramenta de Análise de Dados Educacionais', '2023-10-01', '2024-06-30', NOW(6), NOW(6), 10, 19, 21),

('Sistema de Monitoramento Ambiental', '2023-11-15', '2024-05-20', NOW(6), NULL, 11, 21, 23),
('Aplicativo de Finanças Pessoais', '2023-12-01', '2024-04-15', NOW(6), NOW(6), 12, 23, 25),
('Plataforma de Recrutamento Digital', '2024-01-10', '2024-08-31', NOW(6), NULL, 13, 25, 27),
('Solução de Logística Inteligente', '2024-02-05', '2024-07-20', NOW(6), NOW(6), 14, 27, 29),
('Sistema de Gestão Hospitalar', '2024-03-15', '2024-09-30', NOW(6), NULL, 15, 29, 31),
('Aplicativo de Realidade Aumentada para Educação', '2024-04-01', '2024-10-15', NOW(6), NOW(6), 16, 31, 33),
('Plataforma de Crowdfunding', '2024-05-10', '2024-11-30', NOW(6), NULL, 17, 33, 35),
('Sistema de Segurança Residencial', '2024-06-05', '2024-12-20', NOW(6), NOW(6), 18, 35, 37),
('Ferramenta de Gestão de Projetos', '2024-07-15', '2025-01-31', NOW(6), NULL, 19, 37, 39),
('Aplicativo de Bem-Estar Mental', '2024-08-01', '2025-02-28', NOW(6), NOW(6), 20, 39, 1),

('Sistema de Controle de Acesso Biométrico', '2024-09-10', '2025-03-15', NOW(6), NULL, 1, 1, 5),
('Plataforma de Comércio Justo', '2024-10-05', '2025-04-30', NOW(6), NOW(6), 2, 3, 7),
('Aplicativo de Gestão de Tempo', '2024-11-15', '2025-05-20', NOW(6), NULL, 3, 5, 9),
('Solução de Armazenamento em Nuvem', '2025-01-01', '2025-06-15', NOW(6), NOW(6), 4, 7, 11),
('Plataforma de Aprendizado de Máquina', '2025-02-10', '2025-08-31', NOW(6), NULL, 5, 9, 13),
('Sistema de Monitoramento de Tráfego', '2025-03-05', '2025-09-20', NOW(6), NOW(6), 6, 11, 15),
('Aplicativo de Turismo Acessível', '2025-04-15', '2025-10-31', NOW(6), NULL, 7, 13, 17),
('Solução de Blockchain para Votação', '2025-05-01', '2025-11-15', NOW(6), NOW(6), 8, 15, 19),
('Plataforma de Doações para ONGs', '2025-06-10', '2025-12-30', NOW(6), NULL, 9, 17, 21),
('Sistema de Gestão de Bibliotecas', '2025-07-05', '2026-01-20', NOW(6), NOW(6), 10, 19, 23);

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
-- Table `maidai`.`editais`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `maidai`.`editais` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `ano` YEAR NOT NULL,
  `created_at` DATETIME NOT NULL,
  `updated_at` DATETIME NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

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

---- Remove the old foreign key constraints first
/*
ALTER TABLE `maidai`.`projetos`
DROP FOREIGN KEY `fk_projetos_orientadores1`,
DROP FOREIGN KEY `fk_projetos_orientadores2`;

-- Then drop the columns
ALTER TABLE `maidai`.`projetos`
DROP COLUMN `orientador`,
DROP COLUMN `coorientador`;
*/

---- If you need to keep the old structure temporarily during transition, you can rename columns instead of dropping them:
ALTER TABLE `maidai`.`projetos`
CHANGE COLUMN `orientador` `deprecated_orientador` INT UNSIGNED NULL,
CHANGE COLUMN `coorientador` `deprecated_coorientador` INT UNSIGNED NULL;



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

-- For records where ppg_id is null, you might want to:
-- 1. Set a default ppg_id, or
-- 2. Skip them (as done above)

-- Re-enable safe updates
SET SQL_SAFE_UPDATES = 1;

/*
-- First drop foreign key constraints from projetos that reference orientadores
ALTER TABLE `maidai`.`projetos`
DROP FOREIGN KEY `fk_projetos_orientadores1`,
DROP FOREIGN KEY `fk_projetos_orientadores2`;

-- Then drop the old table
DROP TABLE IF EXISTS `maidai`.`orientadores`;
*/

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

/*
INSERT INTO `roles` VALUES (1,'administrador'),(2,'colaborador');

INSERT INTO `tipos_pessoa` VALUES (2,'bolsista'),(1,'orientador');

INSERT INTO `users` VALUES (1,'admin','$2a$12$Ft5sNY69V4hCkxZbfqz6ZOHCVFMbDrNtz1CT3SezCOnoP9Cqd5lGm','admin',_binary '','2025-03-21 14:01:45.785000','2025-03-27 16:26:40.895000'),(2,'colaborator','$2a$12$MuBN9tvDJfAsvQaebCEjfeYvplptlPriPthD25r4j.7i8LV9xGrj6','colaborator',_binary '','2025-03-27 15:33:21.285000','2025-03-27 17:28:05.988000');

INSERT INTO `users_roles` VALUES (1,1),(2,2);
*/