-- phpMyAdmin SQL Dump
-- version 4.6.1
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 29-05-2021 a las 13:35:11
-- Versión del servidor: 5.6.47
-- Versión de PHP: 5.6.40

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `lavin`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `idCliente` int(3) NOT NULL,
  `nomCliente` varchar(70) NOT NULL,
  `ape1Cliente` varchar(70) NOT NULL,
  `mail` varchar(70) NOT NULL,
  `contrasena` varchar(70) NOT NULL,
  `contrasenaAdm` varchar(70) NOT NULL DEFAULT '$2y$10$wL5tKmn.cOyMrnyVzjEVVO61H50l3dJGBAc3SGAB1CyI.p3lGI57K'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`idCliente`, `nomCliente`, `ape1Cliente`, `mail`, `contrasena`, `contrasenaAdm`) VALUES
(1, 'Lulu', 'lala', 'kk@kk.es', '$2y$10$H1p2I8DZn44rQxuI6PJP.OOfgqArMAjBoac/XFCN3JLd0ptfLhYDi', '$2y$10$wL5tKmn.cOyMrnyVzjEVVO61H50l3dJGBAc3SGAB1CyI.p3lGI57K'),
(2, 'Amaia', 'Pascual', 'amaya.pascual@gmail.com', '$2y$10$wL5tKmn.cOyMrnyVzjEVVO61H50l3dJGBAc3SGAB1CyI.p3lGI57K', '$2y$10$wL5tKmn.cOyMrnyVzjEVVO61H50l3dJGBAc3SGAB1CyI.p3lGI57K'),
(17, 'Ana', 'Ana', 'aa@aa.es', '$2y$10$lha7BjMT7rzX0m7l2DzHNeTKIZsVLb7NjA3hOX79ZqoKZAi8BNEwW', '$2y$10$wL5tKmn.cOyMrnyVzjEVVO61H50l3dJGBAc3SGAB1CyI.p3lGI57K'),
(18, 'Bego', 'Bego', 'bb@bb.es', '$2y$10$faMD9EcWjZ1PlhoqB95n2uMwjw290mt9/PumSe5iD4FJa9Fe8CZBy', '$2y$10$wL5tKmn.cOyMrnyVzjEVVO61H50l3dJGBAc3SGAB1CyI.p3lGI57K'),
(19, 'Carmen', 'Calvo', 'cc@cc.es', '$2y$10$q.LnPo4y9w/s4rdlrxeQOu3.W7yddUDBBJmSn8COcb5NNzsjqvvTi', '$2y$10$wL5tKmn.cOyMrnyVzjEVVO61H50l3dJGBAc3SGAB1CyI.p3lGI57K'),
(20, 'Dalia', 'Diaz', 'dd@dd.es', '$2y$10$aBlelzJctJPRewtt1uv9RuSBou25VL.X3zzB4xhJ/03DlJ5MVMxpC', '$2y$10$wL5tKmn.cOyMrnyVzjEVVO61H50l3dJGBAc3SGAB1CyI.p3lGI57K'),
(21, 'Iñaki', 'Lavin', 'inaki@gmail.com', '$2y$10$w/25fhFzBFJ9V9jL2jPRDur7aIYllbhkEPLw6fjhxprONmF/nEOwe', '$2y$10$wL5tKmn.cOyMrnyVzjEVVO61H50l3dJGBAc3SGAB1CyI.p3lGI57K'),
(22, 'Carmen', 'Calvo', 'cc@cc.es', '$2y$10$q.LnPo4y9w/s4rdlrxeQOu3.W7yddUDBBJmSn8COcb5NNzsjqvvTi', '$2y$10$wL5tKmn.cOyMrnyVzjEVVO61H50l3dJGBAc3SGAB1CyI.p3lGI57K'),
(23, 'Lulu', 'lala', 'kk@kk.es', '$2y$10$H1p2I8DZn44rQxuI6PJP.OOfgqArMAjBoac/XFCN3JLd0ptfLhYDi', '$2y$10$wL5tKmn.cOyMrnyVzjEVVO61H50l3dJGBAc3SGAB1CyI.p3lGI57K'),
(24, 'zz', 'zz', 'zz@zz.es', '$2y$10$Z50tdHvq01/BYz.VDnJQXuoop4cWIZHFlS7KmvycNgWAsyoGRiQky', '$2y$10$wL5tKmn.cOyMrnyVzjEVVO61H50l3dJGBAc3SGAB1CyI.p3lGI57K'),
(25, 'pp', 'pp', 'pp@pp.es', '$2y$10$U4x5Ks9JyGcV3LzUB9sNHumAQImCQCsMF2e4DTY/taRvQ3kWqJO6C', '$2y$10$wL5tKmn.cOyMrnyVzjEVVO61H50l3dJGBAc3SGAB1CyI.p3lGI57K'),
(26, 'qq', 'qq', 'qq@qq.es', '$2y$10$zxIfAuiU5mJPN.SiCeB6/uLeNvxQZS.gX9G8WvZgAEnYH8as9E1R2', '$2y$10$wL5tKmn.cOyMrnyVzjEVVO61H50l3dJGBAc3SGAB1CyI.p3lGI57K'),
(27, 'ss', 'ss', 'ss@ss.es', '$2y$10$tJvNNy1RviD2HTB/lzcaau1MuK1/zRzpkbGctItpgElJKL4zSZwR.', '$2y$10$wL5tKmn.cOyMrnyVzjEVVO61H50l3dJGBAc3SGAB1CyI.p3lGI57K'),
(28, 'ff', 'ff', 'ff@ff.es', '$2y$10$I3V1lOurbWtmWQ.ySRMkF.IbQ8EXjzPV0ciJXU3ZRq.XQa1AKx5si', '$2y$10$wL5tKmn.cOyMrnyVzjEVVO61H50l3dJGBAc3SGAB1CyI.p3lGI57K'),
(29, 'gugugu', 'gsgsgg', 'gg@gg.es', '$2y$10$7C2dTPHNiTKBhHzWIdqMC.m5oTMKgehzhqhPZ2gSJqiFP8ZSYVla.', '$2y$10$wL5tKmn.cOyMrnyVzjEVVO61H50l3dJGBAc3SGAB1CyI.p3lGI57K'),
(30, 'ff', 'ff', 'ff@ff.es', '$2y$10$GoIeD0j6tzx3uAgG4Zfu1uqlrq0sGYEAv4EpnlaRhgmSDUw1CXQCa', '$2y$10$wL5tKmn.cOyMrnyVzjEVVO61H50l3dJGBAc3SGAB1CyI.p3lGI57K'),
(31, 'ffff', 'ffff', 'ffff@ff.es', '$2y$10$ZlRFe9fqbDGnqPJzGaKqfu5BoF9167hxunmtPI.mvb/ujyIBY/BLK', '$2y$10$wL5tKmn.cOyMrnyVzjEVVO61H50l3dJGBAc3SGAB1CyI.p3lGI57K'),
(32, 'xx', 'xx', 'xx@xx.es', '$2y$10$B34auqO/rruVW.3H8Vje6uS7AYFzLc3bZaYopTjHmGp1gKDhxaEte', '$2y$10$wL5tKmn.cOyMrnyVzjEVVO61H50l3dJGBAc3SGAB1CyI.p3lGI57K'),
(33, 'ss', 'ss', 'ss@ss.es', '$2y$10$JD8pUeu51Z2rDQbIiPAJp.358unRLPa/yu9BC3CDr8II.V9X6TaxW', '$2y$10$wL5tKmn.cOyMrnyVzjEVVO61H50l3dJGBAc3SGAB1CyI.p3lGI57K'),
(34, 'ana', 'ana', 'ana@ana.es', '$2y$10$nTcHOqBrtu4NlWdNIqLaqen42zCCMCb3LYzaNYMiAn3OVIx1z/xfS', '$2y$10$wL5tKmn.cOyMrnyVzjEVVO61H50l3dJGBAc3SGAB1CyI.p3lGI57K'),
(35, 'martin', 'martin', 'martin@martin.es', '$2y$10$ICILJtN7A1Yzj0CCEL90.upfMoSbdehXRRUKUYH4VZmF.DX5/pvkW', '$2y$10$wL5tKmn.cOyMrnyVzjEVVO61H50l3dJGBAc3SGAB1CyI.p3lGI57K'),
(36, 'lola', 'lola', 'lola@lola.es', '$2y$10$KghDlZ1YWEOq3gEBF4kQpuvCFsog.fRQfWAAJLnEe1bMPNshdoC8y', '$2y$10$wL5tKmn.cOyMrnyVzjEVVO61H50l3dJGBAc3SGAB1CyI.p3lGI57K'),
(37, 'Ignacio', 'Lavín', 'ignacio@ignacio.es', '$2y$10$sxq.dY0eynlYwc63uhjqGeuQipMlFcVbtujkdYtV2zstvW.XEmSsO', '$2y$10$wL5tKmn.cOyMrnyVzjEVVO61H50l3dJGBAc3SGAB1CyI.p3lGI57K'),
(38, 'angeles', 'angeles', 'angeles@angeles.es', '$2y$10$JDxmnh450HwOVj71Vb4q1.1DVqbZrSxutFI82WEF.bqhtRGaw7rqO', '$2y$10$wL5tKmn.cOyMrnyVzjEVVO61H50l3dJGBAc3SGAB1CyI.p3lGI57K');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `lotes`
--

