# Informe final del Integrante 2

Fecha de corte: 18 de agosto de 2026.

## A. Resumen ejecutivo

La rama local `feature/samuel-segundo-50` evoluciona Tienda Piscinas hacia una aplicación web transaccional con seguridad, roles, catálogos, servicios, solicitudes, carrito por sesión, pedidos, inventario, historial, internacionalización y cotización estimada. El desarrollo conserva Spring MVC, Thymeleaf, Controller/Service/Repository y JPA, sin migrar a otro frontend ni introducir infraestructura ajena al curso.

El esquema representa 13 tablas útiles. La compra guarda pedido y detalles con precio histórico, calcula importes en servidor y revierte la operación ante inventario insuficiente. La última suite limpia completó 64 pruebas sin fallos, errores ni omisiones.

El código y los 18 commits están completos localmente. Ningún push se confirmó: GitHub rechazó todos los intentos con HTTP 403 porque la cuenta autenticada `Erian158` no tiene permiso de escritura sobre el repositorio de `SAMuel-wk-bot`. Por esa razón tampoco fue posible crear el Pull Request.

## B. Lista de módulos

| Módulo | Función |
| --- | --- |
| Seguridad | Login, logout, BCrypt y rutas por rol |
| Registro | Crea cuenta y perfil CLIENTE; correo único |
| Usuarios | Consulta administrativa sin mostrar hashes |
| Categorías | CRUD y eliminación controlada |
| Productos | CRUD, stock, precio, categoría, búsqueda y filtros |
| Servicios | Catálogo público y CRUD administrativo |
| Solicitudes | Creación/consulta propia y gestión administrativa de estado |
| Carrito | Sesión, agregar, aumentar, reducir, eliminar y vaciar |
| Pedidos | Compra transaccional, totales, detalles y descuento de stock |
| Historial | Propiedad por cliente y detalle; vista total para administración |
| Errores | Páginas 403, 404 y 500; errores de negocio traducibles |
| Internacionalización | Español/inglés, locale de sesión y 303 claves por idioma |
| Interfaz comercial | Portada, cards, navegación por rol y paleta acuática |
| Cotizador | Volumen, litros, recomendación y servicio/precio estimado |
| Modelo heredado | Conserva Piscina, Reserva y Pago en el esquema |

## C. Archivos importantes

| Finalidad | Ubicación |
| --- | --- |
| Aplicación Spring | `proyecto_piscinas/src/main/java/com/piscinas/gestion_piscinas` |
| Seguridad | `config/SecurityConfig.java` y `service/UsuarioDetallesService.java` |
| Datos demo | `config/DatosInicialesConfig.java` |
| Entidades | paquete `domain` |
| Persistencia | paquete `repository` |
| Reglas y transacciones | paquete `service` |
| Rutas MVC | paquete `controller` |
| Vistas | `proyecto_piscinas/src/main/resources/templates` |
| Estilos | `proyecto_piscinas/src/main/resources/static/css/estilos.css` |
| Idiomas | `messages_es.properties` y `messages_en.properties` |
| Configuración | `application.properties` y `.env.example` |
| SQL oficial | `proyecto_piscinas/piscinas_script.sql` |
| Pruebas | `proyecto_piscinas/src/test/java/com/piscinas/gestion_piscinas` |
| Instalación | `README.md` y `docs/configuracion-mysql.md` |
| Bitácora | `docs/bitacora-integrante2.md` |
| Rúbrica | `docs/matriz-rubrica.md` |
| Investigación | `docs/investigacion-cotizacion.md` |

## D. Base de datos

El script contiene 13 tablas:

1. `usuarios`: identidad, hash BCrypt y estado.
2. `roles`: ADMINISTRADOR y CLIENTE.
3. `usuarios_roles`: relación de seguridad.
4. `clientes`: perfil y dirección.
5. `categorias`: clasificación del catálogo.
6. `productos`: precio, descripción, categoría y stock.
7. `servicios`: oferta y precio base.
8. `solicitudes_servicio`: transacción cliente-servicio y estado.
9. `pedidos`: cabecera, fecha, importes y estado.
10. `detalles_pedido`: producto, cantidad, precio histórico y subtotal.
11. `piscinas`: entidad heredada.
12. `reservas`: transacción heredada.
13. `pagos`: pago de reserva heredado.

El script agrega datos ficticios de demostración, incluida una solicitud y un pedido con detalle. No crea usuarios MySQL ni almacena credenciales personales. La ejecución completa sobre una instancia MySQL limpia está pendiente porque la instancia local exige una contraseña no proporcionada y el script reconstruye las tablas.

## E. Roles y permisos reales

### Invitado

