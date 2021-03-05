-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 04-03-2021 a las 21:35:44
-- Versión del servidor: 10.4.13-MariaDB
-- Versión de PHP: 7.4.8

SET FOREIGN_KEY_CHECKS=0;
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `javatp`
--
CREATE DATABASE IF NOT EXISTS `javatp` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `javatp`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoria`
--
-- Creación: 04-03-2021 a las 20:22:47
-- Última actualización: 04-03-2021 a las 20:22:47
--

DROP TABLE IF EXISTS `categoria`;
CREATE TABLE `categoria` (
  `id` int(10) NOT NULL,
  `nombre` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `categoria`
--

INSERT INTO `categoria` (`id`, `nombre`) VALUES
(4, 'Auriculares'),
(1, 'Camaras'),
(2, 'Computadoras'),
(3, 'Discos duros'),
(5, 'Impresoras'),
(7, 'Mouses'),
(8, 'Notebooks'),
(6, 'Pantallas'),
(9, 'Sillas'),
(10, 'Teclados');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `compra`
--
-- Creación: 04-03-2021 a las 20:22:47
--

DROP TABLE IF EXISTS `compra`;
CREATE TABLE `compra` (
  `id` int(10) NOT NULL,
  `idUsuario` int(10) NOT NULL,
  `idDireccion` int(10) NOT NULL,
  `nroTarjeta` varchar(20) NOT NULL,
  `fecha` date NOT NULL,
  `hora` time NOT NULL,
  `importe` double NOT NULL,
  `fechaValuacion` date DEFAULT NULL,
  `horaValuacion` time DEFAULT NULL,
  `puntaje` int(1) DEFAULT NULL,
  `comentario` varchar(255) DEFAULT NULL,
  `estadoValuacion` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `direccion`
--
-- Creación: 04-03-2021 a las 20:22:46
--

DROP TABLE IF EXISTS `direccion`;
CREATE TABLE `direccion` (
  `id` int(10) NOT NULL,
  `provincia` varchar(30) NOT NULL,
  `localidad` varchar(30) NOT NULL,
  `direccion` varchar(30) NOT NULL,
  `piso` varchar(2) NOT NULL,
  `departamento` varchar(2) NOT NULL,
  `codigoAreaTelef` varchar(10) NOT NULL,
  `nroTelef` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--
-- Creación: 04-03-2021 a las 20:22:47
-- Última actualización: 04-03-2021 a las 20:23:43
--

DROP TABLE IF EXISTS `producto`;
CREATE TABLE `producto` (
  `id` int(10) NOT NULL,
  `descripcion` varchar(100) NOT NULL,
  `linkFoto` varchar(100) NOT NULL,
  `idCategoria` int(10) NOT NULL,
  `precio` double NOT NULL,
  `stock` int(10) NOT NULL,
  `bajaLogica` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`id`, `descripcion`, `linkFoto`, `idCategoria`, `precio`, `stock`, `bajaLogica`) VALUES
(1, 'Camara web Black model', 'Camara web Black model.jpg', 1, 800.3, 10, 1),
(2, 'Computador i3 7ma Gen', 'Computador i3 7ma Gen.jpg', 2, 52000, 3, 1),
(3, 'Disco duro 1Tb WD Blue', 'Disco duro 1Tb WD Blue.jpg', 3, 2699.59, 6, 1),
(4, 'Disco duro 512Mb', 'Disco duro 512Mb.jpg', 3, 1899.29, 9, 1),
(5, 'Headphones Urnaos', 'Headphones Urnaos.jpg', 4, 1030.99, 8, 1),
(6, 'Impresora multifunción HP', 'Impresora multifuncion HP.jpg', 5, 7886.38, 4, 1),
(7, 'LCD Samsung SyncMaster 510N', 'LCD Samsung SyncMaster 510N.jpg', 6, 13069, 3, 1),
(8, 'Mouse Adrus M2', 'Mouse Adrus M2.jpg', 7, 636, 0, 1),
(9, 'Mouse inalambrico Netmak', 'Mouse inalambrico Netmak.jpg', 7, 882.1, 11, 1),
(10, 'Notebook ASUS Grey Color', 'Notebook ASUS Grey Color.jpg', 8, 72500.99, 2, 1),
(11, 'Notebook CX', 'Notebook CX.jpg', 8, 66329, 3, 1),
(12, 'Silla - Black and blue', 'Silla - Black and blue.jpg', 9, 3010, 0, 1),
(13, 'Teclado Multicolor', 'Teclado Multicolor.jpg', 10, 1250.2, 8, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto_en_compra`
--
-- Creación: 04-03-2021 a las 20:22:47
--

