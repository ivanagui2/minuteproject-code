SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `porphyry` ;
CREATE SCHEMA IF NOT EXISTS `porphyry` DEFAULT CHARACTER SET latin1 ;
USE `porphyry` ;

-- -----------------------------------------------------
-- Table `porphyry`.`ADMIN_ENVIRONMENT`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `porphyry`.`ADMIN_ENVIRONMENT` ;

CREATE  TABLE IF NOT EXISTS `porphyry`.`ADMIN_ENVIRONMENT` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `NAME` VARCHAR(45) NOT NULL ,
  `DESCRIPTION` VARCHAR(45) NOT NULL ,
  `DISPLAY_ORDER` INT NOT NULL ,
  PRIMARY KEY (`ID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `porphyry`.`ADMIN_PROJECT`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `porphyry`.`ADMIN_PROJECT` ;

CREATE  TABLE IF NOT EXISTS `porphyry`.`ADMIN_PROJECT` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `NAME` VARCHAR(45) NULL ,
  PRIMARY KEY (`ID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `porphyry`.`ADMIN_APPLICATION`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `porphyry`.`ADMIN_APPLICATION` ;

CREATE  TABLE IF NOT EXISTS `porphyry`.`ADMIN_APPLICATION` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `NAME` VARCHAR(45) NOT NULL ,
  `DESCRIPTION` VARCHAR(45) NOT NULL ,
  `PROJECT_ID` INT NOT NULL ,
  PRIMARY KEY (`ID`) ,
  INDEX `fk_APPLICATION_PROJECT_idx` (`PROJECT_ID` ASC) ,
  CONSTRAINT `fk_APPLICATION_PROJECT`
    FOREIGN KEY (`PROJECT_ID` )
    REFERENCES `porphyry`.`ADMIN_PROJECT` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `porphyry`.`ADMIN_ACCESS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `porphyry`.`ADMIN_ACCESS` ;

CREATE  TABLE IF NOT EXISTS `porphyry`.`ADMIN_ACCESS` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `NAME` VARCHAR(45) NOT NULL ,
  `URL` VARCHAR(300) NOT NULL ,
  `USERNAME` VARCHAR(45) NOT NULL ,
  `PASSWORD` VARCHAR(45) NOT NULL ,
  `APPLICATION_ID` INT NOT NULL ,
  `ENVIRONMENT_ID` INT NOT NULL ,
  PRIMARY KEY (`ID`) ,
  INDEX `fk_ACCESS_APPLICATION1_idx` (`APPLICATION_ID` ASC) ,
  INDEX `fk_ACCESS_ENVIRONMENT1_idx` (`ENVIRONMENT_ID` ASC) ,
  UNIQUE INDEX `NAME_UNIQUE` (`NAME` ASC) ,
  CONSTRAINT `fk_ACCESS_APPLICATION1`
    FOREIGN KEY (`APPLICATION_ID` )
    REFERENCES `porphyry`.`ADMIN_APPLICATION` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ACCESS_ENVIRONMENT1`
    FOREIGN KEY (`ENVIRONMENT_ID` )
    REFERENCES `porphyry`.`ADMIN_ENVIRONMENT` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `porphyry`.`SEC_USER`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `porphyry`.`SEC_USER` ;

CREATE  TABLE IF NOT EXISTS `porphyry`.`SEC_USER` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `IDENTIFIER` VARCHAR(45) NOT NULL ,
  `ACTIVE` TINYINT(1) NOT NULL DEFAULT 1 ,
  `EMAIL` VARCHAR(100) NOT NULL ,
  PRIMARY KEY (`ID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `porphyry`.`BUS_RELEASE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `porphyry`.`BUS_RELEASE` ;

CREATE  TABLE IF NOT EXISTS `porphyry`.`BUS_RELEASE` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `NAME` VARCHAR(45) NOT NULL ,
  `NOTES` TEXT NULL ,
  `APPLICATION_ID` INT NOT NULL ,
  `ENVIRONMENT_ID` INT NOT NULL ,
  `RELEASER_ID` INT NOT NULL ,
  `STATUS` VARCHAR(45) NULL ,
  `CREATION_DATE` DATETIME NULL ,
  `VALIDATOR_ID` INT NULL ,
  `TIME_TO_START` DATETIME NULL ,
  `TIME_TO_END` DATETIME NULL ,
  `VALIDATION REASON` TEXT NULL ,
  PRIMARY KEY (`ID`) ,
  INDEX `fk_RELEASE_APPLICATION1_idx` (`APPLICATION_ID` ASC) ,
  INDEX `fk_RELEASE_ENVIRONMENT1_idx` (`ENVIRONMENT_ID` ASC) ,
  INDEX `fk_RELEASE_USER1_idx` (`RELEASER_ID` ASC) ,
  INDEX `fk_RELEASE_USER2_idx` (`VALIDATOR_ID` ASC) ,
  CONSTRAINT `fk_RELEASE_APPLICATION1`
    FOREIGN KEY (`APPLICATION_ID` )
    REFERENCES `porphyry`.`ADMIN_APPLICATION` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_RELEASE_ENVIRONMENT1`
    FOREIGN KEY (`ENVIRONMENT_ID` )
    REFERENCES `porphyry`.`ADMIN_ENVIRONMENT` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_RELEASE_USER1`
    FOREIGN KEY (`RELEASER_ID` )
    REFERENCES `porphyry`.`SEC_USER` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_RELEASE_USER2`
    FOREIGN KEY (`VALIDATOR_ID` )
    REFERENCES `porphyry`.`SEC_USER` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `porphyry`.`SEC_ROLE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `porphyry`.`SEC_ROLE` ;

CREATE  TABLE IF NOT EXISTS `porphyry`.`SEC_ROLE` (
  `ID` INT NOT NULL AUTO_INCREMENT ,
  `ROLE` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`ID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `porphyry`.`SEC_USER_ROLE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `porphyry`.`SEC_USER_ROLE` ;

CREATE  TABLE IF NOT EXISTS `porphyry`.`SEC_USER_ROLE` (
  `SEC_USER_ID` INT NOT NULL ,
  `SEC_ROLE_ID` INT NOT NULL ,
  INDEX `fk_SEC_GROUP_SEC_USER1_idx` (`SEC_USER_ID` ASC) ,
  INDEX `fk_SEC_GROUP_SEC_ROLE1_idx` (`SEC_ROLE_ID` ASC) ,
  PRIMARY KEY (`SEC_USER_ID`, `SEC_ROLE_ID`) ,
  CONSTRAINT `fk_SEC_GROUP_SEC_USER1`
    FOREIGN KEY (`SEC_USER_ID` )
    REFERENCES `porphyry`.`SEC_USER` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_SEC_GROUP_SEC_ROLE1`
    FOREIGN KEY (`SEC_ROLE_ID` )
    REFERENCES `porphyry`.`SEC_ROLE` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `porphyry`.`ADMIN_APPLI_ENV`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `porphyry`.`ADMIN_APPLI_ENV` ;

CREATE  TABLE IF NOT EXISTS `porphyry`.`ADMIN_APPLI_ENV` (
  `ADMIN_APPLICATION_ID` INT NOT NULL ,
  `ADMIN_ENVIRONMENT_ID` INT NOT NULL ,
  INDEX `fk_ADMIN_APPLI_ENV_ADMIN_APPLICATION1_idx` (`ADMIN_APPLICATION_ID` ASC) ,
  INDEX `fk_ADMIN_APPLI_ENV_ADMIN_ENVIRONMENT1_idx` (`ADMIN_ENVIRONMENT_ID` ASC) ,
  PRIMARY KEY (`ADMIN_APPLICATION_ID`, `ADMIN_ENVIRONMENT_ID`) ,
  CONSTRAINT `fk_ADMIN_APPLI_ENV_ADMIN_APPLICATION1`
    FOREIGN KEY (`ADMIN_APPLICATION_ID` )
    REFERENCES `porphyry`.`ADMIN_APPLICATION` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ADMIN_APPLI_ENV_ADMIN_ENVIRONMENT1`
    FOREIGN KEY (`ADMIN_ENVIRONMENT_ID` )
    REFERENCES `porphyry`.`ADMIN_ENVIRONMENT` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `porphyry`.`SEC_PROJECT_RIGHT`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `porphyry`.`SEC_PROJECT_RIGHT` ;

CREATE  TABLE IF NOT EXISTS `porphyry`.`SEC_PROJECT_RIGHT` (
  `ID_SEC_PROJECT_RIGHT` INT NOT NULL AUTO_INCREMENT ,
  `SEC_USER_ID` INT NOT NULL ,
  `ADMIN_APPLICATION_ID` INT NOT NULL ,
  `ROLE` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`ID_SEC_PROJECT_RIGHT`) ,
  INDEX `fk_SEC_PROJECT_RIGHT_SEC_USER1_idx` (`SEC_USER_ID` ASC) ,
  INDEX `fk_SEC_PROJECT_RIGHT_ADMIN_APPLICATION1_idx` (`ADMIN_APPLICATION_ID` ASC) ,
  CONSTRAINT `fk_SEC_PROJECT_RIGHT_SEC_USER1`
    FOREIGN KEY (`SEC_USER_ID` )
    REFERENCES `porphyry`.`SEC_USER` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_SEC_PROJECT_RIGHT_ADMIN_APPLICATION1`
    FOREIGN KEY (`ADMIN_APPLICATION_ID` )
    REFERENCES `porphyry`.`ADMIN_APPLICATION` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `porphyry` ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
