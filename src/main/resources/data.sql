INSERT INTO roles(nombre) VALUES ('ADMINISTRADOR'), ('CLIENTE');
INSERT INTO usuarios(correo, clave, activo) VALUES ('cliente@piscinas.cr', '{pendiente-bcrypt}', TRUE);
INSERT INTO usuarios_roles(usuario_id, rol_id) VALUES (1, 2);
INSERT INTO clientes(usuario_id, nombre, telefono, direccion) VALUES (1, 'Cliente demostración', '8804-1005', 'Costa Rica');
INSERT INTO categorias(nombre, descripcion) VALUES
('Químicos', 'Balance, desinfección y claridad del agua'),
('Limpieza', 'Herramientas para una piscina impecable'),
('Equipos', 'Bombas, filtros y circulación eficiente'),
('Accesorios', 'Complementos para disfrutar la piscina');
INSERT INTO productos(categoria_id, sku, nombre, descripcion, precio, existencia, destacado) VALUES
(1, 'QUI-CLORO-01', 'Cloro granulado premium', 'Desinfección potente para agua limpia y segura.', 18500, 24, TRUE),
(1, 'QUI-PH-01', 'Regulador de pH', 'Protege el agua, la piel y los equipos.', 9900, 18, TRUE),
(1, 'QUI-ALG-01', 'Alguicida concentrado', 'Previene y elimina la formación de algas.', 12500, 16, FALSE),
(2, 'LIM-KIT-01', 'Kit de limpieza completo', 'Red, cepillo y accesorios esenciales.', 34900, 9, TRUE),
(2, 'LIM-ASP-01', 'Aspiradora manual', 'Limpieza práctica del fondo de la piscina.', 42500, 7, FALSE),
(3, 'EQU-BOM-01', 'Bomba de alta eficiencia', 'Circulación confiable con consumo optimizado.', 189000, 5, TRUE),
(3, 'EQU-FIL-01', 'Filtro de arena', 'Filtración estable para agua transparente.', 229000, 4, FALSE),
(4, 'ACC-LED-01', 'Luz LED sumergible', 'Iluminación segura para disfrutar de noche.', 29500, 12, FALSE);
INSERT INTO servicios(nombre, descripcion, precio_base) VALUES
('Mantenimiento preventivo', 'Limpieza, balance químico e inspección general.', 35000),
('Diagnóstico del agua', 'Medición y recomendación de tratamiento.', 15000),
('Revisión de equipos', 'Diagnóstico de bombas y filtración.', 25000);
INSERT INTO pedidos(cliente_id, estado, subtotal, impuesto, total) VALUES (1, 'DEMOSTRACION', 18500, 2405, 20905);
INSERT INTO detalles_pedido(pedido_id, producto_id, cantidad, precio_unitario, subtotal) VALUES (1, 1, 1, 18500, 18500);