DROP TABLE IF EXISTS `producto_en_compra`;
CREATE TABLE `producto_en_compra` (
  `id` int(10) NOT NULL,
  `idProducto` int(10) NOT NULL,
  `idCompra` int(10) NOT NULL,
  `precio` double NOT NULL,
  `cantidad` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tarjeta`
--
-- Creación: 04-03-2021 a las 20:22:46
--

DROP TABLE IF EXISTS `tarjeta`;
CREATE TABLE `tarjeta` (
  `numero` varchar(20) NOT NULL,
  `entidadEmisora` varchar(50) NOT NULL,
  `mesVencimiento` int(2) NOT NULL,
  `anioVencimiento` int(4) NOT NULL,
  `dniTitular` varchar(8) NOT NULL,
  `nyaTitular` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--
-- Creación: 04-03-2021 a las 20:22:46
-- Última actualización: 04-03-2021 a las 20:22:46
--

DROP TABLE IF EXISTS `usuario`;
CREATE TABLE `usuario` (
  `id` int(10) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `apellido` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(10) NOT NULL,
  `rol` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id`, `nombre`, `apellido`, `email`, `password`, `rol`) VALUES
(1, 'German', 'Rodriguez', 'gr@gmail.com', 'qwe123', 'admin'),
(2, 'Juan', 'Perez', 'jp@gmail.com', 'asd123', 'cliente');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `categoria`
--
ALTER TABLE `categoria`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nombre` (`nombre`);

--
-- Indices de la tabla `compra`
--
ALTER TABLE `compra`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idUsuario` (`idUsuario`),
  ADD KEY `idDireccion` (`idDireccion`),
  ADD KEY `nroTarjeta` (`nroTarjeta`);

--
-- Indices de la tabla `direccion`
--
ALTER TABLE `direccion`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `descripcion` (`descripcion`),
  ADD KEY `idCategoria` (`idCategoria`);

--
-- Indices de la tabla `producto_en_compra`
--
ALTER TABLE `producto_en_compra`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idProducto` (`idProducto`),
  ADD KEY `idCompra` (`idCompra`);

--
-- Indices de la tabla `tarjeta`
--
ALTER TABLE `tarjeta`
  ADD PRIMARY KEY (`numero`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `categoria`
--
ALTER TABLE `categoria`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `compra`
--
ALTER TABLE `compra`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `direccion`
--
ALTER TABLE `direccion`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `producto`
--
ALTER TABLE `producto`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT de la tabla `producto_en_compra`
--
ALTER TABLE `producto_en_compra`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `compra`
--
ALTER TABLE `compra`
  ADD CONSTRAINT `compra_ibfk_1` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`id`),
  ADD CONSTRAINT `compra_ibfk_2` FOREIGN KEY (`idDireccion`) REFERENCES `direccion` (`id`),
  ADD CONSTRAINT `compra_ibfk_3` FOREIGN KEY (`nroTarjeta`) REFERENCES `tarjeta` (`numero`);

--
-- Filtros para la tabla `producto`
--
ALTER TABLE `producto`
  ADD CONSTRAINT `producto_ibfk_1` FOREIGN KEY (`idCategoria`) REFERENCES `categoria` (`id`);

--
-- Filtros para la tabla `producto_en_compra`
--
ALTER TABLE `producto_en_compra`
  ADD CONSTRAINT `producto_en_compra_ibfk_1` FOREIGN KEY (`idProducto`) REFERENCES `producto` (`id`),
  ADD CONSTRAINT `producto_en_compra_ibfk_2` FOREIGN KEY (`idCompra`) REFERENCES `compra` (`id`);
SET FOREIGN_KEY_CHECKS=1;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
