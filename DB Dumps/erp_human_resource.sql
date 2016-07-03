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
INSERT INTO `human_resource` VALUES ('saram','pasham','javam','888','CONFIRMED','100824','High',1),('pardis','khanum','everything','123456','PENDING','128290','Low',0),('farbod','khubi','nothing','888','PENDING','157726','High',0),('salam','khubi','hyhk','123456','PENDING','220835','Low',0),('salam','na','hichi','888','PENDING','240331','High',0),('salam','khodafez','hh','hhh','PENDING','246005','High',0),('salam','khuni','na','888','PENDING','373666','High',0),('farbod','khubi','nothing','888','PENDING','393974','High',0),('sas','jkjk','lkjlkj','89098','PENDING','414400','High',0),('yes','yesy','yesy','888','PENDING','415083','High',0),('sssssssss','khodafez','hh','hhh','PENDING','433388','High',0),('pardis','pashakhanloo','everything','123456','PENDING','538217','Low',0),('chetori','ya','na','888','PENDING','556727','High',0),('salam','ss','ss','yyy','PENDING','619031','High',0),('na','na','na','888','PENDING','709604','High',0),('mozhi','gheini','all','888','PENDING','777007','High',0),('salam','lsal','skldja','kljdf','PENDING','778034','High',0),('slaam','khubi','na','12345','PENDING','783606','High',0),('slam','lals','adlksj','wejkf','PENDING','824871','High',0),('mozhi','gheini','all','888','PENDING','951087','High',0);
/*!40000 ALTER TABLE `human_resource` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-07-03 16:48:57
