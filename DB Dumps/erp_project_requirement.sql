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
  `criticalProvideDate` date DEFAULT NULL,
  `lengthOfPossession` int(10) DEFAULT NULL,
  `ResourceID` varchar(255) NOT NULL,
  `ProjectID` varchar(255) NOT NULL,
  `ModuleID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `ResourceID` (`ResourceID`),
  KEY `ProjectID` (`ProjectID`),
  KEY `ModuleID` (`ModuleID`),
  CONSTRAINT `project_requirement_ibfk_1` FOREIGN KEY (`ResourceID`) REFERENCES `resource` (`ID`),
  CONSTRAINT `project_requirement_ibfk_2` FOREIGN KEY (`ProjectID`) REFERENCES `project` (`ID`),
  CONSTRAINT `project_requirement_ibfk_3` FOREIGN KEY (`ModuleID`) REFERENCES `module` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_requirement`
--

LOCK TABLES `project_requirement` WRITE;
/*!40000 ALTER TABLE `project_requirement` DISABLE KEYS */;
/*!40000 ALTER TABLE `project_requirement` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-07-13 21:03:36
