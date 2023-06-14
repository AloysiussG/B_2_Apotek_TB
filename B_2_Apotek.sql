-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 34.101.141.200
-- Generation Time: Jun 13, 2023 at 05:00 PM
-- Server version: 8.0.26-google
-- PHP Version: 7.3.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `apotek`
--

-- --------------------------------------------------------

--
-- Table structure for table `obat`
--

CREATE TABLE `obat` (
  `idObat` int NOT NULL,
  `namaObat` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `tanggalKadaluarsa` date NOT NULL,
  `harga` double NOT NULL,
  `tanggalProduksi` date NOT NULL,
  `kuantitas` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `obat`
--

INSERT INTO `obat` (`idObat`, `namaObat`, `tanggalKadaluarsa`, `harga`, `tanggalProduksi`, `kuantitas`) VALUES
(1, 'Panadol', '2023-06-09', 15000, '2023-06-02', 6),
(2, 'Tolak Angin', '2023-06-30', 5000, '2023-06-02', 25),
(3, 'Betadin', '2023-06-22', 10000, '2023-06-01', 48),
(4, 'Combantrin', '2023-06-30', 15000, '2023-06-16', 38),
(5, 'Paracetamol', '2023-06-30', 14000, '2023-06-04', 18);

-- --------------------------------------------------------

--
-- Table structure for table `pengadaanobat`
--

CREATE TABLE `pengadaanobat` (
  `idPengadaan` int NOT NULL,
  `nip` int NOT NULL,
  `idObat` int NOT NULL,
  `kuantitas` int NOT NULL,
  `supplier` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `tanggalPengadaan` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pengadaanobat`
--

INSERT INTO `pengadaanobat` (`idPengadaan`, `nip`, `idObat`, `kuantitas`, `supplier`, `tanggalPengadaan`) VALUES
(1, 2, 1, 10, 'Kimia Farma', '2023-06-13'),
(2, 2, 2, 10, 'Kimia Farma', '2023-06-13'),
(3, 2, 3, 10, 'Kimia Farma', '2023-06-13'),
(4, 2, 4, 10, 'K24', '2023-06-13'),
(5, 2, 5, 10, 'Guardian', '2023-06-13');

-- --------------------------------------------------------

--
-- Table structure for table `pengguna`
--

CREATE TABLE `pengguna` (
  `idPengguna` int NOT NULL,
  `idUser` int NOT NULL,
  `nama` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `noTelp` varchar(15) COLLATE utf8mb4_general_ci NOT NULL,
  `alamat` varchar(254) COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pengguna`
--

INSERT INTO `pengguna` (`idPengguna`, `idUser`, `nama`, `noTelp`, `alamat`) VALUES
(-1, -1, 'Pembeli Offline', '7777777', 'Apotek Terdekat'),
(4, 4, 'Aloysius Seto', '081327326666', 'Jl. Kali Urang'),
(5, 5, 'Nicolas', '081327326688', 'Jl. Mandiri'),
(7, 7, 'Rayzel hiu', '081327322222', 'Jl. UGM'),
(8, 8, 'Joseph', '082327438889', 'Jl. Madura'),
(9, 9, 'Klasik', '084657897765', 'Jl. Hayat');

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
  `idRole` int NOT NULL,
  `gaji` double NOT NULL,
  `namaRole` varchar(255) COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`idRole`, `gaji`, `namaRole`) VALUES
(1, 1000000, 'Apoteker'),
(2, 2000000, 'Kepala Gudang'),
(3, 3000000, 'Kasir');

-- --------------------------------------------------------

--
-- Table structure for table `staff`
--

CREATE TABLE `staff` (
  `nip` int NOT NULL,
  `idUser` int NOT NULL,
  `idRole` int NOT NULL,
  `nama` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `tahunMasuk` date NOT NULL,
  `noTelp` varchar(15) COLLATE utf8mb4_general_ci NOT NULL,
  `alamat` varchar(254) COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `staff`
--

INSERT INTO `staff` (`nip`, `idUser`, `idRole`, `nama`, `tahunMasuk`, `noTelp`, `alamat`) VALUES
(-1, -1, 3, 'Self-Service', '2023-06-01', '7777777', 'Online'),
(1, 1, 1, 'Samuel', '2023-06-02', '081327322213', 'Jl. Kledokan'),
(2, 2, 2, 'Gregory Wilson', '2023-06-13', '081327322222', 'Jl. AtmaJa'),
(3, 3, 3, 'William Ongky', '2023-06-13', '081327322277', 'Jl. Jogis'),
(4, 11, 1, 'Ivan', '2023-06-13', '087563431229', 'Jl. Maguwo'),
(5, 10, 2, 'Matthew', '2023-06-13', '081346758876', 'Jl. Gondur');

-- --------------------------------------------------------

--
-- Table structure for table `transaksi`
--

CREATE TABLE `transaksi` (
  `idTransaksi` int NOT NULL,
  `nip` int DEFAULT NULL,
  `idObat` int NOT NULL,
  `idPengguna` int DEFAULT NULL,
  `tanggalPembelian` date NOT NULL,
  `jumlah` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `transaksi`
--

INSERT INTO `transaksi` (`idTransaksi`, `nip`, `idObat`, `idPengguna`, `tanggalPembelian`, `jumlah`) VALUES
(2, 3, 1, -1, '2023-06-13', 2),
(3, 3, 2, -1, '2023-06-07', 2),
(4, 3, 3, -1, '2023-06-15', 2),
(5, 3, 4, -1, '2023-06-15', 2),
(6, 3, 5, -1, '2023-06-17', 2),
(7, -1, 2, 7, '2023-06-13', 3);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `idUser` int NOT NULL,
  `username` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`idUser`, `username`, `password`) VALUES
(-1, 'Master', 'Master'),
(1, 'samuel', 'samuel'),
(2, 'gregory', 'gregory'),
(3, 'ongky', 'ongky'),
(4, 'seto', 'seto'),
(5, 'nicolas', 'nicolas'),
(7, 'rayzel', 'rayzel'),
(8, 'joseph', 'joseph'),
(9, 'klasik', 'klasik'),
(10, 'matthew', 'matthew'),
(11, 'ivan', 'ivan');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `obat`
--
ALTER TABLE `obat`
  ADD PRIMARY KEY (`idObat`);

--
-- Indexes for table `pengadaanobat`
--
ALTER TABLE `pengadaanobat`
  ADD PRIMARY KEY (`idPengadaan`),
  ADD KEY `idObat` (`idObat`),
  ADD KEY `nip` (`nip`);

--
-- Indexes for table `pengguna`
--
ALTER TABLE `pengguna`
  ADD PRIMARY KEY (`idPengguna`),
  ADD KEY `idUser` (`idUser`);

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`idRole`);

--
-- Indexes for table `staff`
--
ALTER TABLE `staff`
  ADD PRIMARY KEY (`nip`),
  ADD KEY `idUser` (`idUser`),
  ADD KEY `idRole` (`idRole`);

--
-- Indexes for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD PRIMARY KEY (`idTransaksi`),
  ADD KEY `nip` (`nip`),
  ADD KEY `idObat` (`idObat`),
  ADD KEY `idPengguna` (`idPengguna`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`idUser`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `obat`
--
ALTER TABLE `obat`
  MODIFY `idObat` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `pengadaanobat`
--
ALTER TABLE `pengadaanobat`
  MODIFY `idPengadaan` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `pengguna`
--
ALTER TABLE `pengguna`
  MODIFY `idPengguna` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `role`
--
ALTER TABLE `role`
  MODIFY `idRole` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `staff`
--
ALTER TABLE `staff`
  MODIFY `nip` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `transaksi`
--
ALTER TABLE `transaksi`
  MODIFY `idTransaksi` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `idUser` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `pengadaanobat`
--
ALTER TABLE `pengadaanobat`
  ADD CONSTRAINT `pengadaanobat_ibfk_1` FOREIGN KEY (`idObat`) REFERENCES `obat` (`idObat`),
  ADD CONSTRAINT `pengadaanobat_ibfk_2` FOREIGN KEY (`nip`) REFERENCES `staff` (`nip`);

--
-- Constraints for table `pengguna`
--
ALTER TABLE `pengguna`
  ADD CONSTRAINT `pengguna_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `user` (`idUser`);

--
-- Constraints for table `staff`
--
ALTER TABLE `staff`
  ADD CONSTRAINT `staff_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `user` (`idUser`),
  ADD CONSTRAINT `staff_ibfk_2` FOREIGN KEY (`idRole`) REFERENCES `role` (`idRole`);

--
-- Constraints for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD CONSTRAINT `transaksi_ibfk_1` FOREIGN KEY (`idObat`) REFERENCES `obat` (`idObat`),
  ADD CONSTRAINT `transaksi_ibfk_2` FOREIGN KEY (`idPengguna`) REFERENCES `pengguna` (`idPengguna`),
  ADD CONSTRAINT `transaksi_ibfk_3` FOREIGN KEY (`nip`) REFERENCES `staff` (`nip`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
