-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 17, 2022 at 03:39 PM
-- Server version: 10.4.19-MariaDB
-- PHP Version: 8.0.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `jklaptop`
--

-- --------------------------------------------------------

--
-- Table structure for table `brand`
--

CREATE TABLE `brand` (
  `BrandID` char(5) NOT NULL,
  `BrandName` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `brand`
--

INSERT INTO `brand` (`BrandID`, `BrandName`) VALUES
('BD001', 'Asus'),
('BD002', 'Lenovo'),
('BD003', 'Dell'),
('BD004', 'Acer');

-- --------------------------------------------------------

--
-- Table structure for table `cart`
--

CREATE TABLE `cart` (
  `UserID` char(5) NOT NULL,
  `ProductID` char(5) NOT NULL,
  `Qty` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `detailtransaction`
--

CREATE TABLE `detailtransaction` (
  `TransactionID` char(5) NOT NULL,
  `ProductID` char(5) NOT NULL,
  `Qty` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `detailtransaction`
--

INSERT INTO `detailtransaction` (`TransactionID`, `ProductID`, `Qty`) VALUES
('TR015', 'PD811', 2),
('TR064', 'PD102', 1);

-- --------------------------------------------------------

--
-- Table structure for table `headertransaction`
--

CREATE TABLE `headertransaction` (
  `TransactionID` char(5) NOT NULL,
  `UserID` char(5) NOT NULL,
  `Date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `headertransaction`
--

INSERT INTO `headertransaction` (`TransactionID`, `UserID`, `Date`) VALUES
('TR015', 'US002', '2022-01-17'),
('TR064', 'US240', '2022-01-17');

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `ProductID` char(5) NOT NULL,
  `BrandID` char(5) NOT NULL,
  `ProductName` varchar(100) NOT NULL,
  `ProductPrice` int(11) NOT NULL,
  `ProductStock` int(11) NOT NULL,
  `ProductRating` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`ProductID`, `BrandID`, `ProductName`, `ProductPrice`, `ProductStock`, `ProductRating`) VALUES
('PD001', 'BD001', 'Vivobook', 12000000, 3, 6),
('PD002', 'BD003', 'Dell XPS 13', 15000000, 2, 9),
('PD102', 'BD004', 'Acer Aspire 5', 8500000, 4, 8),
('PD811', 'BD002', 'Lenovo X1000', 7500000, 1, 7);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `UserID` char(5) NOT NULL,
  `UserName` varchar(255) NOT NULL,
  `UserEmail` varchar(100) NOT NULL,
  `UserPassword` varchar(20) NOT NULL,
  `UserGender` varchar(20) NOT NULL,
  `UserAddress` varchar(255) NOT NULL,
  `UserRole` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`UserID`, `UserName`, `UserEmail`, `UserPassword`, `UserGender`, `UserAddress`, `UserRole`) VALUES
('US001', 'Lala', 'lala@gmail.com', '12aa', 'Female', 'Villa Street', 'Admin'),
('US002', 'Raka', 'raka@gmail.com', '22aa', 'Male', 'Kembangan Street', 'Member'),
('US240', 'Raiden', 'jojo@gmail.com', '11aa', 'Male', 'Jemari Street', 'Member');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `brand`
--
ALTER TABLE `brand`
  ADD PRIMARY KEY (`BrandID`);

--
-- Indexes for table `cart`
--
ALTER TABLE `cart`
  ADD PRIMARY KEY (`UserID`,`ProductID`),
  ADD KEY `ProductIDConstraint` (`ProductID`),
  ADD KEY `UserIDConstraint` (`UserID`);

--
-- Indexes for table `detailtransaction`
--
ALTER TABLE `detailtransaction`
  ADD PRIMARY KEY (`TransactionID`,`ProductID`),
  ADD KEY `ProductIDConstraint` (`ProductID`),
  ADD KEY `TransactionIDConstraint` (`TransactionID`);

--
-- Indexes for table `headertransaction`
--
ALTER TABLE `headertransaction`
  ADD PRIMARY KEY (`TransactionID`),
  ADD KEY `UserIDConstraint` (`UserID`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`ProductID`),
  ADD KEY `BrandIDConstraint` (`BrandID`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`UserID`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `cart`
--
ALTER TABLE `cart`
  ADD CONSTRAINT `ProductIDConstraint` FOREIGN KEY (`ProductID`) REFERENCES `product` (`ProductID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `UserIDConstraint` FOREIGN KEY (`UserID`) REFERENCES `user` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `detailtransaction`
--
ALTER TABLE `detailtransaction`
  ADD CONSTRAINT `ProductIDConstraint` FOREIGN KEY (`ProductID`) REFERENCES `product` (`ProductID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `TransactionIDConstraint` FOREIGN KEY (`TransactionID`) REFERENCES `headertransaction` (`TransactionID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `headertransaction`
--
ALTER TABLE `headertransaction`
  ADD CONSTRAINT `UserIDConstraint` FOREIGN KEY (`UserID`) REFERENCES `user` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `product`
--
ALTER TABLE `product`
  ADD CONSTRAINT `BrandIDConstraint` FOREIGN KEY (`BrandID`) REFERENCES `brand` (`BrandID`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
