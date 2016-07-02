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
INSERT INTO `resource` VALUES ('100824','IDLE',1,'1'),('105401','IDLE',1,NULL),('128290','IDLE',1,NULL),('157726','IDLE',1,NULL),('165536','IDLE',1,NULL),('188546','IDLE',1,'1'),('215343','IDLE',1,'1'),('220835','IDLE',1,NULL),('225066','IDLE',1,'1'),('240331','IDLE',1,NULL),('241314','IDLE',1,'1'),('246005','IDLE',1,NULL),('248657','IDLE',1,NULL),('373666','IDLE',1,NULL),('393974','IDLE',1,NULL),('414400','IDLE',1,NULL),('415083','IDLE',1,NULL),('433388','IDLE',1,NULL),('476220','IDLE',1,NULL),('526894','IDLE',1,NULL),('538217','IDLE',1,NULL),('556727','IDLE',1,NULL),('619031','IDLE',1,NULL),('709604','IDLE',1,NULL),('717278','IDLE',1,NULL),('764488','IDLE',1,NULL),('777007','IDLE',1,NULL),('778034','IDLE',1,NULL),('783606','IDLE',1,NULL),('824871','IDLE',1,NULL),('951087','IDLE',1,NULL);
/*!40000 ALTER TABLE `resource` ENABLE KEYS */;
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
