CREATE DATABASE `javatp`;
USE `javatp`;


DROP TABLE IF EXISTS `usuario`;
CREATE TABLE `usuario`(
	`id` 				int(10) 		NOT NULL AUTO_INCREMENT,
	`nombre` 			varchar(50) 	NOT NULL,
	`apellido`			varchar(50)		NOT NULL,
	`email`				varchar(50)		NOT NULL,
	`password` 			varchar(10) 	NOT NULL,
	`rol`				varchar(10)		NOT NULL,
	PRIMARY KEY (`id`),
	UNIQUE (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

INSERT INTO `usuario` VALUES 
(1	,'German'	,'Rodriguez'	,'gr@gmail.com'		,'qwe123'	,'admin'),
(2	,'Juan'		,'Perez'		,'jp@gmail.com'		,'asd123'	,'cliente');

DROP TABLE IF EXISTS `tarjeta`;
CREATE TABLE `tarjeta`(
	`numero`				varchar(20)		NOT NULL,
	`entidadEmisora`		varchar(50)		NOT NULL,
	`mesVencimiento`		int(2)			NOT NULL,
	`anioVencimiento`		int(4)			NOT NULL,
	`dniTitular`			varchar(8)		NOT NULL,
	`nyaTitular`			varchar(50)		NOT NULL,
	PRIMARY KEY (`numero`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `direccion`;
CREATE TABLE `direccion`(
	`id`				int(10)			NOT NULL AUTO_INCREMENT,
	`provincia`			varchar(30)		NOT NULL,
	`localidad`			varchar(30)		NOT NULL,
	`direccion`			varchar(30)		NOT NULL,
	`piso`				varchar(2)		NOT NULL,
	`departamento`		varchar(2)		NOT NULL,
	`codigoAreaTelef`	varchar(10)		NOT NULL,
	`nroTelef`			varchar(10)		NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `compra`;
CREATE TABLE `compra`(
	`id`				int(10)			NOT NULL AUTO_INCREMENT,
	`idUsuario`			int(10)			NOT NULL,
	`idDireccion`		int(10)			NOT NULL,
	`nroTarjeta`		varchar(20)		NOT NULL,
	`fecha`				date			NOT NULL,
	`hora`				time			NOT NULL,
	`importe`			double			NOT NULL,
	`fechaValuacion`	date					,
	`horaValuacion`		time					,
	`puntaje`			int(1)					,
	`comentario`		varchar(255)			,
	`estadoValuacion`	varchar(20)		NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`idUsuario`) REFERENCES `usuario`(`id`),
	FOREIGN KEY (`idDireccion`) REFERENCES `direccion`(`id`),
	FOREIGN KEY (`nroTarjeta`) REFERENCES `tarjeta`(`numero`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `categoria`;
CREATE TABLE `categoria`(
	`id`				int(10)			NOT NULL AUTO_INCREMENT,
	`nombre`			varchar(50) 	NOT NULL,
	PRIMARY KEY (`id`),
	UNIQUE (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;

INSERT INTO `categoria` VALUES 
(1	,'Camaras'),
(2	,'Computadoras'),
(3	,'Discos duros'),
(4	,'Auriculares'),
(5	,'Impresoras'),
(6	,'Pantallas'),
(7	,'Mouses'),
(8	,'Notebooks'),
(9	,'Sillas'),
(10	,'Teclados');


DROP TABLE IF EXISTS `producto`;
CREATE TABLE `producto`(
	`id`		 		int(10) 		NOT NULL AUTO_INCREMENT,
	`descripcion` 		varchar(100) 	NOT NULL,
	`linkFoto` 			varchar(100) 	NOT NULL,
	`idCategoria` 		int(10) 		NOT NULL,
	`precio` 			double 			NOT NULL,
	`stock` 			int(10) 		NOT NULL,
	`bajaLogica`		boolean			NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`idCategoria`) REFERENCES `categoria`(`id`),
	UNIQUE(`descripcion`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4;

INSERT INTO `producto` VALUES 
(1	,'Camara web Black model'		,'Camara web Black model.jpg'		,1	,800.30		,10	,true),
(2	,'Computador i3 7ma Gen'		,'Computador i3 7ma Gen.jpg'		,2	,52000.00	,3	,true),
(3	,'Disco duro 1Tb WD Blue'		,'Disco duro 1Tb WD Blue.jpg'		,3	,2699.59	,6	,true),
(4	,'Disco duro 512Mb'				,'Disco duro 512Mb.jpg'				,3	,1899.29	,9	,true),
(5	,'Headphones Urnaos'			,'Headphones Urnaos.jpg'			,4	,1030.99	,8	,true),
(6	,'Impresora multifunción HP'	,'Impresora multifuncion HP.jpg'	,5	,7886.38	,4	,true),
(7	,'LCD Samsung SyncMaster 510N'	,'LCD Samsung SyncMaster 510N.jpg'	,6	,13069.00	,3	,true),
(8	,'Mouse Adrus M2'				,'Mouse Adrus M2.jpg'				,7	,636.00		,0	,true),
(9	,'Mouse inalambrico Netmak'		,'Mouse inalambrico Netmak.jpg'		,7	,882.10		,11	,true),
(10 ,'Notebook ASUS Grey Color'		,'Notebook ASUS Grey Color.jpg'		,8	,72500.99	,2	,true),
(11 ,'Notebook CX'					,'Notebook CX.jpg'					,8	,66329.00	,3	,true),
(12 ,'Silla - Black and blue'		,'Silla - Black and blue.jpg'		,9	,3010.00	,0	,true),
(13 ,'Teclado Multicolor'			,'Teclado Multicolor.jpg'			,10	,1250.20	,8	,true);


DROP TABLE IF EXISTS `producto_en_compra`;
CREATE TABLE `producto_en_compra`(
	`id`				int(10)			NOT NULL AUTO_INCREMENT,
	`idProducto`		int(10)			NOT NULL,
	`idCompra`			int(10)			NOT NULL,
	`precio`			double			NOT NULL,
	`cantidad`			int(10)			NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`idProducto`) REFERENCES `producto`(`id`),
	FOREIGN KEY (`idCompra`) REFERENCES `compra`(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
