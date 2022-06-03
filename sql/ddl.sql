CREATE TABLE `account` (
  `accountId` int NOT NULL AUTO_INCREMENT,
  `accountName` varchar(45) NOT NULL,
  `balance` int DEFAULT '0',
  `accountType` int DEFAULT '1',
  PRIMARY KEY (`accountId`),
  UNIQUE KEY `accountName_UNIQUE` (`accountName`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci