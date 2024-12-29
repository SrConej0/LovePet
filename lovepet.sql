-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Tiempo de generación: 17-12-2024 a las 03:31:55
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `lovepet`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ingreso`
--

CREATE TABLE `ingreso` (
  `id` int(11) NOT NULL,
  `telefono` varchar(9) NOT NULL,
  `codigo` varchar(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `ingreso`
--

INSERT INTO `ingreso` (`id`, `telefono`, `codigo`) VALUES
(1, '912345678', '1234'),
(2, '923456789', '2345'),
(3, '934567890', '3456'),
(4, '945678901', '4567'),
(5, '956789012', '5678');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mascotas`
--

CREATE TABLE `mascotas` (
  `id` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `descripcion` text DEFAULT NULL,
  `foto` varchar(255) DEFAULT NULL,
  `edad` int(11) DEFAULT NULL,
  `tipo_id` varchar(3) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `mascotas`
--

INSERT INTO `mascotas` (`id`, `nombre`, `descripcion`, `foto`, `edad`, `tipo_id`) VALUES
(1, 'Max', 'Labrador juguetón y amigable con niños', 'max.jpg', 3, 'P01'),
(2, 'Luna', 'Pastor alemán leal y protectora', 'luna.jpg', 5, 'P01'),
(3, 'Rocky', 'Bulldog francés cariñoso y tranquilo', 'rocky.jpg', 2, 'P01'),
(4, 'Bella', 'Golden retriever energética y cariñosa', 'bella.jpg', 4, 'P01'),
(5, 'Charlie', 'Beagle curioso y aventurero', 'charlie.jpg', 1, 'P01'),
(6, 'Mía', 'Siamesa elegante y vocal', 'mia.jpg', 3, 'G02'),
(7, 'Simba', 'Gato naranja perezoso y mimoso', 'simba.jpg', 4, 'G02'),
(8, 'Nala', 'Gata negra misteriosa y independiente', 'nala.jpg', 2, 'G02'),
(9, 'Oliver', 'Gato atigrado juguetón y travieso', 'oliver.jpg', 1, 'G02'),
(10, 'Luz', 'Gata blanca tranquila y cariñosa', 'luz.jpg', 5, 'G02'),
(11, 'Blu', 'Periquito azul cantarín', 'blu.jpg', 2, 'A03'),
(12, 'Río', 'Canario amarillo de melodioso canto', 'rio.jpg', 1, 'A03'),
(13, 'Pepita', 'Agapornis verde muy sociable', 'pepita.jpg', 3, 'A03'),
(14, 'Coco', 'Cacatúa blanca inteligente y juguetona', 'coco.jpg', 5, 'A03'),
(15, 'Kiwi', 'Ninfas gris perla tranquila', 'kiwi.jpg', 4, 'A03'),
(16, 'Thumper', 'Conejo blanco de orejas caídas', 'thumper.jpg', 2, 'C04'),
(17, 'Oreo', 'Conejo holandés blanco y negro', 'oreo.jpg', 1, 'C04'),
(18, 'Pelusa', 'Conejo angora esponjoso', 'pelusa.jpg', 3, 'C04'),
(19, 'Zanahoria', 'Conejo mini rex naranja', 'zanahoria.jpg', 2, 'C04'),
(20, 'Cotton', 'Conejo enano muy juguetón', 'cotton.jpg', 1, 'C04'),
(21, 'Nemo', 'Pez payaso naranja y blanco', 'nemo.jpg', 1, 'P05'),
(22, 'Dory', 'Pez cirujano azul muy activo', 'dory.jpg', 2, 'P05'),
(23, 'Bubble', 'Pez betta de aletas largas', 'bubble.jpg', 1, 'P05'),
(24, 'Goldie', 'Pez dorado de cola de velo', 'goldie.jpg', 3, 'P05'),
(25, 'Fin', 'Guppy de colores brillantes', 'fin.jpg', 1, 'P05'),
(26, 'Peanut', 'Hámster sirio dorado', 'peanut.jpg', 1, 'H06'),
(27, 'Chock', 'Hámster ruso enano gris', 'chock.jpg', 2, 'H06'),
(28, 'Chip', 'Hámster chino de pelo largo', 'chip.jpg', 1, 'H06'),
(29, 'Nibbles', 'Hámster campbell de manchas blancas', 'nibbles.jpg', 1, 'H06'),
(30, 'Whiskers', 'Hámster roborovski muy rápido', 'whiskers.jpg', 2, 'H06'),
(31, 'Donatello', 'Tortuga de orejas rojas', 'donatello.jpg', 5, 'T07'),
(32, 'Shelly', 'Tortuga terrestre rusa', 'shelly.jpg', 7, 'T07'),
(33, 'Crush', 'Tortuga de caja oriental', 'crush.jpg', 10, 'T07'),
(34, 'Michelangelo', 'Tortuga leopardo', 'michelangelo.jpg', 3, 'T07'),
(35, 'Squirt', 'Tortuga mapa', 'squirt.jpg', 4, 'T07'),
(36, 'Polly', 'Loro gris africano muy inteligente', 'polly.jpg', 10, 'L08'),
(37, 'Tico', 'Guacamayo azul y amarillo', 'tico.jpg', 15, 'L08'),
(38, 'Kiko', 'Cacatúa de cresta amarilla', 'kiko.jpg', 8, 'L08'),
(39, 'Lola', 'Amazonas de frente azul', 'lola.jpg', 12, 'L08'),
(40, 'Pepe', 'Cotorra argentina muy habladora', 'pepe.jpg', 6, 'L08'),
(41, 'Bandit', 'Hurón albino juguetón', 'bandit.jpg', 3, 'H09'),
(42, 'Rascal', 'Hurón sable muy curioso', 'rascal.jpg', 2, 'H09'),
(43, 'Ferret', 'Hurón canela tranquilo', 'ferret.jpg', 4, 'H09'),
(44, 'Loki', 'Hurón panda travieso', 'loki.jpg', 1, 'H09'),
(45, 'Nugget', 'Hurón champagne energético', 'nugget.jpg', 2, 'H09'),
(46, 'Nagini', 'Pitón real dócil', 'nagini.jpg', 5, 'S10'),
(47, 'Kaa', 'Boa constrictor tranquila', 'kaa.jpg', 7, 'S10'),
(48, 'Medusa', 'Serpiente de maíz colorida', 'medusa.jpg', 3, 'S10'),
(49, 'Slytherin', 'Serpiente rey de California', 'slytherin.jpg', 4, 'S10'),
(50, 'Basilisk', 'Pitón bola albina', 'basilisk.jpg', 2, 'S10');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `parquesmascotas`
--

CREATE TABLE `parquesmascotas` (
  `id` int(11) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `descripcion` text DEFAULT NULL,
  `latitud` decimal(12,8) DEFAULT NULL,
  `longitud` decimal(12,8) DEFAULT NULL,
  `foto` varchar(255) DEFAULT NULL,
  `visitantes` int(11) DEFAULT NULL,
  `areas_verdes` int(11) DEFAULT NULL,
  `instalaciones` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `parquesmascotas`
--

INSERT INTO `parquesmascotas` (`id`, `nombre`, `descripcion`, `latitud`, `longitud`, `foto`, `visitantes`, `areas_verdes`, `instalaciones`) VALUES
(1, 'Parque Canino Esperancita', 'Parque para perros con áreas de juego, zonas de descanso y vistas al mar en la Costa Verde de Magdalena. Ideal para socializar a las mascotas y disfrutar de un día al aire libre.', -12.10294207, -77.06545081, 'parquecaninoesperancita.jpg', 200, 5, 'Fuentes de agua, bancos'),
(2, 'Play Land Can', 'Parque ubicado en San Borja, cuenta con áreas de juego para perros, zonas de descanso y sombra, y equipamiento para ejercicio canino. Perfecto para mantener a las mascotas activas y felices.', -12.10278307, -76.98860833, 'playlandcan.jpg', 150, 4, 'Equipos de agilidad, sombra'),
(3, 'Zona Guau', 'Este espacio recreativo en Los Olivos ofrece equipos de agilidad, áreas de juego y zonas verdes para que los perros corran y jueguen libremente. Un lugar favorito para los entrenadores de perros.', -11.98338295, -77.06695023, 'zonaguau.jpg', 180, 6, 'Equipos de agilidad, sombra'),
(4, 'Divertican', 'Parque canino en San Isidro con fuentes de agua, áreas de sombra y equipamiento de juego. Ideal para refrescarse y jugar durante los días calurosos.', -12.10075278, -77.02273413, 'divertican.jpg', 120, 3, 'Fuentes de agua, bancos'),
(5, 'Parque de las Mascotas', 'Ubicado en Jesús María, este parque urbano cuenta con zonas de juego, áreas verdes y bancos para que los dueños de mascotas puedan descansar mientras sus perros juegan.', -12.06811939, -77.04133415, 'parquedelasmacotas.jpg', 170, 5, 'Bancos, áreas de juego'),
(6, 'Parque Texcoco', 'Área canina en La Molina con equipos de ejercicio, áreas de descanso y una gran variedad de superficies para que los perros puedan explorar y disfrutar.', -12.09529442, -76.92293350, 'parquetexcoco.jpg', 130, 4, 'Equipos de ejercicio, bancos'),
(7, 'Espacios para Mascotas', 'En Miraflores, este parque ofrece áreas de juego, zonas de descanso y vistas impresionantes. Un lugar popular para paseos y juegos al aire libre.', -12.12160904, -77.03041772, 'espaciosparamascotas.jpg', 160, 5, 'Áreas de juego, sombra');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipos_animales`
--

CREATE TABLE `tipos_animales` (
  `tipo_id` varchar(3) NOT NULL,
  `tipo` varchar(50) NOT NULL,
  `descripcion` text NOT NULL,
  `foto` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tipos_animales`
--

INSERT INTO `tipos_animales` (`tipo_id`, `tipo`, `descripcion`, `foto`) VALUES
('A03', 'Aves', 'Coloridas y melodiosas, ideales para quienes disfrutan de su canto.', 'aves.jpg'),
('C04', 'Conejos', 'Dulces y juguetones, excelentes para niños y adultos por igual.', 'conejos.jpg'),
('G02', 'Gatos', 'Independientes y afectuosos, perfectos para hogares tranquilos.', 'gatos.jpg'),
('H06', 'Hámsters', 'Pequeños y activos, ideales para espacios reducidos.', 'hamsters.jpg'),
('H09', 'Hurones', 'Juguetones y curiosos, para dueños activos y aventureros.', 'hurones.jpg'),
('L08', 'Loros', 'Inteligentes y parlanchines, para quienes buscan una mascota interactiva.', 'loros.jpg'),
('P01', 'Perros', 'Leales y cariñosos, excelentes compañeros para toda la familia.', 'perros.jpg'),
('P05', 'Peces', 'Relajantes y de bajo mantenimiento, perfectos para decorar el hogar.', 'peces.jpg'),
('S10', 'Serpientes', 'Fascinantes y de bajo mantenimiento, para amantes de lo exótico.', 'serpientes.jpg'),
('T07', 'Tortugas', 'Tranquilas y longevas, compañeras para toda la vida.', 'tortugas.jpg');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `ingreso`
--
ALTER TABLE `ingreso`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `mascotas`
--
ALTER TABLE `mascotas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `tipo_id` (`tipo_id`);

--
-- Indices de la tabla `parquesmascotas`
--
ALTER TABLE `parquesmascotas`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `tipos_animales`
--
ALTER TABLE `tipos_animales`
  ADD PRIMARY KEY (`tipo_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `ingreso`
--
ALTER TABLE `ingreso`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `mascotas`
--
ALTER TABLE `mascotas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;

--
-- AUTO_INCREMENT de la tabla `parquesmascotas`
--
ALTER TABLE `parquesmascotas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `mascotas`
--
ALTER TABLE `mascotas`
  ADD CONSTRAINT `mascotas_ibfk_1` FOREIGN KEY (`tipo_id`) REFERENCES `tipos_animales` (`tipo_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
