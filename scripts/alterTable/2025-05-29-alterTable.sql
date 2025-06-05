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