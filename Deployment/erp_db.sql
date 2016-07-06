CREATE DATABASE  IF NOT EXISTS `erp` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `erp`;
-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: erp
-- ------------------------------------------------------
-- Server version	5.5.5-10.1.13-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `access_level`
--

DROP TABLE IF EXISTS `access_level`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `access_level` (
  `accessLevelType` varchar(255) NOT NULL,
  `canGetReport` varchar(1) DEFAULT '0',
  `canSearch` varchar(1) DEFAULT '0',
  `canGetResourceAttributes` varchar(1) DEFAULT '0',
  `canAddRemResource` varchar(1) DEFAULT '0',
  `canAddRemReq` varchar(1) DEFAULT '0',
  `canAddProject` varchar(1) DEFAULT '0',
  `canAddRemSysMod` varchar(1) DEFAULT '0',
  `canChangePermission` varchar(1) DEFAULT '0',
  `canConfirmNormalUser` varchar(1) DEFAULT '0',
  `canConfirmMidUser` varchar(1) DEFAULT '0',
  `canAddUnit` varchar(1) DEFAULT '0',
  PRIMARY KEY (`accessLevelType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `access_level`
--

LOCK TABLES `access_level` WRITE;
/*!40000 ALTER TABLE `access_level` DISABLE KEYS */;
INSERT INTO `access_level` VALUES ('High','1','1','1','1','1','1','1','1','1','1','1'),('Low','1','1','1','0','1','0','1','1','0','0','0'),('Medium','1','1','1','1','1','1','1','0','0','1','0');
/*!40000 ALTER TABLE `access_level` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `human_resource`
--

DROP TABLE IF EXISTS `human_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `human_resource` (
  `firstName` varchar(255) DEFAULT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  `expertise` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `confirmStatus` varchar(255) DEFAULT NULL,
  `ResourceID` varchar(255) NOT NULL,
  `AccessLevelType` varchar(255) NOT NULL DEFAULT '3',
  `logged_in` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ResourceID`),
  KEY `human_resource_ibfk_2` (`AccessLevelType`),
  CONSTRAINT `human_resource_ibfk_1` FOREIGN KEY (`ResourceID`) REFERENCES `resource` (`ID`),
  CONSTRAINT `human_resource_ibfk_2` FOREIGN KEY (`AccessLevelType`) REFERENCES `access_level` (`accessLevelType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `human_resource`
--

LOCK TABLES `human_resource` WRITE;
/*!40000 ALTER TABLE `human_resource` DISABLE KEYS */;
INSERT INTO `human_resource` VALUES ('admin','admin','administrator','admin','CONFIRMED','310243','High',1),('مریم','غلامعلی‌تبار','مهندسی نیازمندی‌ها','777777','PENDING','431504','High',0),('پردیس','پاشاخانلو','ریفکتورینگ','88888','PENDING','505943','High',0),('مژده','قینی','تحلیل','999999','PENDING','859478','High',0);
/*!40000 ALTER TABLE `human_resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `information_resource`
--

DROP TABLE IF EXISTS `information_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `information_resource` (
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `ResourceID` varchar(255) NOT NULL,
  PRIMARY KEY (`ResourceID`),
  CONSTRAINT `information_resource_ibfk_1` FOREIGN KEY (`ResourceID`) REFERENCES `resource` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `information_resource`
--

LOCK TABLES `information_resource` WRITE;
/*!40000 ALTER TABLE `information_resource` DISABLE KEYS */;
INSERT INTO `information_resource` VALUES ('پایگاه‌داده بیماران','اطلاعات همه‌ی بیماران بیمارستان بهگر','873389');
/*!40000 ALTER TABLE `information_resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `module`
--

DROP TABLE IF EXISTS `module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `module` (
  `ID` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `developmentStart` date DEFAULT NULL,
  `developmentEnd` date DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `module`
--

LOCK TABLES `module` WRITE;
/*!40000 ALTER TABLE `module` DISABLE KEYS */;
/*!40000 ALTER TABLE `module` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `module_humanresource`
--

DROP TABLE IF EXISTS `module_humanresource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `module_humanresource` (
  `ModuleID` varchar(255) NOT NULL,
  `HumanResourceID` varchar(255) NOT NULL,
  PRIMARY KEY (`ModuleID`,`HumanResourceID`),
  KEY `HumanResourceID` (`HumanResourceID`),
  CONSTRAINT `module_humanresource_ibfk_1` FOREIGN KEY (`ModuleID`) REFERENCES `module` (`ID`),
  CONSTRAINT `module_humanresource_ibfk_2` FOREIGN KEY (`HumanResourceID`) REFERENCES `human_resource` (`ResourceID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `module_humanresource`
--

LOCK TABLES `module_humanresource` WRITE;
/*!40000 ALTER TABLE `module_humanresource` DISABLE KEYS */;
/*!40000 ALTER TABLE `module_humanresource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `module_modification`
--

DROP TABLE IF EXISTS `module_modification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `module_modification` (
  `ID` varchar(255) NOT NULL,
  `modificationType` varchar(255) DEFAULT NULL,
  `modificationStart` date DEFAULT NULL,
  `modificationEnd` date DEFAULT NULL,
  `ModuleID` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `ModuleID` (`ModuleID`),
  CONSTRAINT `module_modification_ibfk_1` FOREIGN KEY (`ModuleID`) REFERENCES `module` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `module_modification`
--

LOCK TABLES `module_modification` WRITE;
/*!40000 ALTER TABLE `module_modification` DISABLE KEYS */;
/*!40000 ALTER TABLE `module_modification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `module_system`
--

DROP TABLE IF EXISTS `module_system`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `module_system` (
  `ModuleID` varchar(255) NOT NULL,
  `SystemID` varchar(255) NOT NULL,
  PRIMARY KEY (`SystemID`,`ModuleID`),
  KEY `ModuleID` (`ModuleID`),
  CONSTRAINT `module_system_ibfk_1` FOREIGN KEY (`ModuleID`) REFERENCES `module` (`ID`),
  CONSTRAINT `module_system_ibfk_2` FOREIGN KEY (`SystemID`) REFERENCES `system` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `module_system`
--

LOCK TABLES `module_system` WRITE;
/*!40000 ALTER TABLE `module_system` DISABLE KEYS */;
/*!40000 ALTER TABLE `module_system` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `modulemodification_humanresource`
--

DROP TABLE IF EXISTS `modulemodification_humanresource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `modulemodification_humanresource` (
  `ModuleModificationID` varchar(255) NOT NULL,
  `HumanResourceID` varchar(255) NOT NULL,
  PRIMARY KEY (`ModuleModificationID`,`HumanResourceID`),
  KEY `HumanResourceID` (`HumanResourceID`),
  CONSTRAINT `modulemodification_humanresource_ibfk_1` FOREIGN KEY (`ModuleModificationID`) REFERENCES `module_modification` (`ID`),
  CONSTRAINT `modulemodification_humanresource_ibfk_2` FOREIGN KEY (`HumanResourceID`) REFERENCES `human_resource` (`ResourceID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `modulemodification_humanresource`
--

LOCK TABLES `modulemodification_humanresource` WRITE;
/*!40000 ALTER TABLE `modulemodification_humanresource` DISABLE KEYS */;
/*!40000 ALTER TABLE `modulemodification_humanresource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `monetary_resource`
--

DROP TABLE IF EXISTS `monetary_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `monetary_resource` (
  `monetaryType` varchar(255) DEFAULT NULL,
  `quantity_amount` int(15) DEFAULT NULL,
  `quantity_unit` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `accountNumber` int(10) DEFAULT NULL,
  `ResourceID` varchar(255) NOT NULL,
  PRIMARY KEY (`ResourceID`),
  CONSTRAINT `monetary_resource_ibfk_1` FOREIGN KEY (`ResourceID`) REFERENCES `resource` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `monetary_resource`
--

LOCK TABLES `monetary_resource` WRITE;
/*!40000 ALTER TABLE `monetary_resource` DISABLE KEYS */;
INSERT INTO `monetary_resource` VALUES ('NON_CASH',1000000,'RIAL','واحد اداری',0,'653741');
/*!40000 ALTER TABLE `monetary_resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `physical_resource`
--

DROP TABLE IF EXISTS `physical_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `physical_resource` (
  `name` varchar(255) DEFAULT NULL,
  `model` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `ResourceID` varchar(255) NOT NULL,
  PRIMARY KEY (`ResourceID`),
  CONSTRAINT `physical_resource_ibfk_1` FOREIGN KEY (`ResourceID`) REFERENCES `resource` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `physical_resource`
--

LOCK TABLES `physical_resource` WRITE;
/*!40000 ALTER TABLE `physical_resource` DISABLE KEYS */;
INSERT INTO `physical_resource` VALUES ('صندلی','نیلپر','اتاق ۲۰۲','342306');
/*!40000 ALTER TABLE `physical_resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project` (
  `ID` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `developmentStart` date DEFAULT NULL,
  `developmentEnd` date DEFAULT NULL,
  `customerName` varchar(255) DEFAULT NULL,
  `usersCount` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES ('256789','گیشه',NULL,NULL,'null',0),('605356','سیستم آموزش شریف',NULL,NULL,'null',5);
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_management`
--

DROP TABLE IF EXISTS `project_management`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_management` (
  `ProjectID` varchar(255) NOT NULL,
  `ManagerID` varchar(255) NOT NULL,
  PRIMARY KEY (`ProjectID`,`ManagerID`),
  KEY `ManagerID` (`ManagerID`),
  CONSTRAINT `project_management_ibfk_1` FOREIGN KEY (`ProjectID`) REFERENCES `project` (`ID`),
  CONSTRAINT `project_management_ibfk_3` FOREIGN KEY (`ManagerID`) REFERENCES `human_resource` (`ResourceID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_management`
--

LOCK TABLES `project_management` WRITE;
/*!40000 ALTER TABLE `project_management` DISABLE KEYS */;
INSERT INTO `project_management` VALUES ('605356','310243');
/*!40000 ALTER TABLE `project_management` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_requirement`
--

DROP TABLE IF EXISTS `project_requirement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_requirement` (
  `ID` varchar(255) NOT NULL,
  `provideDate` date DEFAULT NULL,
  `releaseDate` date DEFAULT NULL,
  `isEssential` tinyint(4) DEFAULT NULL,
  `criticalProvideDate` varchar(8) DEFAULT NULL,
  `lengthOfPossession` int(10) DEFAULT NULL,
  `ResourceID` varchar(255) NOT NULL,
  `ProjectID` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `ResourceID` (`ResourceID`),
  KEY `ProjectID` (`ProjectID`),
  CONSTRAINT `project_requirement_ibfk_1` FOREIGN KEY (`ResourceID`) REFERENCES `resource` (`ID`),
  CONSTRAINT `project_requirement_ibfk_2` FOREIGN KEY (`ProjectID`) REFERENCES `project` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_requirement`
--

LOCK TABLES `project_requirement` WRITE;
/*!40000 ALTER TABLE `project_requirement` DISABLE KEYS */;
/*!40000 ALTER TABLE `project_requirement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_technology`
--

DROP TABLE IF EXISTS `project_technology`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_technology` (
  `ProjectID` varchar(255) NOT NULL,
  `Technologyname` varchar(255) NOT NULL,
  PRIMARY KEY (`ProjectID`,`Technologyname`),
  KEY `Technologyname` (`Technologyname`),
  CONSTRAINT `project_technology_ibfk_1` FOREIGN KEY (`ProjectID`) REFERENCES `project` (`ID`),
  CONSTRAINT `project_technology_ibfk_2` FOREIGN KEY (`Technologyname`) REFERENCES `technology` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_technology`
--

LOCK TABLES `project_technology` WRITE;
/*!40000 ALTER TABLE `project_technology` DISABLE KEYS */;
INSERT INTO `project_technology` VALUES ('605356','جاوا');
/*!40000 ALTER TABLE `project_technology` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_unit`
--

DROP TABLE IF EXISTS `project_unit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_unit` (
  `ProjectID` varchar(255) NOT NULL,
  `UnitID` varchar(255) NOT NULL,
  PRIMARY KEY (`ProjectID`,`UnitID`),
  KEY `UnitID` (`UnitID`),
  CONSTRAINT `project_unit_ibfk_1` FOREIGN KEY (`ProjectID`) REFERENCES `project` (`ID`),
  CONSTRAINT `project_unit_ibfk_2` FOREIGN KEY (`UnitID`) REFERENCES `unit` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_unit`
--

LOCK TABLES `project_unit` WRITE;
/*!40000 ALTER TABLE `project_unit` DISABLE KEYS */;
INSERT INTO `project_unit` VALUES ('256789','715291'),('256789','825162'),('256789','872639'),('256789','916203'),('605356','715291'),('605356','825162');
/*!40000 ALTER TABLE `project_unit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `requirement`
--

DROP TABLE IF EXISTS `requirement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `requirement` (
  `ID` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `provideDate` date DEFAULT NULL,
  `ResourceID` varchar(255) NOT NULL,
  `UnitID` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `ResourceID` (`ResourceID`),
  KEY `UnitID` (`UnitID`),
  CONSTRAINT `requirement_ibfk_2` FOREIGN KEY (`UnitID`) REFERENCES `unit` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `requirement`
--

LOCK TABLES `requirement` WRITE;
/*!40000 ALTER TABLE `requirement` DISABLE KEYS */;
/*!40000 ALTER TABLE `requirement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resource`
--

DROP TABLE IF EXISTS `resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resource` (
  `ID` varchar(255) NOT NULL,
  `resourceStatus` varchar(255) DEFAULT NULL,
  `isAvailable` tinyint(4) DEFAULT NULL,
  `ProjectID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `resource_ibfk_3` (`ProjectID`),
  CONSTRAINT `resource_ibfk_3` FOREIGN KEY (`ProjectID`) REFERENCES `project` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resource`
--

LOCK TABLES `resource` WRITE;
/*!40000 ALTER TABLE `resource` DISABLE KEYS */;
INSERT INTO `resource` VALUES ('310243','IDLE',1,NULL),('342306','IDLE',1,NULL),('431504','IDLE',1,NULL),('505943','IDLE',1,NULL),('653741','IDLE',1,NULL),('859478','IDLE',1,NULL),('873389','IDLE',1,NULL);
/*!40000 ALTER TABLE `resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system`
--

DROP TABLE IF EXISTS `system`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system` (
  `ID` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `ProjectID` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `ProjectID` (`ProjectID`),
  CONSTRAINT `system_ibfk_1` FOREIGN KEY (`ProjectID`) REFERENCES `project` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system`
--

LOCK TABLES `system` WRITE;
/*!40000 ALTER TABLE `system` DISABLE KEYS */;
/*!40000 ALTER TABLE `system` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `technology`
--

DROP TABLE IF EXISTS `technology`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `technology` (
  `name` varchar(255) NOT NULL,
  `reason` varchar(511) DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `technology`
--

LOCK TABLES `technology` WRITE;
/*!40000 ALTER TABLE `technology` DISABLE KEYS */;
INSERT INTO `technology` VALUES ('جاوا','استفاده از امکانات شی‌گرایی');
/*!40000 ALTER TABLE `technology` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unit`
--

DROP TABLE IF EXISTS `unit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `unit` (
  `ID` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unit`
--

LOCK TABLES `unit` WRITE;
/*!40000 ALTER TABLE `unit` DISABLE KEYS */;
INSERT INTO `unit` VALUES ('715291','مهندسی نیازمندی‌ها'),('825162','طراحی'),('872639','تحلیل'),('916203','پیاده‌سازی');
/*!40000 ALTER TABLE `unit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unit_resource`
--

DROP TABLE IF EXISTS `unit_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `unit_resource` (
  `ID` varchar(255) NOT NULL,
  `additionDate` date DEFAULT NULL,
  `removeDate` date DEFAULT NULL,
  `ResourceID` varchar(255) NOT NULL,
  `UnitID` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `ResourceID` (`ResourceID`),
  KEY `UnitID` (`UnitID`),
  CONSTRAINT `unit_resource_ibfk_1` FOREIGN KEY (`ResourceID`) REFERENCES `resource` (`ID`),
  CONSTRAINT `unit_resource_ibfk_2` FOREIGN KEY (`UnitID`) REFERENCES `unit` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unit_resource`
--

LOCK TABLES `unit_resource` WRITE;
/*!40000 ALTER TABLE `unit_resource` DISABLE KEYS */;
INSERT INTO `unit_resource` VALUES ('292483','2016-07-05','0000-00-00','859478','872639'),('327415','2016-07-05','0000-00-00','873389','916203'),('436133','2016-07-05','0000-00-00','342306','916203'),('484710','2016-07-05','0000-00-00','505943','825162'),('641290','2016-07-05','0000-00-00','653741','825162'),('745563','2016-07-05','0000-00-00','310243','872639'),('877644','2016-07-05','0000-00-00','431504','715291');
/*!40000 ALTER TABLE `unit_resource` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-07-06  2:36:32
