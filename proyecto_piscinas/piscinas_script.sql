-- SCRIPT OFICIAL Y ÚNICA FUENTE DEL ESQUEMA DE DEMOSTRACIÓN.
-- Compatible con MySQL 8. No crea usuarios de MySQL ni contiene credenciales personales.
-- ADVERTENCIA: elimina y vuelve a crear las tablas de gestion_piscinas.
CREATE DATABASE IF NOT EXISTS gestion_piscinas
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;
USE gestion_piscinas;

SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS detalles_pedido;
DROP TABLE IF EXISTS pedidos;
DROP TABLE IF EXISTS solicitudes_servicio;
DROP TABLE IF EXISTS servicios;
DROP TABLE IF EXISTS pagos;
DROP TABLE IF EXISTS reservas;
DROP TABLE IF EXISTS piscinas;
DROP TABLE IF EXISTS productos;
DROP TABLE IF EXISTS categorias;
DROP TABLE IF EXISTS clientes;
DROP TABLE IF EXISTS usuarios_roles;
DROP TABLE IF EXISTS usuarios;
DROP TABLE IF EXISTS roles;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE roles (
    id_rol BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(30) NOT NULL UNIQUE
);

CREATE TABLE usuarios (
    id_usuario BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    telefono VARCHAR(20),
    contrasena VARCHAR(100) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_registro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE usuarios_roles (
    id_usuario BIGINT NOT NULL,
    id_rol BIGINT NOT NULL,
    PRIMARY KEY (id_usuario, id_rol),
    CONSTRAINT fk_usuarios_roles_usuario
        FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
    CONSTRAINT fk_usuarios_roles_rol
        FOREIGN KEY (id_rol) REFERENCES roles(id_rol)
);

CREATE TABLE clientes (
    id_cliente BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_usuario BIGINT NOT NULL UNIQUE,
    direccion VARCHAR(250) NOT NULL,
    CONSTRAINT fk_clientes_usuario
        FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
);

CREATE TABLE categorias (
    id_categoria BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_categoria VARCHAR(50) NOT NULL UNIQUE,
    descripcion TEXT
);

CREATE TABLE productos (
    id_producto BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_categoria BIGINT NOT NULL,
    nombre_producto VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(12, 2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    fecha_ingreso TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_productos_precio CHECK (precio > 0),
    CONSTRAINT chk_productos_stock CHECK (stock >= 0),
    CONSTRAINT fk_productos_categoria
        FOREIGN KEY (id_categoria) REFERENCES categorias(id_categoria)
);

CREATE TABLE servicios (
    id_servicio BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(1000) NOT NULL,
    precio_base DECIMAL(12, 2) NOT NULL DEFAULT 0,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT chk_servicios_precio CHECK (precio_base >= 0)
);

CREATE TABLE solicitudes_servicio (
    id_solicitud BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_cliente BIGINT NOT NULL,
    id_servicio BIGINT NOT NULL,
    fecha_solicitud TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    direccion_servicio VARCHAR(250) NOT NULL,
    observaciones VARCHAR(1000),
    estado VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE',
    CONSTRAINT fk_solicitudes_cliente
        FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente),
    CONSTRAINT fk_solicitudes_servicio
        FOREIGN KEY (id_servicio) REFERENCES servicios(id_servicio)
);

CREATE TABLE piscinas (
    id_piscina BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_piscina VARCHAR(50) NOT NULL,
    tipo VARCHAR(30) NOT NULL,
    capacidad_max INT NOT NULL,
    precio_hora DECIMAL(10, 2) NOT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'Disponible'
);

CREATE TABLE reservas (
    id_reserva BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_usuario BIGINT NOT NULL,
    id_piscina BIGINT NOT NULL,
    fecha_reserva DATE NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fin TIME NOT NULL,
    estado_reserva VARCHAR(20) NOT NULL DEFAULT 'Pendiente',
    CONSTRAINT fk_reservas_usuario
        FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
    CONSTRAINT fk_reservas_piscina
        FOREIGN KEY (id_piscina) REFERENCES piscinas(id_piscina)
);

CREATE TABLE pagos (
    id_pago BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_reserva BIGINT NOT NULL UNIQUE,
    monto DECIMAL(10, 2) NOT NULL,
    metodo_pago VARCHAR(30) NOT NULL,
    fecha_pago TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_pagos_monto CHECK (monto >= 0),
    CONSTRAINT fk_pagos_reserva
        FOREIGN KEY (id_reserva) REFERENCES reservas(id_reserva)
);

CREATE TABLE pedidos (
    id_pedido BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_cliente BIGINT NOT NULL,
    fecha_pedido TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    subtotal DECIMAL(12, 2) NOT NULL,
    impuesto DECIMAL(12, 2) NOT NULL,
    total DECIMAL(12, 2) NOT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE',
    CONSTRAINT chk_pedidos_subtotal CHECK (subtotal >= 0),
    CONSTRAINT chk_pedidos_impuesto CHECK (impuesto >= 0),
    CONSTRAINT chk_pedidos_total CHECK (total >= 0),
    CONSTRAINT fk_pedidos_cliente
        FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente)
);

CREATE TABLE detalles_pedido (
    id_detalle BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_pedido BIGINT NOT NULL,
    id_producto BIGINT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(12, 2) NOT NULL,
    subtotal_linea DECIMAL(12, 2) NOT NULL,
    CONSTRAINT chk_detalles_cantidad CHECK (cantidad > 0),
    CONSTRAINT chk_detalles_precio CHECK (precio_unitario > 0),
    CONSTRAINT chk_detalles_subtotal CHECK (subtotal_linea > 0),
    CONSTRAINT fk_detalles_pedido
        FOREIGN KEY (id_pedido) REFERENCES pedidos(id_pedido),
    CONSTRAINT fk_detalles_producto
        FOREIGN KEY (id_producto) REFERENCES productos(id_producto)
);

