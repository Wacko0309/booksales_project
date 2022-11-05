CREATE TABLE IF NOT EXISTS `bookstore` (
  `ISBN` varchar(20) NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `author` varchar(20) DEFAULT NULL,
  `price` int DEFAULT NULL,
  `sales` int DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  `stock` int DEFAULT NULL,
  PRIMARY KEY (`ISBN`));