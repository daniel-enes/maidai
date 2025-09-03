ALTER TABLE `maidai`.`bolsas`
    ADD COLUMN `status` VARCHAR(255) NULL AFTER `fim`;

-- First, we need to drop the unique index
ALTER TABLE `maidai`.`bolsas`
DROP INDEX `pessoas_id_UNIQUE`;