CREATE TABLE `lotes` (
  `idLote` int(11) NOT NULL,
  `refLote` int(11) NOT NULL,
  `descripcion` varchar(250) NOT NULL,
  `salida` double NOT NULL,
  `imgLote` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `lotes`
--

INSERT INTO `lotes` (`idLote`, `refLote`, `descripcion`, `salida`, `imgLote`) VALUES
(1, 1, 'Lote de 10 piezas de Cobre. Grecia, Ibérico (5), Romano (3) y Bizancio. Muy comercial. BC+ a MBC-.', 90, '93001'),
(2, 2, 'Lote de 11 piezas de Cobre. Ibérico, Romano y Bizantino. Interesante para estudio. BC a BC+.', 35, '93002'),
(3, 3, 'Arsaos. As. Cu. Ab-145. Vill-5. Pátina verde. MBC/MBC+.', 150, '93003'),
(4, 4, 'Cástulo. Semis. Cu. Ab-728. 13,34 gr. MBC-.', 30, '93004');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`idCliente`);

--
-- Indices de la tabla `lotes`
--
ALTER TABLE `lotes`
  ADD PRIMARY KEY (`idLote`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `clientes`
--
ALTER TABLE `clientes`
  MODIFY `idCliente` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;
--
-- AUTO_INCREMENT de la tabla `lotes`
--
ALTER TABLE `lotes`
  MODIFY `idLote` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
