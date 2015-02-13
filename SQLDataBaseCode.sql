-- phpMyAdmin SQL Dump
-- version 4.1.6
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Feb 13, 2015 at 11:44 AM
-- Server version: 5.6.16
-- PHP Version: 5.5.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `parkingsystem`
--

-- --------------------------------------------------------

--
-- Table structure for table `subscribers`
--

CREATE TABLE IF NOT EXISTS `subscribers` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `FirstName` varchar(50) NOT NULL,
  `LastName` varchar(50) NOT NULL,
  `carID` varchar(15) NOT NULL,
  `accountNumber` varchar(20) DEFAULT NULL,
  `balance` decimal(6,2) DEFAULT '0.00',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `FirstName` (`FirstName`),
  UNIQUE KEY `LastName` (`LastName`),
  UNIQUE KEY `carID` (`carID`),
  UNIQUE KEY `ID` (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=288 ;

-- --------------------------------------------------------

--
-- Table structure for table `traffic`
--

CREATE TABLE IF NOT EXISTS `traffic` (
  `CarID` varchar(15) NOT NULL,
  `DateIn` varchar(20) DEFAULT NULL,
  `DateOut` varchar(20) DEFAULT NULL,
  `balance` double(5,2) DEFAULT '0.00',
  `hasPaid` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

