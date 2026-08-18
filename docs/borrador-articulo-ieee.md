# Borrador de artículo — formato IEEE pendiente de maquetación

## Título

**Tienda Piscinas: aplicación web transaccional para productos y servicios de mantenimiento de piscinas**

## Autores

`[Nombres reales del equipo — PENDIENTE DE COMPLETAR]`

## Resumen

Este trabajo presenta una aplicación web académica que integra catálogo, inventario, autenticación por roles, solicitudes de servicio, carrito por sesión y pedidos transaccionales para el dominio de piscinas. La solución utiliza Spring Boot, Spring MVC, Thymeleaf, JPA/Hibernate, MySQL, Bootstrap y Spring Security, siguiendo la arquitectura estudiada durante el curso. El flujo de compra valida cantidades y precios en el servidor, conserva el precio unitario en el detalle, descuenta inventario y utiliza rollback ante stock insuficiente. Además, incluye internacionalización español/inglés y un cotizador orientativo que calcula el volumen de piscinas rectangulares y relaciona la necesidad indicada con servicios activos. La evaluación automatizada local comprende 64 pruebas aprobadas; la validación con MySQL limpio, la usabilidad con participantes y la evidencia de cliente permanecen pendientes y no se sustituyen por resultados simulados.

## Palabras clave

Spring Boot, MVC, comercio electrónico, Spring Security, JPA, transacciones, internacionalización, piscinas.

## I. Introducción

La compra de suministros y la solicitud de mantenimiento pueden requerir consultar información dispersa sobre precio, disponibilidad, servicio y seguimiento. Tienda Piscinas propone centralizar esas tareas en una aplicación web de servidor que permita diferenciar operaciones públicas, de cliente y de administración.

El objetivo académico fue evolucionar el proyecto existente sin sustituir su tecnología ni introducir una arquitectura ajena al curso. Se priorizaron Controller, Service, Repository, entidades JPA y plantillas Thymeleaf, junto con mecanismos simples de sesión y seguridad.

## II. Problema y alcance

El sistema debe ofrecer persistencia medular, más de ocho tablas útiles, control real de accesos, operaciones transaccionales, traducción visible y una funcionalidad investigada. El alcance implementado incluye usuarios, roles, clientes, categorías, productos, servicios, solicitudes, carrito, pedidos, detalles e inventario. Las entidades heredadas de piscinas, reservas y pagos se conservaron en el esquema, aunque su interfaz completa no forma parte del segundo bloque funcional.

## III. Arquitectura y metodología

La presentación se implementó con HTML5, Bootstrap y Thymeleaf. Los controladores reciben solicitudes HTTP y delegan reglas a interfaces Service con implementaciones concretas. Los repositorios Spring Data administran las entidades JPA y MySQL conserva la información.

Spring Security autentica por correo y compara las contraseñas BCrypt. Las reglas del servidor restringen rutas; Thymeleaf Security adapta la navegación, pero no sustituye la autorización backend. La internacionalización utiliza archivos `messages_es.properties` y `messages_en.properties` con locale de sesión.

El trabajo se organizó por bloques funcionales. Cada bloque fue auditado, probado y registrado en `docs/bitacora-integrante2.md`, con commits separados en la rama `feature/samuel-segundo-50`.

## IV. Modelo de datos

El esquema final contiene 13 tablas físicas. `usuarios`, `roles`, `usuarios_roles` y `clientes` soportan identidad y permisos. `categorias` y `productos` soportan el catálogo. `servicios` y `solicitudes_servicio` representan atención al cliente. `pedidos` y `detalles_pedido` registran compras y precios históricos. `piscinas`, `reservas` y `pagos` preservan el modelo inicial.

Las relaciones más relevantes son Producto→Categoría, Cliente→Usuario, Solicitud→Cliente/Servicio, Pedido→Cliente y Detalle→Pedido/Producto.

## V. Seguridad y transacciones

