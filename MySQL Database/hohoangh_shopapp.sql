-- phpMyAdmin SQL Dump
-- version 3.5.8.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Oct 04, 2015 at 06:00 AM
-- Server version: 5.6.12
-- PHP Version: 5.3.27

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+07:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `hohoangh_shopapp`
--

-- --------------------------------------------------------

--
-- Table structure for table `accounts`
--

CREATE TABLE IF NOT EXISTS `accounts` (
  `UserID` varchar(8) COLLATE utf8_unicode_ci NOT NULL,
  `Username` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `Password` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `FirstName` text COLLATE utf8_unicode_ci NOT NULL,
  `LastName` text COLLATE utf8_unicode_ci NOT NULL,
  `Address` text COLLATE utf8_unicode_ci NOT NULL,
  `District` text COLLATE utf8_unicode_ci NOT NULL,
  `City` text COLLATE utf8_unicode_ci NOT NULL,
  `Phone` varchar(11) COLLATE utf8_unicode_ci NOT NULL,
  `Email` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `RegID` text COLLATE utf8_unicode_ci,
  PRIMARY KEY (`UserID`),
  UNIQUE KEY `Username_UNIQUE` (`Username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Các tài khoản để đăng nhập';

--
-- Dumping data for table `accounts`
--

INSERT INTO `accounts` (`UserID`, `Username`, `Password`, `FirstName`, `LastName`, `Address`, `District`, `City`, `Phone`, `Email`, `RegID`) VALUES
('44F8LUW3', 'hanglekhanh', 'khanh111', 'Khánh', 'Hàng', '57 abc', '6', 'Hồ Chí Minh', '123456789', 'hanglekhanh169@gmail.com', 'APA91bGGpLj5c3MqC5ziJEQo6jY6JAhCYjAl4dBIklLnjVXxqo6zFxvHO-bsHMY6VHNjzsRTb9Xe8J_w7uuqws8dKhldcwkpbOWbF-pGvpoPEfdDxK9HQgY'),
('4R2Q2Q1R', 'ngochuyen', '123456', 'Huyền', 'Phan', '123', 'Thủ Đức', 'Hồ Chí Minh', '0993322372', 'ngochuyen0151@gmail.com', 'aaa'),
('AX10BYN8', 'hhhai0304', '03041994', 'Hải', 'Hồ Hoàng', '3/5 Nguyễn Trãi', '5', 'Hồ Chí Minh', '01299030494', 'hhhai0304@gmail.com', 'APA91bFHpAJdj1aGPvMVXj7qyQsjvh1aAQpNCC8peOIAI6KgYOMtStY9F3i2mTqcbF25iwJ37GSdfi0uhkTctdImYb2WZirS-EDx-wuYOJk9Hh_pSEDbevY'),
('B2YM5XLB', 'nploc0203', '02031994', 'Lộc', 'Nguyễn', '46/4 khu phố 3, phường Linh Xuân', 'Thủ đức', 'Hồ Chí Minh', '0993322276', 'nploc0203@gmail.com', 'APA91bEJkcrP3Hn_2TlpAEXnRzDndARu3jvayn8S8hrxIpRLl8lEa61aWumKHGhpwuEBiMbiff1q68R52bb2OK06_dps2UEkLzzU00l09hX8sID6AHZWlCo'),
('MUINKT92', 'LaiKỳAnh', '987654321', 'Anh', 'Lai', '671/23A Nguyễn Trãi F11 ', '5', 'Hồ Chí Minh', '01866000382', 'laikyanh28@gmail.com', 'APA91bF5P8D1HT1qVUnbLeSDxyuk-r4FTOjtHdGvSYjp4hTqoX3GFLxwbTUZ4jGcOSdkOEQohOLvJ1jMYEqzTWD_izPrrN7HNZCWGOwG2X116PcgUuMNWcA');

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE IF NOT EXISTS `category` (
  `CategoryID` varchar(8) COLLATE utf8_unicode_ci NOT NULL,
  `Name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `Detail` text COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`CategoryID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Chứa các danh mục phân loại';

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`CategoryID`, `Name`, `Detail`) VALUES
('CA01', 'Phần Ăn Combo', 'Những gói Combo hấp dẫn'),
('CA02', 'Gà Rán - Gà Quay', 'Gà các loại'),
('CA03', 'Burger - Cơm', 'Buger và Cơm'),
('CA04', 'Thức Ăn Nhẹ', 'Gà lẻ - Khoai - Xà lách'),
('CA05', 'Tráng Miệng', 'Thức uống - Kem');

--
-- Triggers `category`
--
DROP TRIGGER IF EXISTS `update_category_forinsert`;
DELIMITER //
CREATE TRIGGER `update_category_forinsert` AFTER INSERT ON `category`
 FOR EACH ROW BEGIN
INSERT INTO `dbversion`(UpdateTime) VALUES (now());
END
//
DELIMITER ;
DROP TRIGGER IF EXISTS `update_category_forupdate`;
DELIMITER //
CREATE TRIGGER `update_category_forupdate` AFTER UPDATE ON `category`
 FOR EACH ROW BEGIN
INSERT INTO `dbversion`(UpdateTime) VALUES (now());
END
//
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `dbversion`
--

CREATE TABLE IF NOT EXISTS `dbversion` (
  `Version` int(11) NOT NULL AUTO_INCREMENT,
  `UpdateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`Version`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=64 ;

--
-- Dumping data for table `dbversion`
--

INSERT INTO `dbversion` (`Version`, `UpdateTime`) VALUES
(62, '2015-08-29 19:42:07'),
(63, '2015-09-08 20:46:52');

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE IF NOT EXISTS `orders` (
  `OrderID` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `UserID` varchar(8) COLLATE utf8_unicode_ci NOT NULL,
  `OrderDate` datetime NOT NULL COMMENT 'Ngày lập hóa đơn',
  `Address` longtext COLLATE utf8_unicode_ci NOT NULL COMMENT 'Địa chỉ giao hàng',
  `Phone` varchar(11) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Số điện thoại người đặt hàng',
  `Price` decimal(10,0) NOT NULL COMMENT 'Tổng giá tiền đơn hàng',
  `OrderStatus` text COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`OrderID`),
  KEY `user_id_idx` (`UserID`),
  KEY `user_order_idx` (`UserID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Dữ liệu về các đơn hàng';

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`OrderID`, `UserID`, `OrderDate`, `Address`, `Phone`, `Price`, `OrderStatus`) VALUES
('796IEZ1XR3', 'AX10BYN8', '2015-09-30 14:52:28', '3/5 Nguyễn Trãi, Quận 5, Hồ Chí Minh', '01299030494', 150000, '2'),
('VDNW7IJJ2E', 'MUINKT92', '2015-10-02 22:06:34', '671/23A Nguyễn Trãi F11 , Quận 5, Hồ Chí Minh', '01866000382', 84000, '2');

-- --------------------------------------------------------

--
-- Table structure for table `orders_detail`
--

CREATE TABLE IF NOT EXISTS `orders_detail` (
  `DetailID` int(11) NOT NULL AUTO_INCREMENT,
  `OrderID` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `ProductID` varchar(8) COLLATE utf8_unicode_ci NOT NULL COMMENT 'ID của sản phẩm',
  `Quantity` int(11) NOT NULL COMMENT 'Số lượng',
  `Note` text COLLATE utf8_unicode_ci COMMENT 'Ghi chú thêm cho sản phẩm',
  PRIMARY KEY (`DetailID`),
  KEY `OrderID_idx` (`OrderID`),
  KEY `ProductID_idx` (`ProductID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Chi tiết đơn hàng' AUTO_INCREMENT=47 ;

--
-- Dumping data for table `orders_detail`
--

INSERT INTO `orders_detail` (`DetailID`, `OrderID`, `ProductID`, `Quantity`, `Note`) VALUES
(41, '796IEZ1XR3', 'CA030007', 2, 'không cay'),
(42, '796IEZ1XR3', 'CA050006', 4, ''),
(45, 'VDNW7IJJ2E', 'CA030006', 1, ''),
(46, 'VDNW7IJJ2E', 'CA050005', 1, '');

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE IF NOT EXISTS `products` (
  `ProductID` varchar(8) COLLATE utf8_unicode_ci NOT NULL,
  `ProductName` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Tên sản phẩm',
  `Detail` text COLLATE utf8_unicode_ci COMMENT 'Thông tin chi tiết của sản phẩm',
  `Price` int(11) NOT NULL COMMENT 'Giá tiền mỗi sản phẩm',
  `Avatar` text COLLATE utf8_unicode_ci COMMENT 'Ảnh đại diện cho sản phẩm',
  `CategoryID` varchar(8) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`ProductID`),
  KEY `CategoryID_idx` (`CategoryID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Danh sách các sản phẩm được bán';

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`ProductID`, `ProductName`, `Detail`, `Price`, `Avatar`, `CategoryID`) VALUES
('CA010001', 'Gà Rán Giòn Cay', '- 2 miếng Gà Rán Giòn Cay\\n- 1 khoai tây chiên (vừa)\\n- 1 Pepsi (vừa)', 79000, 'CA010001', 'CA01'),
('CA010002', 'Gà Rán Truyền Thống', '- 2 miếng Gà Rán Truyền Thống\\n- 1 khoai tây nghiền (vừa)\\n- 1 bắp cải trộn (vừa)\\n- 1 Pepsi (vừa)', 79000, 'CA010002', 'CA01'),
('CA010003', 'Phần Ăn Tiết Kiệm', '- 1 miếng Gà Rán Truyền Thống hoặc Gà Giòn Cay\\n- 1 khoai tây chiên (vừa)\\n- 1 Pepsi (vừa)', 49000, 'CA010003', 'CA01'),
('CA010004', 'Phần Ăn Trẻ Em', '- 1 Gà Rán Truyền Thống HOẶC 1 Gà Popcorn HOẶC 2 Giòn Không Xương HOẶC 1 Go-Go Cá\\n- 1 khoai tây chiên Chicky\\n- 1 Pepsi (vừa)\\n- 1 quà tặng', 59000, 'CA010004', 'CA01'),
('CA010005', 'Phần Ăn XL', '- 1 miếng Gà Giòn Cay\\n- 1 bơ-gơ Zinger\\n- 1 khoai tây chiên (vừa)\\n- 1 bắp cải trộn (vừa)\\n- 1 Pepsi (vừa)', 104000, 'CA010005', 'CA01'),
('CA010006', 'Phần Ăn Gia Đình A', '- 6 miếng Gà Truyền Thống\\n- 1 khoai tây chiên (đại)\\n- 1 khoai tây nghiền (đại)\\n- 3 Pepsi (vừa)', 242000, 'CA010006', 'CA01'),
('CA010007', 'Phần Ăn Gia Đình B', '- 4 miếng Gà Truyền Thống\\n- 1 khoai tây chiên (vừa)\\n- 1 khoai tây nghiền (vừa)\\n- 1 bắp cải trộn (vừa)\\n- 2 Pepsi (vừa)', 158000, 'CA010007', 'CA01'),
('CA010008', 'Gà Quay Tiêu - Gà Big''n Juicy', '- 1 miếng Gà Quay Tiêu HOẶC Gà Big''n Juicy\\n- 1 cơm HOẶC khoai tây chiên (vừa)\\n- 1 Pepsi (vừa)', 79000, 'CA010008', 'CA01'),
('CA020001', 'Gà Truyền Thống - Gà Giòn Cay (1 Miếng)', '1 miếng', 34000, 'CA020001', 'CA02'),
('CA020002', 'Gà Truyền Thống - Gà Giòn Cay (3 Miếng)', '3 miếng', 95000, 'CA020002', 'CA02'),
('CA020003', 'Gà Truyền Thống - Gà Giòn Cay (6 Miếng)', '6 miếng', 184000, 'CA020003', 'CA02'),
('CA020004', 'Gà Truyền Thống - Gà Giòn Cay (9 Miếng)', '9 miếng', 275000, 'CA020004', 'CA02'),
('CA020005', 'Cánh Gà Giòn Cay (3 Miếng)', '3 miếng', 47000, 'CA020005', 'CA02'),
('CA020006', 'Cánh Gà Giòn Cay (5 Miếng)', '5 miếng', 69000, 'CA020006', 'CA02'),
('CA020007', 'Gà Quay Tiêu', '2 phần', 64000, 'CA020007', 'CA02'),
('CA020008', 'Gà Quay Giấy Bạc', '2 phần', 64000, 'CA020008', 'CA02'),
('CA030001', 'Cơm Vi Vu - Phi Lê Gà Quay Tiêu', '1 phần', 39000, 'CA030001', 'CA03'),
('CA030002', 'Cơm Vi Vu - Phi Lê Gà Quay Flaya', '1 phần', 39000, 'CA030002', 'CA03'),
('CA030003', 'Cơm Vi Vu - Gà Giòn Không Xương', '1 phần', 39000, 'CA030003', 'CA03'),
('CA030004', 'Cơm Vi Vu - Gà POPCORN', '1 phần', 39000, 'CA030004', 'CA03'),
('CA030005', 'Bơ-Gơ OCEAN', '1 cái', 22000, 'CA030005', 'CA03'),
('CA030006', 'Bơ-Gơ Tôm', '1 cái', 39000, 'CA030006', 'CA03'),
('CA030007', 'Bơ-Gơ Gà Quay Flava', '1 cái', 45000, 'CA030007', 'CA03'),
('CA030008', 'Bơ-Gơ Zinger', '1 cái', 49000, 'CA030008', 'CA03'),
('CA030009', 'Cơm Gà Truyền Thống KFC', '1 phần', 39000, 'CA030009', 'CA03'),
('CA030010', 'Cơm Phi-Lê Gà Quay Flava', '1 phần', 39000, 'CA030010', 'CA03'),
('CA030011', 'Cơm Đùi Gà Quay Tiêu', '1 phần', 39000, 'CA030011', 'CA03'),
('CA040001', 'Gà Giòn Không Xương (3 Miếng)', '3 miếng', 40000, 'CA040001', 'CA04'),
('CA040002', 'Gà Giòn Không Xương (5 Miếng)', '5 miếng', 61000, 'CA040002', 'CA04'),
('CA040003', 'Gà Viên (Vừa)', 'Vừa', 40000, 'CA040003', 'CA04'),
('CA040004', 'Gà Viên (Lớn)', 'Lớn', 61000, 'CA040004', 'CA04'),
('CA040005', 'Cá Thanh', '3 miếng', 40000, 'CA040005', 'CA04'),
('CA040006', 'Khoai Tây Chiên (Vừa)', '1 phần', 12000, 'CA040006', 'CA04'),
('CA040007', 'Khoai Tây Chiên (Lớn)', '1 phần', 25000, 'CA040007', 'CA04'),
('CA040008', 'Khoai Tây Chiên (Đại)', '1 phần', 35000, 'CA040008', 'CA04'),
('CA040009', 'Khoai Tây Nghiền (Vừa)', '1 phần', 10000, 'CA040009', 'CA04'),
('CA040010', 'Khoai Tây Nghiền (Lớn)', '1 phần', 20000, 'CA040010', 'CA04'),
('CA040011', 'Khoai Tây Nghiền (Đại)', '1 phần', 30000, 'CA040011', 'CA04'),
('CA040012', 'Bắp Cải Trộn (Vừa)', '1 phần', 12000, 'CA040012', 'CA04'),
('CA040013', 'Bắp Cải Trộn (Lớn)', '1 phần', 22000, 'CA040013', 'CA04'),
('CA040014', 'Bắp Cải Trộn (Đại)', '1 phần', 30000, 'CA040014', 'CA04'),
('CA040015', 'Xà Lách', '1 phần', 29000, 'CA040015', 'CA04'),
('CA050001', 'Kem Sundae KFC', '1 cái', 15000, 'CA050001', 'CA05'),
('CA050002', 'Kem Ốc Quế', '1 cây', 3000, 'CA050002', 'CA05'),
('CA050003', 'Kem Phủ Sô-Cô-La', '1 cây', 5000, 'CA050003', 'CA05'),
('CA050004', 'Bánh Trứng Nướng (1 Cái)', '1 cái', 15000, 'CA050004', 'CA05'),
('CA050005', 'Bánh Trứng Nướng (4 Cái)', '4 cái', 45000, 'CA050005', 'CA05'),
('CA050006', 'Aquafina', '500ml', 15000, 'CA050006', 'CA05'),
('CA050007', 'Nước Pepsi (Lớn)', '1 ly', 17000, 'CA050007', 'CA05'),
('CA050008', 'Bánh Nhân Mứt Táo - Khoai Môn', '1 cái', 17000, 'CA050008', 'CA05'),
('CA050009', 'Milo (Vừa)', '1 ly', 20000, 'CA050009', 'CA05'),
('CA050010', 'Diet Pepsi (Lon)', '350ml', 20000, 'CA050010', 'CA05'),
('CA050011', 'Twister (Lon)', '330ml', 20000, 'CA050011', 'CA05'),
('CA050012', 'Nước Pepsi (Vừa)', '1 ly', 10000, 'CA050012', 'CA05');

--
-- Triggers `products`
--
DROP TRIGGER IF EXISTS `update_version_forinsert`;
DELIMITER //
CREATE TRIGGER `update_version_forinsert` AFTER INSERT ON `products`
 FOR EACH ROW BEGIN
INSERT INTO `dbversion`(UpdateTime) VALUES (now());
END
//
DELIMITER ;
DROP TRIGGER IF EXISTS `update_version_forupdate`;
DELIMITER //
CREATE TRIGGER `update_version_forupdate` AFTER UPDATE ON `products`
 FOR EACH ROW BEGIN
INSERT INTO `dbversion`(UpdateTime) VALUES (now());
END
//
DELIMITER ;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `user_order` FOREIGN KEY (`UserID`) REFERENCES `accounts` (`UserID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `orders_detail`
--
ALTER TABLE `orders_detail`
  ADD CONSTRAINT `detail_order` FOREIGN KEY (`OrderID`) REFERENCES `orders` (`OrderID`) ON DELETE CASCADE ON UPDATE NO ACTION,
  ADD CONSTRAINT `product_order` FOREIGN KEY (`ProductID`) REFERENCES `products` (`ProductID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `category` FOREIGN KEY (`CategoryID`) REFERENCES `category` (`CategoryID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
