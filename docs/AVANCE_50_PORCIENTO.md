# Informe técnico del avance: 50%

**Fecha de corte:** 17 de agosto de 2026  
**Rama:** `samuel`  
**Objetivo:** construir la base funcional, persistente, visual y reproducible de Tienda Piscinas Santamaría sin ejecutar la mitad asignada al segundo integrante.

## 1. Problema y alcance

El cliente potencial necesita centralizar la consulta de productos químicos, equipos, accesorios y servicios para piscinas. La primera mitad resuelve la presentación comercial y la consulta persistente del catálogo. La segunda mitad debe convertir esa base en una aplicación autenticada y transaccional completa.

## 2. Método de desarrollo

Se utilizó una arquitectura MVC por capas. La vista Thymeleaf solicita el catálogo al controlador; el controlador utiliza `ProductoRepository`; JPA consulta la base; y Thymeleaf renderiza los registros. Esto permite demostrar que la persistencia tiene un uso visible y no decorativo.

```text
Navegador -> InicioController -> ProductoRepository -> Base de datos
          <- index.html       <- List<Producto>      <- productos
```

## 3. Componentes entregados

| Componente | Evidencia | Estado |
|---|---|---|
| Diseño responsive | `index.html`, `estilos.css`, fragmentos | Completo en esta fase |
| Spring Boot/MVC | `InicioController` | Completo en esta fase |
| Thymeleaf | Ciclo `th:each`, expresiones y fragmentos | Completo en esta fase |
| Persistencia JPA | Entidades, repositorio y catálogo dinámico | Parcial del proyecto total |
| Base de datos | `schema.sql`: 10 tablas; `data.sql` | Estructura completa, operaciones parciales |
| Tabla transaccional | `pedidos` y `detalles_pedido` | Diseñada y poblada con evidencia demo |
| Pruebas | Contexto + integración MVC/JPA | Base automatizada completa |
| Documentación | README y carpeta `docs` | Completa para este corte |

## 4. Matriz de trazabilidad con la rúbrica

| Criterio | Aporte de esta mitad | Pendiente para segunda mitad |
|---|---|---|
| Almacenamiento | 10 tablas, claves foráneas, índices y pedido demo | Validar flujo completo de creación de pedidos |
| Autenticación y roles | Tablas `usuarios`, `roles`, `usuarios_roles` | Spring Security y restricciones reales |
| Uso efectivo de BD | Catálogo leído mediante JPA | CRUD, carrito, pedidos y servicios persistentes |
| Internacionalización | No se implementa en esta mitad | Español/inglés funcional |
| Diseño final | Interfaz responsive y consistente | Ajustes finales basados en pruebas de usuario |
| Temáticas del curso | HTML/CSS, Bootstrap, Spring Boot, MVC, Thymeleaf, JPA | Seguridad, CRUD, relaciones avanzadas, consultas |
| Investigación adicional | Contacto contextual por WhatsApp | Convertir carrito a cotización o integrar otra mejora |
| GitHub colaborativo | Rama y commit del primer integrante | Rama, commits, PR y revisión del segundo integrante |
| Solución real | Propuesta orientada a Piscinas Santamaría | Adjuntar entrevista/evidencia o estudio de mercado |
| Defensa | Documentación técnica inicial | Artículo IEEE, presentación y demostración final |

## 5. Modelo de datos

El esquema contiene: roles, usuarios, usuarios_roles, clientes, categorías, productos, servicios, solicitudes_servicio, pedidos y detalles_pedido. Las relaciones garantizan trazabilidad entre cliente, compra, producto y solicitud de servicio. `pedidos` es la cabecera transaccional y `detalles_pedido` conserva cantidades y precios históricos.

## 6. Protocolo de prueba reproducible

1. Instalar JDK 21 y Maven 3.9+.
2. Ejecutar `mvn clean test`.
3. Confirmar que cargan el contexto y `InicioControllerTests`.
4. Ejecutar `mvn spring-boot:run`.
5. Visitar `/` y confirmar ocho productos cargados.
6. Abrir `/h2-console` y consultar `SELECT * FROM productos;` y `SELECT * FROM pedidos;`.
7. Verificar la adaptación visual en anchos de 375, 768 y 1440 píxeles.

## 7. Limitaciones declaradas

Los testimonios y métricas de clientes de la maqueta son contenido demostrativo y deben validarse antes de uso comercial. H2 es una base de desarrollo; la configuración productiva debe usar MySQL y secretos externos. La autenticación todavía no existe y la clave demo es deliberadamente inválida.

## 8. Evidencias recomendadas para el artículo

- Captura de la portada en escritorio y móvil.
- Resultado de `mvn clean test` con fecha y commit.
- Diagrama entidad-relación derivado de `schema.sql`.
- Capturas de consultas de productos y pedido demo.
- Tabla comparativa antes/después del prototipo.
- Registro de commits, pull request y revisión entre pares.
- Consentimiento o minuta de entrevista con cliente potencial.
