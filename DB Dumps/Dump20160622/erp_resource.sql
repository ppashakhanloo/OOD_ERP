-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: erp
-- ------------------------------------------------------
-- Server version	5.5.49

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
  `UnitID` varchar(255) NOT NULL,
  `ProjectID` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `UnitID` (`UnitID`),
  KEY `ProjectID` (`ProjectID`),
  CONSTRAINT `resource_ibfk_3` FOREIGN KEY (`ProjectID`) REFERENCES `project` (`ID`),
  CONSTRAINT `resource_ibfk_1` FOREIGN KEY (`UnitID`) REFERENCES `unit` (`ID`),
  CONSTRAINT `resource_ibfk_2` FOREIGN KEY (`UnitID`) REFERENCES `unit` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resource`
--

LOCK TABLES `resource` WRITE;
/*!40000 ALTER TABLE `resource` DISABLE KEYS */;
INSERT INTO `resource` VALUES ('102664','IDLE',0,'1','1'),('117096','IDLE',0,'1','1'),('167893','IDLE',0,'1','1'),('188546','IDLE',0,'1','1'),('215343','IDLE',0,'1','1'),('225066','IDLE',0,'1','1'),('266022','IDLE',0,'1','1'),('267379','IDLE',0,'1','1'),('406079','IDLE',0,'1','1'),('413164','IDLE',0,'1','1'),('418066','IDLE',0,'1','1'),('479891','IDLE',0,'1','1'),('484850','IDLE',0,'1','1'),('591625','IDLE',0,'1','1'),('620640','IDLE',0,'1','1'),('621900','IDLE',0,'1','1'),('657312','IDLE',0,'1','1'),('661401','IDLE',0,'1','1'),('708814','IDLE',0,'1','1'),('736826','IDLE',0,'1','1'),('746945','IDLE',0,'1','1'),('748573','IDLE',0,'1','1'),('760082','IDLE',0,'1','1'),('774992','IDLE',0,'1','1'),('791523','IDLE',0,'1','1'),('796307','IDLE',0,'1','1'),('805576','IDLE',0,'1','1'),('852934','IDLE',0,'1','1'),('858241','IDLE',0,'1','1'),('876579','IDLE',0,'1','1'),('926047','IDLE',0,'1','1'),('955731','IDLE',0,'1','1'),('958114','IDLE',0,'1','1');
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

-- Dump completed on 2016-06-24  1:35:26