- Puede abrir inicio, productos, servicios y cotizador.
- Puede buscar, filtrar, registrarse, iniciar sesión y cambiar idioma.
- No puede confirmar compras, consultar historiales ni entrar a administración.

### CLIENTE

- Puede usar carrito y finalizar compra.
- Puede ver únicamente sus pedidos y solicitudes.
- Puede crear solicitudes.
- Recibe 403 si intenta una ruta administrativa.

### ADMINISTRADOR

- Gestiona categorías, productos y servicios.
- Consulta usuarios, solicitudes y pedidos.
- Modifica estados.
- La navegación y las acciones administrativas aparecen solamente para este rol.

Las reglas se aplican en Spring Security y se complementan con `sec:authorize`; ocultar elementos no reemplaza la seguridad del servidor.

## F. Flujo de compra

1. CLIENTE agrega un identificador de producto y cantidad al carrito de sesión.
2. El servidor recupera el producto y valida cantidad positiva y stock.
3. El carrito permite aumentar, reducir, eliminar o vaciar.
4. Al confirmar, se obtiene el usuario autenticado y su perfil Cliente.
5. `PedidoServiceImpl` vuelve a cargar cada producto con bloqueo para actualización.
6. Se rechazan productos inexistentes, cantidades inválidas o inventario insuficiente.
7. El precio se toma de la base; no se acepta un precio del navegador.
8. Se calcula subtotal, tasa de impuesto centralizada y total.
9. Se crea Pedido y un DetallePedido por línea con precio histórico.
10. Se descuenta el stock.
11. `@Transactional` confirma todo junto; una excepción produce rollback.
12. El carrito se vacía solamente después del éxito.
13. El cliente consulta el pedido por identificador y correo autenticado.

## G. Temas de clase demostrados

1. Spring Boot y Spring MVC.
2. Thymeleaf y fragmentos.
3. Bootstrap y CSS.
4. JPA/Hibernate.
5. CRUD.
6. Relaciones entre entidades.
7. Consultas derivadas y JPQL.
8. Spring Security, roles y BCrypt.
9. Variables de sesión y carrito.
10. Transacciones y rollback.
11. Internacionalización con `messages` y locale.
12. Validaciones y formularios.

## H. Investigación adicional

El cotizador implementa:

- `volumen_m3 = largo × ancho × profundidad_promedio`;
- `litros = volumen_m3 × 1000`;
- recomendación por agua clara, turbia, verde o problema de equipo;
- búsqueda de un servicio activo relacionado;
- estimación desde precio base y factores internos documentados.

NatHERS sustenta la fórmula de volumen. CDC sustenta las advertencias sobre prueba de pH/desinfectante y seguimiento de instrucciones del fabricante. Los factores comerciales son supuestos académicos internos pendientes de validación; no se atribuyen a esas fuentes. Detalle: `docs/investigacion-cotizacion.md`.

## I. Internacionalización

- Configuración: `InternacionalizacionConfig`.
- Archivos: 303 claves en español y 303 en inglés, sin diferencias de clave.
- Selector: barra de navegación.
- Persistencia: locale almacenado en sesión.
- Cobertura: menús, formularios, botones, estados, validaciones, confirmaciones, errores, carrito, pedidos, servicios y cotizador.

Demostración: abrir `/?lang=en`, navegar a login/carrito/cotizador y comprobar que el idioma permanece; regresar con `?lang=es`.

## J. Pruebas

Comando:

```powershell
mvn clean test
```

Último resultado local sobre copia limpia del proyecto:

```text
Tests: 64
Failures: 0
Errors: 0
Skipped: 0
BUILD SUCCESS
```

Cobertura relevante:

- rutas pública/admin/cliente, login, logout y menús por rol;
- registro, correo único y BCrypt;
- CRUD y consultas JPQL;
- solicitudes y estados;
- carrito completo;
- compra, totales, stock y rollback;
- historial y propiedad de pedido;
- 403/404/500;
- español/inglés y errores de negocio;
- portada/CSS;
- cotizador;
- variables, 13 tablas y hashes SQL.

Pruebas externas pendientes: script sobre MySQL limpio, QA visual manual responsive y usabilidad con participantes reales.

## K. Git

- Repositorio: `https://github.com/SAMuel-wk-bot/Tienda-Piscinas-.git`.
- Base usada: `main` en `8c3fdeb4eadaea70be31cb688f9ec5435c6b3c29` (`Avance 2`).
- Rama local: `feature/samuel-segundo-50`.
- Commits de desarrollo e informe desde la base al momento de redactar: 19 (18 funcionales y 1 de cierre documental).
- Diferencia acumulada antes del commit de cierre: 157 archivos, 9617 inserciones y 1316 eliminaciones; gran parte de las eliminaciones corresponde a artefactos generados de `target` y al SQL duplicado.
- Push: no confirmado; HTTP 403 en todos los intentos.
- Pull Request: no creado porque la rama no existe en remoto.

