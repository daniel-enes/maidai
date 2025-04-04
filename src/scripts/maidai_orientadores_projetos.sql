-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: maidai
-- ------------------------------------------------------
-- Server version	9.2.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `orientadores_projetos`
--

DROP TABLE IF EXISTS `orientadores_projetos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orientadores_projetos` (
  `orientadores_pessoas_id` int unsigned NOT NULL,
  `projetos_id` int unsigned NOT NULL,
  `coorientador` int unsigned DEFAULT NULL,
  PRIMARY KEY (`orientadores_pessoas_id`,`projetos_id`),
  KEY `fk_orientadores_has_projetos_projetos1_idx` (`projetos_id`),
  KEY `fk_orientadores_has_projetos_orientadores1_idx` (`orientadores_pessoas_id`),
  KEY `fk_orientadores_projetos_orientadores1_idx` (`coorientador`),
  CONSTRAINT `fk_orientadores_has_projetos_orientadores1` FOREIGN KEY (`orientadores_pessoas_id`) REFERENCES `orientadores` (`pessoas_id`),
  CONSTRAINT `fk_orientadores_has_projetos_projetos1` FOREIGN KEY (`projetos_id`) REFERENCES `projetos` (`id`),
  CONSTRAINT `fk_orientadores_projetos_orientadores1` FOREIGN KEY (`coorientador`) REFERENCES `orientadores` (`pessoas_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orientadores_projetos`
--

LOCK TABLES `orientadores_projetos` WRITE;
/*!40000 ALTER TABLE `orientadores_projetos` DISABLE KEYS */;
/*!40000 ALTER TABLE `orientadores_projetos` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-04 17:06:16
