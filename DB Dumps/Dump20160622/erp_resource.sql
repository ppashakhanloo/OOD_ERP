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
  `ProjectID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `UnitID` (`UnitID`),
  KEY `resource_ibfk_3` (`ProjectID`),
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
INSERT INTO `resource` VALUES ('102664','IDLE',1,'1','1'),('117096','IDLE',1,'1','1'),('133575','IDLE',0,'1',NULL),('156902','IDLE',1,'1',NULL),('158481','IDLE',1,'1',NULL),('167893','IDLE',1,'1','1'),('183777','IDLE',0,'1',NULL),('188546','IDLE',1,'1','1'),('215343','IDLE',1,'1','1'),('225066','IDLE',1,'1','1'),('241314','IDLE',1,'1',NULL),('260617','IDLE',1,'1',NULL),('261693','IDLE',1,'1',NULL),('266022','IDLE',1,'1','1'),('267379','IDLE',1,'1','1'),('271346','IDLE',1,'1',NULL),('297100','IDLE',1,'1',NULL),('306241','IDLE',1,'1',NULL),('308881','IDLE',1,'1',NULL),('381250','IDLE',1,'1',NULL),('398490','IDLE',1,'1',NULL),('406079','IDLE',1,'1','1'),('413164','IDLE',1,'1','1'),('418066','IDLE',1,'1','1'),('436447','IDLE',1,'1',NULL),('446804','IDLE',1,'1',NULL),('470650','IDLE',0,'1',NULL),('478837','IDLE',1,'1','1'),('479891','IDLE',1,'1','1'),('484850','IDLE',1,'1','1'),('490621','IDLE',0,'1',NULL),('591625','IDLE',1,'1','1'),('620640','IDLE',1,'1','1'),('621900','IDLE',1,'1','1'),('635278','IDLE',0,'1',NULL),('637217','IDLE',1,'1',NULL),('657312','IDLE',1,'1','1'),('661401','IDLE',1,'1','1'),('705087','IDLE',1,'1',NULL),('708814','IDLE',1,'1','1'),('736826','IDLE',1,'1','1'),('746945','IDLE',1,'1','1'),('748573','IDLE',1,'1','1'),('754594','IDLE',1,'1',NULL),('760082','IDLE',1,'1','1'),('774992','IDLE',1,'1','1'),('791523','IDLE',1,'1','1'),('796307','IDLE',1,'1','1'),('805576','IDLE',1,'1','1'),('819616','IDLE',1,'1',NULL),('822952','IDLE',1,'1',NULL),('832817','IDLE',1,'1','1'),('852934','IDLE',1,'1','1'),('858241','IDLE',1,'1','1'),('861735','IDLE',1,'1',NULL),('871539','IDLE',1,'1','1'),('876579','IDLE',1,'1','1'),('889547','IDLE',1,'1',NULL),('892247','IDLE',1,'1',NULL),('896974','IDLE',1,'1',NULL),('926047','IDLE',1,'1','1'),('930266','IDLE',1,'1',NULL),('955731','IDLE',1,'1','1'),('958114','IDLE',1,'1','1');
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

-- Dump completed on 2016-06-25  3:43:26
