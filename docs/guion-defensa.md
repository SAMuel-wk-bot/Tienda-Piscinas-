# Guion de demostración y defensa

## Objetivo

Demostrar en 12–15 minutos que Tienda Piscinas es transaccional, segura, internacionalizada y coherente con los temas del curso. Los nombres y tiempos pueden ajustarse después del ensayo.

## Preparación previa

- Ejecutar el script en una base MySQL de demostración respaldada o descartable.
- Definir variables de entorno y levantar la aplicación en puerto 8080.
- Ejecutar `mvn clean test` y guardar el resultado real.
- Confirmar cuentas demo y stock suficiente.
- Abrir una ventana de invitado y otra lista para cambiar de rol.
- No usar datos ni credenciales personales.

## Distribución sugerida

| Tiempo | Responsable | Contenido |
| --- | --- | --- |
| 0:00–1:00 | Integrante 1 | Problema, usuario potencial y alcance del proyecto base |
| 1:00–2:00 | Integrante 2 | Arquitectura MVC, tecnologías y 13 tablas |
| 2:00–4:00 | Integrante 1 | Portada, catálogo, filtros, categorías y productos |
| 4:00–6:00 | Integrante 2 | Login, roles, rutas protegidas y menú por rol |
| 6:00–9:00 | Integrante 2 | Carrito, compra, Pedido/Detalle, inventario y rollback |
| 9:00–10:30 | Integrante 1 | Servicios, solicitudes e historial |
| 10:30–12:00 | Integrante 2 | Cotizador, fuentes, supuestos y limitaciones |
| 12:00–13:00 | Integrante 1 | Cambio español/inglés y experiencia visual |
| 13:00–14:00 | Ambos | Pruebas, Git, evidencia de cliente real si existe y pendientes |
| 14:00–15:00 | Ambos | Conclusión y preguntas |

La distribución debe revisarse con el equipo para asegurar participación equitativa y que cada persona pueda explicar el código que presenta.

## Recorrido de demostración

### 1. Invitado

1. Abrir portada y explicar que las cards provienen de base de datos.
2. Filtrar productos y mostrar uno agotado/disponible.
3. Abrir servicios y cotizador sin autenticación.
4. Intentar un historial y mostrar redirección a login.

### 2. Seguridad

1. Registrar un cliente y explicar que el rol no es seleccionable.
2. Mostrar BCrypt en concepto, sin enseñar el hash completo como dato útil.
3. Ingresar como CLIENTE.
4. Explicar `SecurityConfig` y una condición `sec:authorize`.
5. Intentar `/administracion` con CLIENTE y mostrar 403.

### 3. Compra transaccional

1. Agregar producto al carrito.
2. Incrementar y reducir cantidad.
3. Explicar que precio y stock se consultan otra vez en el servidor.
4. Finalizar pedido.
5. Abrir “Mis pedidos” y su detalle.
6. Mostrar el stock reducido.
7. Explicar la prueba de stock insuficiente y rollback.

### 4. Servicios

1. Crear una solicitud como CLIENTE.
2. Consultar solamente las solicitudes propias.
3. Entrar como ADMINISTRADOR y actualizar el estado.

### 5. Administración

1. Mostrar CRUD de categoría, producto y servicio.
2. Mostrar usuarios sin contraseñas.
3. Filtrar pedidos/solicitudes y cambiar un estado.

### 6. Internacionalización

1. Cambiar a English.
2. Navegar a login, catálogo, carrito o cotizador.
3. Explicar archivos `messages` y locale de sesión.

### 7. Investigación

1. Ingresar 10 × 4 × 1.25 m y agua verde.
2. Mostrar 50 m³ / 50 000 L.
3. Identificar el servicio activo y factores aplicados.
4. Explicar que NatHERS sustenta volumen y CDC las advertencias.
5. Aclarar que los factores comerciales son supuestos internos pendientes de validación.

## Decisiones técnicas defendibles

- **MVC clásico:** coincide con el patrón del curso y facilita separar vistas, rutas, reglas y persistencia.
- **Carrito por sesión:** fue un tema de clase y evita infraestructura adicional.
- **Seguridad backend + vista:** ocultar botones mejora experiencia, pero las rutas permanecen protegidas.
- **BCrypt:** la base guarda hashes y el registro público nunca asigna ADMINISTRADOR.
- **Precio histórico:** DetallePedido conserva el precio usado aunque el producto cambie después.
- **`@Transactional`:** compra e inventario se confirman juntos o se revierten.
- **JPQL simple:** evidencia consultas estudiadas sin introducir una capa de especificaciones.
- **i18n por sesión:** usa `messages` y conserva el idioma durante la navegación.

## Preguntas probables

### ¿Cómo evitan que un cliente vea un pedido ajeno?

El Service consulta el pedido por identificador y correo autenticado. No confía solamente en el ID de la URL.

### ¿Cómo evitan precio o cantidad manipulados?

El carrito guarda identificadores/cantidades; al comprar, el servidor recupera productos, precio y stock desde la base y recalcula todo.

### ¿Qué ocurre si un producto no tiene inventario?

Se lanza una excepción dentro de la transacción. No se guarda pedido parcial ni se descuentan otros productos.

### ¿Por qué no usaron React, JWT o microservicios?

No eran necesarios para la rúbrica y se priorizó la misma estructura Spring MVC/Thymeleaf estudiada.

### ¿El cotizador prescribe tratamiento?

No. Calcula volumen, orienta y relaciona servicios. La vista advierte que no diagnostica ni indica dosificaciones.

### ¿Qué evidencia falta?

Prueba MySQL limpia, QA visual manual, usabilidad y cliente reales, push/PR y revisión del compañero.

## Checklist del día de presentación

- [ ] Base MySQL de demostración lista.
- [ ] Aplicación inicia sin errores.
- [ ] 60 o más pruebas aprobadas con resultado actual.
- [ ] Invitado, CLIENTE y ADMINISTRADOR comprobados.
- [ ] Stock inicial conocido.
- [ ] Selector de idioma comprobado.
- [ ] Cotizador con datos preparados.
- [ ] Evidencia humana real disponible o declarada pendiente.
- [ ] Historial Git y Pull Request visibles, si el permiso fue resuelto.
- [ ] Ambos integrantes ensayaron su sección y las preguntas.
