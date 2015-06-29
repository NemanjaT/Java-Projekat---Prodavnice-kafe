-- phpMyAdmin SQL Dump
-- version 4.3.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: May 30, 2015 at 07:48 PM
-- Server version: 5.6.24
-- PHP Version: 5.6.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `nrt_112_13_baza`
--

-- --------------------------------------------------------

--
-- Table structure for table `artikli`
--

CREATE TABLE IF NOT EXISTS `artikli` (
  `barkod` bigint(20) NOT NULL,
  `naziv` varchar(32) NOT NULL,
  `cena` double NOT NULL,
  `popust` double NOT NULL DEFAULT '0',
  `kategorija` enum('crna','espreso','kapucino','nes') NOT NULL DEFAULT 'crna',
  `kolicina` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `artikli`
--

INSERT INTO `artikli` (`barkod`, `naziv`, `cena`, `popust`, `kategorija`, `kolicina`) VALUES
(10000000, 'Bonito Black', 220, 0, 'crna', 123),
(10000001, 'Donkafa', 200, 0, 'crna', 48),
(10000002, 'LuCaffee', 350, 0, 'espreso', 47),
(10000003, 'DonKafe Nes', 25, 0, 'nes', 332),
(10000004, 'Bonsuite', 250, 0, 'kapucino', 30),
(10000005, 'Bonito', 240, 0, 'crna', 80),
(10000006, 'Ilikop', 370, 0, 'espreso', 41),
(10000007, 'Mexikano Nes', 50, 0, 'nes', 135),
(10000008, 'Mexikano', 260, 0, 'crna', 16),
(10000009, 'Pera Kafe', 290, 0, 'espreso', 70),
(10000010, 'Pera Black', 190, 5, 'crna', 5),
(10000011, 'C Kafa Black', 210, 0, 'crna', 52),
(10000012, 'C Kafa Grand', 200, 0, 'crna', 83),
(10000013, 'Grand Kafa', 240, 0, 'crna', 76),
(10000014, 'Grand Nes', 18, 0, 'nes', 230),
(10000015, 'Grand Black', 205, 0, 'crna', 10),
(10000016, 'Konkito', 390, 0, 'espreso', 66),
(10000017, 'Sjera', 72, 0, 'kapucino', 81),
(10000018, 'DonSpreso', 460, 0, 'espreso', 15),
(10000019, 'ProlNes', 16, 0, 'nes', 860),
(10000020, 'Tozino', 200, 0, 'crna', 100),
(10000021, 'Special Nes', 20, 0, 'crna', 150),
(10000022, 'Special Espreso', 400, 0, 'espreso', 150),
(10000023, 'Special Crna', 200, 0, 'crna', 143),
(10000024, 'Special Kapucino', 80, 0, 'crna', 50),
(10000025, 'Java', 450, 0, 'espreso', 22),
(10000026, 'JelBlack', 214, 0, 'crna', 27),
(10000027, 'AndjeSpreso', 413, 0, 'espreso', 92),
(10000028, 'Lumila', 90, 0, 'kapucino', 60),
(10000029, 'Yugo', 260, 0, 'crna', 114),
(10000030, 'JavaLio', 205, 10, 'crna', 45),
(10000031, 'AndjKafe', 30, 1, 'nes', 260),
(10000032, 'Grandisimo', 430, 20, 'espreso', 50);

-- --------------------------------------------------------

--
-- Table structure for table `korisnici`
--

CREATE TABLE IF NOT EXISTS `korisnici` (
  `id` int(11) NOT NULL,
  `korisnicko_ime` varchar(16) NOT NULL,
  `lozinka` varchar(16) NOT NULL,
  `ime_prezime` varchar(32) NOT NULL,
  `plata` double NOT NULL DEFAULT '23000',
  `slika` text,
  `posao` enum('snabdevac','prodavac','menadžer') NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `korisnici`
--

INSERT INTO `korisnici` (`id`, `korisnicko_ime`, `lozinka`, `ime_prezime`, `plata`, `slika`, `posao`) VALUES
(1, 'perica', 'lozinka', 'Perica Štrbac', 23000, 'images/perica_strbac.jpg', 'menadžer'),
(2, 'andj', 'lozinka', 'Anđelković Miloš', 23000, 'images/andjelkovic_milos.jpg', 'prodavac'),
(4, 'jeca', 'lozinka', 'Jelena Metodijević', 24500, NULL, 'snabdevac'),
(7, 'caric', 'lozinka', 'Marko Caric', 24600, NULL, 'prodavac'),
(8, 'toza', 'lozinka', 'Nemanja Tozic', 27000, NULL, 'snabdevac');

-- --------------------------------------------------------

--
-- Table structure for table `prodaja`
--

CREATE TABLE IF NOT EXISTS `prodaja` (
  `id` int(11) NOT NULL,
  `vreme_prodaje` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `id_prodavca` int(11) NOT NULL,
  `suma_prodaje` double NOT NULL DEFAULT '0',
  `bonus_prodavca` double NOT NULL DEFAULT '0'
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `prodaja`
--

INSERT INTO `prodaja` (`id`, `vreme_prodaje`, `id_prodavca`, `suma_prodaje`, `bonus_prodavca`) VALUES
(1, '2015-05-18 21:49:34', 2, 8800, 0.1),
(2, '2015-05-18 21:51:55', 2, 1460, 0.05),
(3, '2015-05-18 22:04:56', 2, 1320, 0.05),
(4, '2015-05-18 22:17:17', 2, 200, 0.05),
(5, '2015-05-18 22:19:57', 2, 200, 0.05),
(6, '2015-05-18 22:21:31', 2, 220, 0.05),
(7, '2015-05-18 22:24:13', 2, 220, 0.05),
(8, '2015-05-18 22:43:08', 2, 1100, 0.05),
(9, '2015-05-19 10:48:27', 2, 2340, 0.05),
(10, '2015-05-20 10:55:34', 2, 3200, 0.05),
(11, '2015-05-20 10:57:10', 2, 6290, 0.1),
(12, '2015-05-20 11:00:29', 2, 2250, 0.05),
(13, '2015-05-20 11:05:49', 2, 2640, 0.05),
(14, '2015-05-20 11:09:29', 2, 13290, 0.2),
(15, '2015-05-20 11:10:41', 2, 1540, 0.05),
(16, '2015-05-20 11:11:49', 2, 9400, 0.1),
(17, '2015-05-20 16:57:10', 2, 1710, 0.05),
(18, '2015-05-20 16:59:48', 2, 950, 0.05),
(19, '2015-05-20 18:10:54', 2, 2000, 0.05);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `artikli`
--
ALTER TABLE `artikli`
  ADD PRIMARY KEY (`barkod`);

--
-- Indexes for table `korisnici`
--
ALTER TABLE `korisnici`
  ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `korisnicko_ime` (`korisnicko_ime`), ADD UNIQUE KEY `korisnicko_ime_2` (`korisnicko_ime`), ADD UNIQUE KEY `korisnicko_ime_3` (`korisnicko_ime`);

--
-- Indexes for table `prodaja`
--
ALTER TABLE `prodaja`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `korisnici`
--
ALTER TABLE `korisnici`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT for table `prodaja`
--
ALTER TABLE `prodaja`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=20;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
