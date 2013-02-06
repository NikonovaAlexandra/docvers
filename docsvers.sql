# Host     : localhost
# Port     : 3306
# Database : docsvers


SET FOREIGN_KEY_CHECKS=0;

CREATE DATABASE `docsvers`
    CHARACTER SET 'latin1'
    COLLATE 'latin1_swedish_ci';

USE `docsvers`;

#
# Structure for the `author` table : 
#

CREATE TABLE `author` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `login` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `name` (`login`),
  UNIQUE KEY `login` (`login`),
  UNIQUE KEY `login_2` (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Structure for the `version` table : 
#

CREATE TABLE `version` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Structure for the `document` table : 
#

CREATE TABLE `document` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `author_id` bigint(20) unsigned NOT NULL,
  `document_name` char(20) NOT NULL,
  `version_id` bigint(20) unsigned NOT NULL,
  `description` blob,
  `document_path` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `author_id` (`author_id`),
  KEY `version` (`version_id`),
  CONSTRAINT `author` FOREIGN KEY (`author_id`) REFERENCES `author` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `version` FOREIGN KEY (`version_id`) REFERENCES `version` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

