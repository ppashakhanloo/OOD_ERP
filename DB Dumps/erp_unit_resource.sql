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
-- Table structure for table `unit_resource`
--

DROP TABLE IF EXISTS `unit_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `unit_resource` (
  `ID` varchar(255) NOT NULL,
  `additionDate` varchar(255) DEFAULT NULL,
  `removeDate` varchar(255) DEFAULT NULL,
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
INSERT INTO `unit_resource` VALUES ('121100','Sat Jul 02 21:53:34 IRDT 2016','null','778034','1'),('341942','Sat Jul 02 22:59:32 IRDT 2016','null','246005','1'),('670827','Sat Jul 02 22:02:01 IRDT 2016','null','105401','860349'),('685646','Sat Jul 02 23:08:50 IRDT 2016','null','619031','1'),('712800','Sat Jul 02 22:04:40 IRDT 2016','null','526894','1'),('751506','Sat Jul 02 22:05:17 IRDT 2016','null','414400','836936'),('775566','Sat Jul 02 22:59:44 IRDT 2016','null','433388','1'),('942136','Sat Jul 02 23:14:45 IRDT 2016','null','165536','1'),('997915','Sat Jul 02 22:54:23 IRDT 2016','null','717278','836936');
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

-- Dump completed on 2016-07-03  3:33:51
