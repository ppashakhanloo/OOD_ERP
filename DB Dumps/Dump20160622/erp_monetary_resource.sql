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
INSERT INTO `monetary_resource` VALUES ('NON_CASH',170,'RIAL','tehya',0,'241314'),('CASH',100000,'RIAL','Saman',1234567890,'266022'),('CASH',100000,'RIAL','Saman',1234567890,'267379'),('CASH',100000,'DOLLAR','Sasan',1234567890,'484850'),('CASH',100000,'RIAL','Sasan',1234567890,'620640'),('NON_CASH',10000,'DOLLAR','sdfgh',12,'754594'),('CASH',100000,'RIAL','Saman',1234567890,'796307'),('CASH',100000,'DOLLAR','Sasan',1234567890,'852934'),('NON_CASH',1,'RIAL','sdfg',234,'892247');
/*!40000 ALTER TABLE `monetary_resource` ENABLE KEYS */;
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