### Commits

1. `33723b0e113206f5bfc0a22cec6c47613345a268` — Documentar estado inicial del segundo 50 por ciento.
2. `dc8b1ae7074234e049acc8fdad440b63b1df9b34` — Implementar modelo de usuarios roles y entidades transaccionales.
3. `b6b2fb6e89ba917531ba54b83b7a0a2f7bc7d2c4` — Configurar autenticacion registro y control de acceso por roles.
4. `beb01504316d1588645fbb345bd55f0e7eee3430` — Agregar pruebas de autenticacion y autorizacion.
5. `4890e386668990c7be830ad0fd2a16fe9094d9d3` — Completar CRUD de categorias y productos con validaciones.
6. `3f211df301c2e63060a9df6d36b5d270ecbd0469` — Agregar busquedas y filtros con consultas JPA y JPQL.
7. `a1942325a41a006fcd7db9312f54b44c32187ab2` — Implementar gestion de servicios y solicitudes.
8. `c9f5ea48f0017f372cbbda17d6eb34d7cc22f193` — Implementar carrito de compras basado en sesion.
9. `e055b16b368015e967b0c2eaa58fc564a15e6f85` — Implementar creacion transaccional de pedidos e inventario.
10. `2c5dfc4e0c329aee55a8d51ec33db3aadeda877d` — Agregar historial de pedidos y gestion administrativa de estados.
11. `3658938f44ec9a3346331998e4f993bb60410594` — Agregar manejo de errores y paginas de estado.
12. `5d0bc4dda6f4d35ed56a37b04fcb6ceedc439ca0` — Integrar internacionalizacion en español e ingles.
13. `bfe01d50b0bc5c6c1e315117d4c4ba8bc0b80982` — Mejorar interfaz comercial y navegacion de Tienda Piscinas.
14. `1bb90862e7d8af08aa3671271ad1e2a6cfba514b` — Implementar cotizacion inteligente para piscinas.
15. `28b00da6ba9c9065457ef37a363cfc032084adac` — Actualizar configuracion MySQL y script de base de datos.
16. `79d86fe34d558813ced3675b15d5e8d46993700b` — Completar README y documentacion de instalacion.
17. `fd3eb38de646e7d81ad95a00ec47f02dc86811d7` — Preparar matriz de rubrica y guion de defensa.
18. `9452b0bc0f52299731c072dae8ae92364758332c` — Completar pruebas finales de seguridad e internacionalizacion.
19. `0fb13c9c23db31d8f30a3ac6b5cd600225c79008` — Completar informe final y evidencias de entrega.

### Rama objetivo del PR

No existe `origin/codex/tienda-piscinas`. Las ramas remotas observadas son `main`, `samuel` y `oscarsolis`. No se debe asumir el destino: el equipo debe confirmar si la rama de integración acordada es `samuel` o si el PR debe dirigirse a `main`. No se realizó merge.

## L. Relación con la rúbrica

La evidencia detallada está en `docs/matriz-rubrica.md`.

- Almacenamiento: 13 tablas y transacciones.
- Autenticación: seguridad real, dos roles, menú/vista/acción.
- Base medular: catálogo, stock, solicitudes y pedidos.
- i18n: cobertura visible ES/EN.
- Diseño: interfaz comercial Bootstrap.
- Temas: más de seis, demostrables.
- Investigación: cotizador documentado y probado.
- GitHub: rama y 18 commits locales; publicación bloqueada.
- Solución real: protocolo listo, evidencia humana pendiente.
- Defensa: README, artículo, matriz y guion listos; ensayo pendiente.

## M. Pendientes reales

1. El propietario debe dar acceso de escritura a la cuenta correcta o autenticar una cuenta autorizada.
2. Publicar `feature/samuel-segundo-50` y confirmar el upstream.
3. Acordar la rama destino (`samuel` o `main`) antes de abrir el PR.
4. Crear el PR y solicitar revisión del primer integrante; no hacer merge unilateral.
5. Ejecutar `piscinas_script.sql` sobre una instancia MySQL limpia o respaldada.
6. Realizar QA visual manual en móvil y escritorio; la herramienta Browser estuvo bloqueada por su dependencia interna de ruta confiable.
7. Ejecutar entrevista/encuesta y usabilidad con personas reales.
8. Incorporar resultados humanos sin inventar datos.
9. Validar factores comerciales del cotizador con un cliente o encargado real.
10. Maquetar el borrador en la plantilla IEEE indicada por el profesor.
11. Ensayar la defensa y confirmar participación equitativa.

No se afirma como completada ninguna tarea que dependa de estas evidencias externas.
