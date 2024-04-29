CREATE DATABASE  IF NOT EXISTS `taskManagement`;
USE `taskManagement`;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `userinfo`;

CREATE TABLE `userinfo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(200) not null unique,
  `password` varchar(200) DEFAULT NULL,
  `roles` varchar(200) DEFAULT 'ROLE_USER',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `task`;

CREATE TABLE `task` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
   `dead_line` date,
   `is_done` bool default false,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

ALTER TABLE userinfo
ADD COLUMN roles NVARCHAR(200) DEFAULT 'ROLE_USER';
