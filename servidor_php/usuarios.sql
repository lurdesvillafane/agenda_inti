-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 04-09-2026 a las 03:02:33
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `usuarios`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedidos`
--

CREATE TABLE `pedidos` (
  `id_pedido` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `titulo` varchar(100) NOT NULL,
  `descripcion` text DEFAULT NULL,
  `pago` varchar(100) DEFAULT NULL,
  `fecha_inicio` date NOT NULL,
  `fecha_entrega` date NOT NULL,
  `cliente` varchar(100) DEFAULT NULL,
  `categoria_color` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `pedidos`
--

INSERT INTO `pedidos` (`id_pedido`, `user_id`, `titulo`, `descripcion`, `pago`, `fecha_inicio`, `fecha_entrega`, `cliente`, `categoria_color`) VALUES
(5, 1, 'stickers', '30 stickers', 'pagado', '2026-09-01', '2026-09-02', 'Juan perez', 'Categoria 1'),
(6, 1, 'Cuaderno', 'Cuaderno a4 de 100 hojas', 'Pagado', '2026-09-04', '2026-09-07', 'Cristina yang', 'Categoria 4'),
(7, 1, 'Peluche', 'Peluche de skzoo', 'Pagado', '2026-09-02', '2026-09-04', 'Laura Marano', 'Categoria 2'),
(8, 1, 'Cuadro', 'Cuadro a4 de messi', 'Seña de 5000, faltan 5000', '2026-09-04', '2026-09-06', 'Lionel Messi', 'Categoria 2'),
(9, 1, 'Stickers taylor', '20 stickers de taylor swift holograficos', 'Seña de 10 mil, faltan 10 mil', '2026-09-04', '2026-09-08', 'Travis Kelce', 'Categoria 1'),
(10, 1, 'Cuaderno de guts', 'Cuaderno a5 de 100 h', 'Pagado', '2026-09-01', '2026-09-06', 'Olivia Rodrigo', 'Categoria 4'),
(11, 3, 'Stickers', '1 sticker', 'pagado', '2026-09-03', '2026-09-05', 'louis', 'Categoria 2');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user`
--

CREATE TABLE `user` (
  `user_id` int(10) NOT NULL,
  `name` varchar(20) NOT NULL,
  `email` varchar(224) NOT NULL,
  `user_name` varchar(15) NOT NULL,
  `password` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `user`
--

INSERT INTO `user` (`user_id`, `name`, `email`, `user_name`, `password`) VALUES
(1, 'lurdes', 'lurdes_anahi07@hotmail.com', 'lurdes111', 'lu1234'),
(2, 'Taylor', 'taylor@gmail.com', 'taylor_swift', 'tay123'),
(3, 'Olivia', 'olivia123@gmail.com', 'oli12', '1234');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `pedidos`
--
ALTER TABLE `pedidos`
  ADD PRIMARY KEY (`id_pedido`),
  ADD KEY `user_id` (`user_id`);

--
-- Indices de la tabla `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `pedidos`
--
ALTER TABLE `pedidos`
  MODIFY `id_pedido` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `user`
--
ALTER TABLE `user`
  MODIFY `user_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `pedidos`
--
ALTER TABLE `pedidos`
  ADD CONSTRAINT `pedidos_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