El registro público asigna únicamente `CLIENTE`, rechaza correos duplicados y codifica contraseñas. `ADMINISTRADOR` gestiona catálogos y estados; `CLIENTE` compra y consulta solamente sus datos; el invitado se limita al contenido público.

Al finalizar una compra, el servidor vuelve a obtener los productos, bloquea las filas necesarias, valida stock y cantidades, calcula subtotal, impuesto y total, guarda pedido y detalles, descuenta existencias y vacía el carrito únicamente si la transacción concluye. Una excepción provoca rollback y evita registros parciales.

## VI. Funcionalidad investigada

El cotizador utiliza la fórmula de volumen basada en área y profundidad promedio documentada por NatHERS. Para una piscina rectangular, calcula largo por ancho por profundidad y convierte metros cúbicos a litros. Las recomendaciones recuerdan medir pH y desinfectante y seguir instrucciones del fabricante, en línea con orientación del CDC.

Los factores de precio por volumen y estado son supuestos académicos internos, no reglas atribuidas a NatHERS o CDC. La herramienta no prescribe químicos ni sustituye una evaluación profesional. Fórmulas, fuentes y limitaciones se documentan en `docs/investigacion-cotizacion.md`.

## VII. Evaluación

La última ejecución local de `mvn clean test` completó 64 pruebas con cero fallos y cero errores. La suite cubre seguridad, registro BCrypt, CRUD, consultas, carrito, pedido exitoso, stock insuficiente, rollback, propiedad de historiales, i18n, páginas de error, diseño, cotizador y configuración SQL.

No se reportan resultados de clientes ni usabilidad porque todavía no se han recopilado con participantes reales. Tampoco se afirma una ejecución completa del script sobre una base MySQL limpia: esa comprobación requiere una instancia respaldada o descartable y una credencial autorizada.

## VIII. Discusión y limitaciones

La solución demuestra una aplicación transaccional integrada sin introducir frameworks frontend o capas adicionales. Entre las limitaciones están el cotizador restringido a piscinas rectangulares, factores comerciales pendientes de validación, QA visual manual pendiente y ausencia actual de evidencia humana. El uso académico de `ddl-auto=update` facilita el desarrollo, mientras que el script oficial permite reconstruir el esquema de demostración.

## IX. Conclusiones

Tienda Piscinas conecta seguridad, persistencia, catálogo, servicios y compras en una evolución coherente del proyecto de curso. La separación MVC y la transacción de pedido permiten defender decisiones técnicas concretas. Los pasos restantes son validar el script en MySQL limpio, ejecutar usabilidad real, incorporar evidencia de cliente y completar la revisión colaborativa mediante Pull Request.

## Referencias preliminares

[1] Australian Government, Nationwide House Energy Rating Scheme, “Whole of Home Calculations Method,” 23 ene. 2025. [En línea]. Disponible: <https://www.nathers.gov.au/sites/default/files/2025-01/NatHERS%20Whole%20of%20Home%20Calculations%20Method%2020250123.pdf>. Consulta: 18 ago. 2026.

[2] Centers for Disease Control and Prevention, “Home Pool and Hot Tub Water Treatment and Testing,” 10 may. 2024. [En línea]. Disponible: <https://www.cdc.gov/healthy-swimming/about/home-pool-and-hot-tub-water-treatment-and-testing.html>. Consulta: 18 ago. 2026.

[3] Centers for Disease Control and Prevention, “Guidelines for Keeping Your Pool Safe and Healthy,” 29 may. 2025. [En línea]. Disponible: <https://www.cdc.gov/healthy-swimming/safety/what-you-can-do-to-stay-healthy-in-swimming-pools.html>. Consulta: 18 ago. 2026.

## Pendientes editoriales

- Incorporar nombres reales y afiliación académica.
- Adaptar longitud, columnas, tipografía y referencias a la plantilla IEEE solicitada por el profesor.
- Agregar figuras o capturas reales con pie y fuente.
- Incorporar resultados humanos únicamente después de ejecutarlos.
- Revisar redacción final entre ambos integrantes.
