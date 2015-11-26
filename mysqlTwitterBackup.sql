CREATE DATABASE  IF NOT EXISTS `TwitterBackup` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `TwitterBackup`;
-- MySQL dump 10.13  Distrib 5.6.27, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: TwitterBackup
-- ------------------------------------------------------
-- Server version	5.6.27-2

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
-- Table structure for table `DirectMesage`
--

DROP TABLE IF EXISTS `DirectMesage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DirectMesage` (
  `id` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `mesage` varchar(200) NOT NULL,
  `twitterUser` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DirectMesage`
--

LOCK TABLES `DirectMesage` WRITE;
/*!40000 ALTER TABLE `DirectMesage` DISABLE KEYS */;
/*!40000 ALTER TABLE `DirectMesage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Fav`
--

DROP TABLE IF EXISTS `Fav`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Fav` (
  `id` varchar(20) NOT NULL,
  `mesage` varchar(200) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `twitterUser` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Fav_1_idx` (`twitterUser`),
  CONSTRAINT `fk_Fav_1` FOREIGN KEY (`twitterUser`) REFERENCES `UserTwitter` (`twitterUser`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Fav`
--

--
-- Table structure for table `MyTweets`
--

DROP TABLE IF EXISTS `MyTweets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MyTweets` (
  `id` varchar(20) NOT NULL,
  `mesage` varchar(200) NOT NULL,
  `name` varchar(45) NOT NULL,
  `twitterUser` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MyTweets`
--

--
-- Table structure for table `Retweet`
--

DROP TABLE IF EXISTS `Retweet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Retweet` (
  `id` int(11) NOT NULL,
  `mesage` varchar(140) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `twitterUser` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Retweet_1_idx` (`twitterUser`),
  CONSTRAINT `twitterUser` FOREIGN KEY (`twitterUser`) REFERENCES `UserTwitter` (`twitterUser`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Retweet`
--

LOCK TABLES `Retweet` WRITE;
/*!40000 ALTER TABLE `Retweet` DISABLE KEYS */;
/*!40000 ALTER TABLE `Retweet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserLocal`
--

DROP TABLE IF EXISTS `UserLocal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserLocal` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` varchar(45) NOT NULL,
  `password` varchar(130) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserLocal`
--



--
-- Table structure for table `UserTwitter`
--

DROP TABLE IF EXISTS `UserTwitter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserTwitter` (
  `twitterUser` varchar(45) NOT NULL,
  `tokenSecret` varchar(60) DEFAULT NULL,
  `token` varchar(60) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  PRIMARY KEY (`twitterUser`),
  KEY `id_idx` (`userId`),
  CONSTRAINT `fk_UserTwitter_1` FOREIGN KEY (`userId`) REFERENCES `UserLocal` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserTwitter`
--


/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-11-23 19:13:01
