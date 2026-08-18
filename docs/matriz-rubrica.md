# Matriz de evidencia para la rúbrica

Estado actualizado: 18 de agosto de 2026.

Esta matriz distingue entre funcionalidad comprobada localmente y evidencia que depende de GitHub, MySQL o participantes reales. Un estado pendiente no debe presentarse como logro durante la defensa.

| Criterio | Peso | Funcionalidad implementada | Archivo/código | Prueba/evidencia | Estado |
| --- | ---: | --- | --- | --- | --- |
| Almacenamiento | 10% | 13 tablas útiles; pedidos, detalles y solicitudes registran operaciones | `proyecto_piscinas/piscinas_script.sql`; entidades en `domain` | `ModeloPersistenciaTests`; `ConfiguracionMySqlTests` cuenta 13 tablas | Implementado y probado con H2; ejecución del script en MySQL limpio pendiente |
| Autenticación y roles | 10% | Login real, BCrypt, ADMINISTRADOR/CLIENTE, rutas, vistas y acciones protegidas | `SecurityConfig`; `UsuarioDetallesService`; `UsuarioServiceImpl`; fragmentos Thymeleaf | `SeguridadAccesoTests`; `RegistroUsuarioTests` | Implementado; 60 pruebas aprobadas localmente |
| Uso efectivo de base de datos | 10% | Catálogo, usuarios, roles, solicitudes, pedidos, detalles, stock e historial dependen de persistencia | Repositorios y capas Service; `PedidoServiceImpl` | Pruebas CRUD, persistencia, compra, rollback e historial | Implementado; validación MySQL limpia pendiente |
| Internacionalización | 8% | Español/inglés visible, selector y locale en sesión; menús, formularios, estados, validaciones y mensajes | `InternacionalizacionConfig`; `messages_es.properties`; `messages_en.properties` | `InternacionalizacionTests`; prueba bilingüe del cotizador | Implementado y probado localmente |
| Diseño final | 10% | Portada comercial, catálogo en cards, paleta acuática, navegación por rol, estados vacíos y fragmentos | `index.html`; `static/css/estilos.css`; `fragmentos` | `DisenoComercialTests`; CSS público y datos reales en portada | Implementado; QA visual manual responsive pendiente |
| Temáticas del curso | 15% | MVC, Thymeleaf, Bootstrap, JPA, CRUD, relaciones, JPQL, Security, sesión/carrito, transacciones e i18n | Código en `controller`, `service`, `repository`, `domain`, `templates` | Suite de 60 pruebas y demostración planificada | Más de 6 temas implementados y probados |
| Investigación adicional | 7% | Cotizador por volumen/estado que usa servicios activos y factores transparentes | `CotizacionPiscinaServiceImpl`; `cotizacion/formulario.html`; `docs/investigacion-cotizacion.md` | 7 pruebas de servicio/web; fuentes NatHERS y CDC | Implementado; factores comerciales pendientes de validación real |
| Uso colaborativo de GitHub | 10% | Rama exclusiva y commits funcionales distribuidos; bitácora con hashes | Rama `feature/samuel-segundo-50`; `docs/bitacora-integrante2.md` | `git log`; diffs por bloque | Evidencia local lista; push y PR bloqueados por permiso 403 |
| Solución real / cliente potencial | 10% | Flujo de tienda y servicios coherente con una necesidad potencial; protocolo de entrevista/encuesta preparado | `docs/evidencia-cliente.md`; módulos funcionales | Evidencia humana aún no aportada | **PENDIENTE DE EVIDENCIA REAL DEL EQUIPO** |
| Presentación y defensa | 10% | README, borrador IEEE, guion, datos demo y recorrido técnico | `README.md`; `docs/borrador-articulo-ieee.md`; `docs/guion-defensa.md` | Ensayo y demostración en vivo pendientes | Material preparado; defensa real pendiente |

## Temas del curso demostrables

| Tema | Evidencia técnica | Cómo demostrarlo |
| --- | --- | --- |
| Spring Boot / MVC | Controladores, rutas y modelos | Abrir catálogo y seguir Controller → vista |
| Thymeleaf y fragmentos | `templates` y `fragmentos` | Cambiar rol/idioma y observar navegación compartida |
| Bootstrap / CSS | Cards, formularios, tablas y `estilos.css` | Mostrar portada y catálogo en dos anchos de pantalla |
| JPA / Hibernate | Entidades y repositorios | Explicar creación y consulta de Pedido/DetallePedido |
| CRUD | Categorías, productos y servicios | Crear, modificar y realizar eliminación controlada |
| Relaciones | ManyToOne y usuarios-roles | Mostrar Producto→Categoría y Detalle→Pedido/Producto |
| Consultas derivadas / JPQL | `ProductoRepository` y filtros | Buscar por nombre, categoría y disponibilidad |
| Spring Security | Configuración, BCrypt y Thymeleaf Security | Comparar invitado, CLIENTE y ADMINISTRADOR |
| Sesión y carrito | `CarritoSesion` y controlador | Agregar, incrementar, reducir y vaciar |
| Transacciones | `PedidoServiceImpl` con `@Transactional` | Explicar compra y prueba de rollback |
| Internacionalización | `messages_es/en` y locale | Cambiar a English y navegar |

## Evidencia Git local disponible

- Rama: `feature/samuel-segundo-50`.
- Commits funcionales: consultar `git log --oneline --decorate`.
- Bitácora cronológica: `docs/bitacora-integrante2.md`.
- Pruebas: 60 aprobadas, 0 fallos y 0 errores en la última ejecución local.
- Bloqueo externo: la cuenta autenticada `Erian158` recibe HTTP 403 al publicar en el repositorio de `SAMuel-wk-bot`.

## Evidencias que no deben simularse

- Entrevista, encuesta o aceptación de un cliente.
- Resultados reales de usabilidad.
- Revisión de un compañero en el Pull Request.
- Ejecución exitosa del script sobre MySQL limpio.
- Comportamiento visual en dispositivos no observados.
