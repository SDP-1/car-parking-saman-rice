-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: parkingsystem
-- ------------------------------------------------------
-- Server version	8.0.26

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
-- Table structure for table `delivery`
--

DROP TABLE IF EXISTS `delivery`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `delivery` (
  `vehicleNumber` varchar(15) DEFAULT NULL,
  `vehicleType` varchar(20) DEFAULT NULL,
  `driverName` varchar(25) DEFAULT NULL,
  `leftTime` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `delivery`
--

LOCK TABLES `delivery` WRITE;
/*!40000 ALTER TABLE `delivery` DISABLE KEYS */;
INSERT INTO `delivery` VALUES ('NA-3434','Bus','Sumith Kumara','2022/02/09 	 10:32 PM');
/*!40000 ALTER TABLE `delivery` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `deliveryvehicle`
--

DROP TABLE IF EXISTS `deliveryvehicle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `deliveryvehicle` (
  `number` varchar(20) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  `maximumWeight` int DEFAULT NULL,
  `numberOfPassengers` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `deliveryvehicle`
--

LOCK TABLES `deliveryvehicle` WRITE;
/*!40000 ALTER TABLE `deliveryvehicle` DISABLE KEYS */;
INSERT INTO `deliveryvehicle` VALUES ('NA-3434','Bus',3500,60),('KA-4563','Van',1000,7),('58-3567','Van',1500,4),('GF-4358','Van',800,4),('CCB-3568','Van',1800,8),('LM-6679','Van',1500,4),('QA-3369','Van',1800,6),('KB-3668','Cargo Lorry',2500,2),('GH-5772','Cargo Lorry',4000,3),('XY-4456','Cargo Lorry',3500,2),('YQ-3536','Cargo Lorry',2000,2),('CBB-3566','Cargo Lorry',2500,2),('QH-3444','Cargo Lorry',5000,4),('JJ-9878','Cargo Lorry',3000,2);
/*!40000 ALTER TABLE `deliveryvehicle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `driver`
--

DROP TABLE IF EXISTS `driver`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `driver` (
  `name` varchar(50) NOT NULL,
  `NIC` varchar(15) NOT NULL,
  `drivingLicenseNo` varchar(15) NOT NULL,
  `address` text,
  `contact` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`NIC`),
  UNIQUE KEY `Driver_NIC_uindex` (`NIC`),
  UNIQUE KEY `Driver_drivingLicenseNo_uindex` (`drivingLicenseNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `driver`
--

LOCK TABLES `driver` WRITE;
/*!40000 ALTER TABLE `driver` DISABLE KEYS */;
INSERT INTO `driver` VALUES ('Sumith Kumara','7835348345V','B6474845','Panadura','0725637456'),('Sumith Udayanga','8735354355V','B3573783','Galle','0703635442'),('Jithmal Perera','8826253734V','B3674589','Horana','0772452457'),('Sumanasiri Herath','8976544373V','B3537538','Beruwala','0772534436'),('Dinesh Udara','9026344373V','B5343783','Hettimulla','0713456878'),('Mohommad Riaz','9124537733V','B3638537','Kaluthara','0777544222'),('Priyanga Perera','9135343537V','B3853753','Matara','0723344562'),('Chethiya Dilan','9162353436V','B6836836','Panadura','0772436737'),('Awantha Fernando','9173537839V','B3554789','Colombo 5','0714534356'),('Dushantha Perera','9255556343V','B3334435','Matara','0777245343'),('Amila Pathirana','9283289272V','B3354674','Galle','0717573583'),('Prashan Dineth','9362426738V','B2683536','Wadduwa','0715353434'),('Sumith Dissanayaka','9425245373V','B8366399','Kaluthara','0782686390'),('Sandun Kumara','9563524267V','B2263333','Panadura','0772325544'),('Charith Sudara','9573536833V','B6835836','Baththaramulla','0771536662'),('Udana Chathuranga','9692653338V','B7888632','Kottawa','0772442444');
/*!40000 ALTER TABLE `driver` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parkingandondeliverysummary`
--

DROP TABLE IF EXISTS `parkingandondeliverysummary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `parkingandondeliverysummary` (
  `vehicleNumber` varchar(15) DEFAULT NULL,
  `vehicalType` varchar(15) DEFAULT NULL,
  `parkingSlot` varchar(10) DEFAULT NULL,
  `driverName` varchar(30) DEFAULT NULL,
  `parkTime` varchar(50) DEFAULT NULL,
  `driverName2` varchar(30) DEFAULT '-',
  `delivaryTime` varchar(50) DEFAULT '-'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parkingandondeliverysummary`
--

LOCK TABLES `parkingandondeliverysummary` WRITE;
/*!40000 ALTER TABLE `parkingandondeliverysummary` DISABLE KEYS */;
INSERT INTO `parkingandondeliverysummary` VALUES ('NA-3434','Bus','14','Sumith Kumara','2022/02/01 	 05:04  PM','Sumith Kumara','2022/02/01 	 05:06 PM'),('NA-3434','Bus','14','Sumith Kumara','2022/02/01 	 05:07  PM','Sumith Kumara','2022/02/09 	 10:32 PM');
/*!40000 ALTER TABLE `parkingandondeliverysummary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parkingspase`
--

DROP TABLE IF EXISTS `parkingspase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `parkingspase` (
  `vehicleNumber` varchar(15) NOT NULL,
  `Type` varchar(15) NOT NULL,
  `parkSlot` int NOT NULL,
  `parkingTime` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parkingspase`
--

LOCK TABLES `parkingspase` WRITE;
/*!40000 ALTER TABLE `parkingspase` DISABLE KEYS */;
/*!40000 ALTER TABLE `parkingspase` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `rowNumber` int NOT NULL,
  `userName` varchar(25) NOT NULL,
  `password` varchar(25) NOT NULL,
  PRIMARY KEY (`userName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'sehan','12345');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-03-08 15:19:45
