SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `functional` ;
CREATE SCHEMA IF NOT EXISTS `functional` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `functional` ;

-- -----------------------------------------------------
-- Table `functional`.`COUNTRY`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `functional`.`COUNTRY` ;

CREATE  TABLE IF NOT EXISTS `functional`.`COUNTRY` (
  `ID` INT NOT NULL ,
  `NAME` VARCHAR(45) NULL ,
  `CAPITAL` INT NULL ,
  PRIMARY KEY (`ID`) ,
  INDEX `fk_COUNTRY_CITY1_idx` (`CAPITAL` ASC) ,
  CONSTRAINT `fk_COUNTRY_CITY1`
    FOREIGN KEY (`CAPITAL` )
    REFERENCES `functional`.`CITY` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `functional`.`CITY`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `functional`.`CITY` ;

CREATE  TABLE IF NOT EXISTS `functional`.`CITY` (
  `ID` INT NOT NULL ,
  `NAME` VARCHAR(45) NULL ,
  `COUNTRY_ID` INT NOT NULL ,
  PRIMARY KEY (`ID`) ,
  INDEX `fk_CITY_COUNTRY1_idx` (`COUNTRY_ID` ASC) ,
  CONSTRAINT `fk_CITY_COUNTRY1`
    FOREIGN KEY (`COUNTRY_ID` )
    REFERENCES `functional`.`COUNTRY` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `functional`.`ADDRESS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `functional`.`ADDRESS` ;

CREATE  TABLE IF NOT EXISTS `functional`.`ADDRESS` (
  `ADDRESS_ID` INT NOT NULL ,
  `STREET1` VARCHAR(45) NULL ,
  `NUMBER` INT NULL ,
  `CITY_ID` INT NOT NULL ,
  PRIMARY KEY (`ADDRESS_ID`) ,
  INDEX `fk_ADDRESS_CITY1_idx` (`CITY_ID` ASC) ,
  CONSTRAINT `fk_ADDRESS_CITY1`
    FOREIGN KEY (`CITY_ID` )
    REFERENCES `functional`.`CITY` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `functional`.`B_CLIENT`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `functional`.`B_CLIENT` ;

CREATE  TABLE IF NOT EXISTS `functional`.`B_CLIENT` (
  `ID` INT NOT NULL ,
  `NAME` VARCHAR(45) NOT NULL ,
  `DESCRIPTION` VARCHAR(45) NULL ,
  `AGE` INT NOT NULL ,
  `ADDRESS_ADDRESS_ID` INT NOT NULL ,
  `TITLE` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`ID`) ,
  INDEX `fk_B_CLIENT_ADDRESS1_idx` (`ADDRESS_ADDRESS_ID` ASC) ,
  CONSTRAINT `fk_B_CLIENT_ADDRESS1`
    FOREIGN KEY (`ADDRESS_ADDRESS_ID` )
    REFERENCES `functional`.`ADDRESS` (`ADDRESS_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `functional`.`COMPANY`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `functional`.`COMPANY` ;

CREATE  TABLE IF NOT EXISTS `functional`.`COMPANY` (
  `ID` INT NOT NULL ,
  `NAME` VARCHAR(45) NULL ,
  PRIMARY KEY (`ID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `functional`.`DEPARTMENT`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `functional`.`DEPARTMENT` ;

CREATE  TABLE IF NOT EXISTS `functional`.`DEPARTMENT` (
  `ID` INT NOT NULL ,
  `PARENT_DEPARTMENT_ID` INT NOT NULL ,
  `NAME` VARCHAR(45) NULL ,
  `COMPANY_ID` INT NULL ,
  PRIMARY KEY (`ID`) ,
  INDEX `fk_DEPARTMENT_DEPARTMENT_idx` (`PARENT_DEPARTMENT_ID` ASC) ,
  INDEX `fk_DEPARTMENT_COMPANY1_idx` (`COMPANY_ID` ASC) ,
  CONSTRAINT `fk_DEPARTMENT_DEPARTMENT`
    FOREIGN KEY (`PARENT_DEPARTMENT_ID` )
    REFERENCES `functional`.`DEPARTMENT` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_DEPARTMENT_COMPANY1`
    FOREIGN KEY (`COMPANY_ID` )
    REFERENCES `functional`.`COMPANY` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `functional`.`USER`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `functional`.`USER` ;

CREATE  TABLE IF NOT EXISTS `functional`.`USER` (
  `ID` INT NOT NULL ,
  `NAME` VARCHAR(45) NULL ,
  `EMAIL` VARCHAR(45) NULL ,
  `DEPARTMENT_ID` INT NOT NULL ,
  PRIMARY KEY (`ID`) ,
  INDEX `fk_USER_DEPARTMENT1_idx` (`DEPARTMENT_ID` ASC) ,
  CONSTRAINT `fk_USER_DEPARTMENT1`
    FOREIGN KEY (`DEPARTMENT_ID` )
    REFERENCES `functional`.`DEPARTMENT` (`ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `functional` ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