CREATE INDEX idx_productos_nombre ON productos(nombre_producto);
CREATE INDEX idx_solicitudes_estado ON solicitudes_servicio(estado);
CREATE INDEX idx_pedidos_estado ON pedidos(estado);
CREATE INDEX idx_pedidos_cliente_fecha ON pedidos(id_cliente, fecha_pedido);

INSERT INTO roles (nombre) VALUES
    ('ADMINISTRADOR'),
    ('CLIENTE');

-- Credenciales ficticias exclusivas para demostración académica.
-- admin@tiendapiscinas.test / AdminPiscinas2026!
-- cliente@tiendapiscinas.test / ClientePiscinas2026!
-- La columna contrasena contiene hashes BCrypt, nunca las claves anteriores en texto plano.
INSERT INTO usuarios
    (nombre, apellido, email, telefono, contrasena, activo)
VALUES
    ('Administrador', 'Demostración', 'admin@tiendapiscinas.test', '8888-0001',
     '$2a$10$vQvJ0TwYKZgldp9KE6oUpegAbKmJ6/cew8X56DGFyhPFaRhTtj6Oi', TRUE),
    ('Cliente', 'Demostración', 'cliente@tiendapiscinas.test', '8888-0002',
     '$2a$10$Hcx7qW8tBKax01nkjbwDqeFctBN/NIG7pHmq9PMsqcoZAzBPcVgB2', TRUE);

INSERT INTO usuarios_roles (id_usuario, id_rol)
SELECT u.id_usuario, r.id_rol
FROM usuarios u
JOIN roles r ON r.nombre = 'ADMINISTRADOR'
WHERE u.email = 'admin@tiendapiscinas.test';

INSERT INTO usuarios_roles (id_usuario, id_rol)
SELECT u.id_usuario, r.id_rol
FROM usuarios u
JOIN roles r ON r.nombre = 'CLIENTE'
WHERE u.email = 'cliente@tiendapiscinas.test';

INSERT INTO clientes (id_usuario, direccion)
SELECT id_usuario, 'Dirección ficticia para demostración académica'
FROM usuarios
WHERE email = 'cliente@tiendapiscinas.test';

INSERT INTO categorias (nombre_categoria, descripcion) VALUES
    ('Químicos', 'Productos para el tratamiento y equilibrio del agua.'),
    ('Limpieza', 'Herramientas y accesorios para limpieza.'),
    ('Equipos', 'Bombas, filtros y equipos para piscina.');

INSERT INTO productos
    (id_categoria, nombre_producto, descripcion, precio, stock)
VALUES
    (1, 'Cloro granulado 1 kg', 'Desinfectante granulado para piscinas.', 6500.00, 20),
    (1, 'Regulador de pH', 'Producto demostrativo para ajustar el pH.', 4800.00, 15),
    (2, 'Red recogehojas', 'Red manual para residuos de superficie.', 7900.00, 8),
    (3, 'Bomba de filtración', 'Bomba demostrativa para sistema de filtración.', 125000.00, 3);

INSERT INTO servicios (nombre, descripcion, precio_base, activo) VALUES
    ('Mantenimiento', 'Revisión y mantenimiento general de la piscina.', 35000.00, TRUE),
    ('Limpieza', 'Limpieza programada de piscina.', 25000.00, TRUE),
    ('Tratamiento de agua', 'Evaluación y tratamiento básico del agua.', 30000.00, TRUE),
    ('Revisión de bomba', 'Diagnóstico básico del equipo de bombeo.', 20000.00, TRUE);

INSERT INTO solicitudes_servicio
    (id_cliente, id_servicio, direccion_servicio, observaciones, estado)
SELECT c.id_cliente, s.id_servicio,
       'Dirección ficticia para demostración académica',
       'Solicitud ficticia exclusiva para demostrar el flujo académico.',
       'PENDIENTE'
FROM clientes c
JOIN usuarios u ON u.id_usuario = c.id_usuario
JOIN servicios s ON s.nombre = 'Mantenimiento'
WHERE u.email = 'cliente@tiendapiscinas.test';

INSERT INTO piscinas
    (nombre_piscina, tipo, capacidad_max, precio_hora, estado)
VALUES
    ('Piscina demostrativa', 'Familiar', 12, 15000.00, 'Disponible');

-- Pedido histórico ficticio para demostrar la tabla transaccional y el precio conservado.
START TRANSACTION;

INSERT INTO pedidos (id_cliente, subtotal, impuesto, total, estado)
SELECT c.id_cliente, 6500.00, 845.00, 7345.00, 'PENDIENTE'
FROM clientes c
JOIN usuarios u ON u.id_usuario = c.id_usuario
WHERE u.email = 'cliente@tiendapiscinas.test';

SET @pedido_demo = LAST_INSERT_ID();

INSERT INTO detalles_pedido
    (id_pedido, id_producto, cantidad, precio_unitario, subtotal_linea)
SELECT @pedido_demo, id_producto, 1, precio, precio
FROM productos
WHERE nombre_producto = 'Cloro granulado 1 kg';

UPDATE productos
SET stock = stock - 1
WHERE nombre_producto = 'Cloro granulado 1 kg';

COMMIT;
