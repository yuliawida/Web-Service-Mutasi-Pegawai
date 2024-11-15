-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 15, 2024 at 06:49 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `mutationdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `approval_letter`
--

CREATE TABLE `approval_letter` (
  `id` bigint(20) NOT NULL,
  `approval_number` varchar(255) DEFAULT NULL,
  `letter_content` varchar(255) DEFAULT NULL,
  `mutation_request_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `approval_letter`
--

INSERT INTO `approval_letter` (`id`, `approval_number`, `letter_content`, `mutation_request_id`, `user_id`) VALUES
(9, '2024/BPS/11', 'Surat Persetujuan Mutasi Pegawai a.n Samsul Harsono', 3, 2),
(10, '2024/BPS/25', 'Surat Persetujuan Mutasi Pegawai a.n Yulia Wida', 2, 1);

-- --------------------------------------------------------

--
-- Table structure for table `mutation_request`
--

CREATE TABLE `mutation_request` (
  `id` bigint(20) NOT NULL,
  `jabatan_tujuan` varchar(255) DEFAULT NULL,
  `kabupaten_tujuan` varchar(255) DEFAULT NULL,
  `provinsi_tujuan` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `unit_kerja_tujuan` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `mutation_request`
--

INSERT INTO `mutation_request` (`id`, `jabatan_tujuan`, `kabupaten_tujuan`, `provinsi_tujuan`, `status`, `unit_kerja_tujuan`, `user_id`) VALUES
(2, 'Pranata Komputer', 'Sragen', 'Jawa Tengah', 'approved\r\n', 'BPS Sragen', 1),
(3, 'Pranata Komputer', 'Surabaya', 'Jawa Timur', 'approved', 'BPS Surabaya', 2),
(4, 'Pranata Komputer', 'Lampung', 'Lampung', 'approved', 'BPS Lampung', 1);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `jabatan` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `nip` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` enum('ADMIN','USER') DEFAULT NULL,
  `unit_kerja` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `jabatan`, `name`, `nip`, `password`, `role`, `unit_kerja`, `username`) VALUES
(1, 'Pranata Komputer', 'Yulia Wida', '222212926', '$2a$10$hJIqeGFpZ1odelwaJLtDZel1p1pK/LVwxiehAk9XILXjaQ8mWm2S6', 'USER', 'BPS Prov Jawa Tengah', 'wida'),
(2, 'Pranata Komputer', 'Samsul Harsono', '222212900', '$2a$10$3XrFnqZ5x8fiVLO89al0M.tXvE0dKjKNIFBA6r1VjzSIMclKFlBGS', 'ADMIN', 'BPS Sragen', 'samsul'),
(12, 'Pranata Komputer', 'Yanto Salim', '222212654', '$2a$10$wDz/z/ivkAt/aWmpTrwa9.LMclSm8sK9BgMB.zgYnJ6vBgo.qf8ii', 'ADMIN', 'BPS Malang', 'yanto');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `approval_letter`
--
ALTER TABLE `approval_letter`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKbfo5n79yqllfasq00m5v4bnq2` (`mutation_request_id`),
  ADD KEY `FK8yvqjf76356vuhjhdux0lxrrj` (`user_id`);

--
-- Indexes for table `mutation_request`
--
ALTER TABLE `mutation_request`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK25tus7232d9kus5d6t6oramrp` (`user_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `approval_letter`
--
ALTER TABLE `approval_letter`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `mutation_request`
--
ALTER TABLE `mutation_request`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `approval_letter`
--
ALTER TABLE `approval_letter`
  ADD CONSTRAINT `FK8yvqjf76356vuhjhdux0lxrrj` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FKbfo5n79yqllfasq00m5v4bnq2` FOREIGN KEY (`mutation_request_id`) REFERENCES `mutation_request` (`id`);

--
-- Constraints for table `mutation_request`
--
ALTER TABLE `mutation_request`
  ADD CONSTRAINT `FK25tus7232d9kus5d6t6oramrp` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
