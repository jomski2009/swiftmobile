-- phpMyAdmin SQL Dump
-- version 3.2.4
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Dec 13, 2013 at 04:00 PM
-- Server version: 5.1.44
-- PHP Version: 5.3.1

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `smsflow`
--

-- --------------------------------------------------------

--
-- Table structure for table `accounts`
--

CREATE TABLE IF NOT EXISTS `accounts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `creationdate` date NOT NULL,
  `balance` double NOT NULL DEFAULT '0',
  `smsvalue` double NOT NULL DEFAULT '0',
  `usersusername` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKaccounts272098` (`user_id`),
  KEY `user_accounts` (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=18 ;

-- --------------------------------------------------------

--
-- Table structure for table `deliveryreports`
--

CREATE TABLE IF NOT EXISTS `deliveryreports` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `messageid` varchar(255) DEFAULT NULL,
  `sentdate` date NOT NULL,
  `donedate` date NOT NULL,
  `status` varchar(100) NOT NULL,
  `gsmerror` int(4) NOT NULL,
  `smsid` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `sms_deliveryreports` (`smsid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `groups`
--

CREATE TABLE IF NOT EXISTS `groups` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `ownerid` int(11) NOT NULL,
  `creationdate` date NOT NULL,
  `usersusername` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgroups362439` (`ownerid`),
  KEY `user_groups` (`ownerid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

-- --------------------------------------------------------

--
-- Table structure for table `groupsms`
--

CREATE TABLE IF NOT EXISTS `groupsms` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `smsid` bigint(20) NOT NULL,
  `groupsid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `sms_groupsms` (`smsid`),
  KEY `groups_groupsms` (`groupsid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `groups_recipients`
--

CREATE TABLE IF NOT EXISTS `groups_recipients` (
  `groupsid` int(11) NOT NULL,
  `recipientsid` int(11) NOT NULL,
  PRIMARY KEY (`groupsid`,`recipientsid`),
  KEY `groups_groups_recipients` (`groupsid`),
  KEY `recipients_groups_recipients` (`recipientsid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `recipients`
--

CREATE TABLE IF NOT EXISTS `recipients` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cellnumber` bigint(13) NOT NULL,
  `dateadded` date NOT NULL,
  `firstvalue` varchar(255) DEFAULT NULL,
  `secondvalue` varchar(255) DEFAULT NULL,
  `thirdvalue` varchar(255) DEFAULT NULL,
  `fourthvalue` varchar(255) DEFAULT NULL,
  `fifthvalue` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `cellnumber` (`cellnumber`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

-- --------------------------------------------------------

--
-- Table structure for table `sms`
--

CREATE TABLE IF NOT EXISTS `sms` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `message` text NOT NULL,
  `messageid` varchar(100) NOT NULL,
  `type` varchar(20) DEFAULT NULL,
  `datesent` date NOT NULL,
  `userid` int(11) NOT NULL,
  `usersusername` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsms334642` (`userid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=18 ;

-- --------------------------------------------------------

--
-- Table structure for table `smsrecipients`
--

CREATE TABLE IF NOT EXISTS `smsrecipients` (
  `smsid` bigint(20) NOT NULL,
  `recipientsid` int(11) NOT NULL,
  PRIMARY KEY (`smsid`,`recipientsid`),
  KEY `sms_smsrecipients` (`smsid`),
  KEY `recipients_smsrecipients` (`recipientsid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `smsresponses`
--

CREATE TABLE IF NOT EXISTS `smsresponses` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `status` int(4) NOT NULL,
  `messageid` varchar(100) NOT NULL,
  `destination` bigint(13) NOT NULL,
  `smsid` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `sms_smsresponses` (`smsid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=15 ;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `firstname` varchar(255) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `cellnumber` bigint(20) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `creationdate` date NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=62 ;

-- --------------------------------------------------------

--
-- Table structure for table `user_roles`
--

CREATE TABLE IF NOT EXISTS `user_roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `authority` varchar(50) NOT NULL,
  `userid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKuser_roles297739` (`userid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `accounts`
--
ALTER TABLE `accounts`
  ADD CONSTRAINT `FKaccounts272098` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `user_accounts` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `deliveryreports`
--
ALTER TABLE `deliveryreports`
  ADD CONSTRAINT `sms_deliveryreports` FOREIGN KEY (`smsid`) REFERENCES `sms` (`id`);

--
-- Constraints for table `groups`
--
ALTER TABLE `groups`
  ADD CONSTRAINT `user_groups` FOREIGN KEY (`ownerid`) REFERENCES `users` (`id`);

--
-- Constraints for table `groupsms`
--
ALTER TABLE `groupsms`
  ADD CONSTRAINT `groups_groupsms` FOREIGN KEY (`groupsid`) REFERENCES `groups` (`id`),
  ADD CONSTRAINT `sms_groupsms` FOREIGN KEY (`smsid`) REFERENCES `sms` (`id`);

--
-- Constraints for table `groups_recipients`
--
ALTER TABLE `groups_recipients`
  ADD CONSTRAINT `groups_groups_recipients` FOREIGN KEY (`groupsid`) REFERENCES `groups` (`id`),
  ADD CONSTRAINT `recipients_groups_recipients` FOREIGN KEY (`recipientsid`) REFERENCES `recipients` (`id`);

--
-- Constraints for table `sms`
--
ALTER TABLE `sms`
  ADD CONSTRAINT `FKsms334642` FOREIGN KEY (`userid`) REFERENCES `users` (`id`);

--
-- Constraints for table `smsrecipients`
--
ALTER TABLE `smsrecipients`
  ADD CONSTRAINT `recipients_smsrecipients` FOREIGN KEY (`recipientsid`) REFERENCES `recipients` (`id`),
  ADD CONSTRAINT `sms_smsrecipients` FOREIGN KEY (`smsid`) REFERENCES `sms` (`id`);

--
-- Constraints for table `smsresponses`
--
ALTER TABLE `smsresponses`
  ADD CONSTRAINT `sms_smsresponses` FOREIGN KEY (`smsid`) REFERENCES `sms` (`id`);

--
-- Constraints for table `user_roles`
--
ALTER TABLE `user_roles`
  ADD CONSTRAINT `FKuser_roles297739` FOREIGN KEY (`userId`) REFERENCES `users` (`id`);
