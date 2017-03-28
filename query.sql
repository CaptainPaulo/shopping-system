# ************************************************************
# Sequel Pro SQL dump
# Version 4499
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.10)
# Database: jweb
# Generation Time: 2016-01-10 22:29:45 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table Member
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Member`;

CREATE TABLE `Member` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(20) NOT NULL,
  `lastName` varchar(20) DEFAULT NULL,
  `email` varchar(60) DEFAULT NULL,
  `newsletter` tinyint(1) DEFAULT NULL,
  `password` varchar(20) NOT NULL,
  `isAdmin` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

LOCK TABLES `Member` WRITE;
/*!40000 ALTER TABLE `Member` DISABLE KEYS */;

INSERT INTO `Member` (`id`, `firstName`, `lastName`, `email`, `newsletter`, `password`, `isAdmin`)
VALUES
	(1,'Foo','Foo','test@test.com',0,'test',0),
	(2,'Bar','Foo','foo@bar.com',1,'test',1);

/*!40000 ALTER TABLE `Member` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table News
# ------------------------------------------------------------

DROP TABLE IF EXISTS `News`;

CREATE TABLE `News` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `author` varchar(60) NOT NULL,
  `title` varchar(100) NOT NULL,
  `body` text NOT NULL,
  `publicationDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

LOCK TABLES `News` WRITE;
/*!40000 ALTER TABLE `News` DISABLE KEYS */;

INSERT INTO `News` (`id`, `author`, `title`, `body`, `publicationDate`)
VALUES
	(1,'Bar','Test','Test.','2016-01-10 18:44:22'),
	(2,'Bar','News 2','Another test.','2016-01-10 23:16:02');

/*!40000 ALTER TABLE `News` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Product
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Product`;

CREATE TABLE `Product` (
  `id` int(11) NOT NULL,
  `name` varchar(60) NOT NULL,
  `description` text,
  `price` float NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `Product` WRITE;
/*!40000 ALTER TABLE `Product` DISABLE KEYS */;

INSERT INTO `Product` (`id`, `name`, `description`, `price`)
VALUES
	(1,'Un produit trop cool','C\'est un produit supercool !',9999);

/*!40000 ALTER TABLE `Product` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Review
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Review`;

CREATE TABLE `Review` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `author` varchar(50) NOT NULL,
  `note` tinyint(4) NOT NULL,
  `description` text,
  `product_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

LOCK TABLES `Review` WRITE;
/*!40000 ALTER TABLE `Review` DISABLE KEYS */;

INSERT INTO `Review` (`id`, `author`, `note`, `description`, `product_id`)
VALUES
	(1,'Foo',4,'Bon produit !',1);

/*!40000 ALTER TABLE `Review` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
