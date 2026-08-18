# Bitácora del Integrante 2 — segundo 50 %

Proyecto: Tienda Piscinas  
Curso: Desarrollo de Aplicaciones Web y Patrones  
Repositorio oficial: `https://github.com/SAMuel-wk-bot/Tienda-Piscinas-.git`  
Rama local de trabajo: `feature/samuel-segundo-50`  
Base: `main` en `8c3fdeb`  
Estado de publicación: **PENDIENTE**. GitHub respondió `403` porque la cuenta autenticada `Erian158` no tiene permiso de escritura en el repositorio de `SAMuel-wk-bot`.

## Convenciones de esta bitácora

- Una tarea solo se marca terminada cuando funciona, fue probada, está documentada y tiene commit/push confirmado.
- `HECHO LOCAL` significa que existe evidencia local, pero todavía falta publicación en GitHub.
- `PENDIENTE` no se reemplaza con datos inventados.
- El hash de cada commit se agrega en la siguiente actualización de la bitácora, porque un commit no puede contener de forma estable su propio hash.

## Bloque 0 — auditoría y base funcional

- **Fecha:** 17 de agosto de 2026.
- **Objetivo:** localizar el repositorio oficial, proteger el estado existente, seleccionar una base, crear la rama exclusiva y comprobar la estructura, la compilación, las pruebas, MySQL y los materiales del curso.
- **Situación antes del cambio:** el workspace solo contenía prácticas y recursos del curso. No había un clon de Tienda Piscinas. Se clonó el remoto oficial en una carpeta nueva. El árbol de trabajo de `main` estaba limpio y no existía `codex/tienda-piscinas`.
- **Archivos creados:** `docs/bitacora-integrante2.md`.
- **Archivos modificados:** ninguno del proyecto funcional.
- **Funcionalidad implementada:** documentación reproducible del estado inicial; rama local exclusiva creada desde `main` actualizado.
- **Temas del curso relacionados:** Spring Boot, MVC, Thymeleaf, JPA/Hibernate, Repository, Service/ServiceImpl, MySQL, relaciones JPA, Bootstrap y pruebas Spring.
- **Explicación sencilla:** se revisó cada capa existente y se ejecutó el proyecto sin asumir que funcionaba. La compilación se separó de la conexión a base de datos para distinguir errores de código de errores de entorno.

### Git inicial

- Remoto confirmado: `origin https://github.com/SAMuel-wk-bot/Tienda-Piscinas-.git`.
- Ramas remotas encontradas: `main`, `oscarsolis` y `samuel`.
- Rama de integración `codex/tienda-piscinas`: no existe.
- `main`: `8c3fdeb Avance 2`.
- `samuel`: contiene un primer 50 % divergente y documentación que propone integrar mediante PR hacia `samuel` o una rama acordada. No se fusionó ni reescribió.
- Decisión de base: `main`, por instrucción expresa de usar la rama principal cuando no exista una rama de integración.
- Rama creada: `feature/samuel-segundo-50`.
- Primer intento de push: **FALLA**, HTTP `403`, sin permiso para la cuenta `Erian158`.
- Protección de cambios ajenos: el clon inició limpio; no había cambios locales de otra persona y no se usaron `reset --hard`, `clean -fd`, force push ni reescritura de commits.

### Inventario inicial del proyecto

Ubicación del proyecto Spring Boot: `proyecto_piscinas`.

#### Configuración y dependencias

- Spring Boot `4.1.0`.
- Java objetivo `17`; Java disponible en el equipo `24.0.1`.
- Maven localizado en Apache NetBeans; no existe `mvnw` en el repositorio.
- Dependencias principales: Spring Data JPA, Thymeleaf, Spring Web MVC, MySQL Connector, Validation y WebJars de Bootstrap.
- `application.properties` contiene URL, usuario y contraseña MySQL fijos (`usuario/contrasenna`), puerto `80`, `ddl-auto=update` y SQL visible.
- No existe `.gitignore` en `main` y `proyecto_piscinas/target` está versionado con binarios generados y atributos de solo lectura.

#### Entidades y tablas existentes

| Entidad | Tabla | Finalidad | Relaciones observadas |
|---|---|---|---|
| `Usuario` | `usuarios` | Datos básicos del usuario | Usado desde `Reserva`; no tiene contraseña ni rol |
| `Piscina` | `piscinas` | Piscinas reservables | Usada desde `Reserva` |
| `Reserva` | `reservas` | Reserva de piscina | `ManyToOne` con Usuario y Piscina; `OneToOne` con Pago |
| `Pago` | `pagos` | Pago de reserva | `OneToOne` con Reserva |
| `Categoria` | `categorias` | Clasificación de productos | Referenciada desde Producto |
| `Producto` | `productos` | Inventario comercial | `ManyToOne` con Categoria |

Total inicial: **6 tablas reales**. No existen todavía `roles`, `clientes`, `servicios`, `solicitudes_servicio`, `pedidos` ni `detalle_pedido` en `main`.

#### Repositorios

- Existen seis repositorios JPA: Usuario, Piscina, Reserva, Pago, Categoria y Producto.
- Solo heredan de `JpaRepository`; no hay consultas derivadas ni JPQL.

#### Servicios

- `CategoriaService` + `CategoriaServiceImpl`: operaciones básicas de listar, guardar, obtener y eliminar; no existía controlador para exponerlas.
- `ProductoService` + `ProductoServiceImpl`: operaciones básicas de listar, guardar, obtener y eliminar; el controlador solo utilizaba el listado.
- Usuario, Piscina, Reserva y Pago no tienen capa de servicio.

#### Controladores y vistas

- Único controlador: `ProductoController`, con `GET /productos` para listar.
- No hay altas, cambios, bajas, filtros, detalle ni validación de formularios.
- Vistas: home estático, listado de productos y fragmentos separados de encabezado/pie.
- El home no consume la base de datos.
- El listado usa Bootstrap desde CDN mientras el home usa WebJars; la referencia `/css/estilos.css` no tiene archivo estático correspondiente en `main`.
- No existen login, registro, carrito, pedidos, servicios, solicitudes, administración, errores ni selector de idioma.

#### SQL y MySQL

- Existen dos copias del script: `proyecto_piscinas/piscinas_script.sql` y `proyecto_piscinas/src/main/resources/piscinas_script.sql`.
- Los scripts no son idénticos y crean 6 tablas.
- El script elimina la base y el usuario al inicio; requiere cuidado y no se ejecutó durante la auditoría.
- El servicio local `MySQL84` está activo.
- La aplicación sí alcanza MySQL, pero el servidor rechaza `usuario/contrasenna` con código `1045`.
- No fue posible confirmar tablas o datos reales sin credenciales válidas. La evidencia inicial de esquema se limita al SQL versionado.

#### Pruebas iniciales

- Solo existe `GestionPiscinasApplicationTests.contextLoads()`.
- No existe perfil de prueba ni base embebida.
- No hay pruebas MVC, repositorio, servicio, seguridad o transacciones.

### Materiales del curso consultados

- Práctica de suculentas: patrón `Controller -> Service -> ServiceImpl -> Repository -> Entity`, CRUD mediante rutas y vistas Thymeleaf.
- Recurso de Lección 05: validaciones Jakarta, `BigDecimal` y relación `Producto -> Categoria` con `ManyToOne`.
- Recurso de Lección 06: inyección por constructor, filtros por categoría y relación `OneToMany` cuando la navegación lo requiere.
- Caso 1: fragmentos generales, archivos `messages` y ejemplo de almacenamiento Firebase.
- No se encontró en el workspace un ejemplo local completo de Spring Security. Cuando se implemente el requisito explícito, se usará la alternativa mínima compatible con la estructura estudiada y se documentará como tal.

### Pruebas ejecutadas y resultados

1. `git status`, `git remote -v`, ramas y últimos 15 commits: **PASA**; clon limpio y remoto correcto.
2. `mvn clean test` sobre copia aislada sin `target`: **FALLA** después de compilar; 1 prueba ejecutada, 1 error por MySQL `1045`.
3. `mvn -DskipTests package` sobre copia aislada: **PASA**, `BUILD SUCCESS`.
4. `mvn -DskipTests spring-boot:run` sobre copia aislada: **FALLA**; Tomcat inicia la preparación, pero JPA detiene el contexto por MySQL `1045`.
5. Arranque y navegación manual: **BLOQUEADO** por las credenciales MySQL fijas.

- **Resultado general:** compilación correcta; pruebas y aplicación no funcionales con la configuración inicial.
- **Commit realizado:** `Documentar estado inicial del segundo 50 por ciento`.
- **Hash del commit:** `33723b0e113206f5bfc0a22cec6c47613345a268`.
- **Rama:** `feature/samuel-segundo-50`.
- **Push confirmado:** **NO**. Intento fallido por `403`.
- **Criterio de rúbrica relacionado:** almacenamiento, uso efectivo de base de datos, GitHub colaborativo y temáticas del curso.
- **Pendientes inmediatos:** crear un perfil de pruebas reproducible; externalizar credenciales; corregir seguimiento de `target`; implementar el modelo de seguridad; resolver permiso de escritura en GitHub; acordar si el PR final apunta a `main` o a `samuel`.
- **Estado del bloque:** **HECHO LOCAL / PENDIENTE PUSH**.

## Bloque 1 — modelo de seguridad y entidades transaccionales

- **Fecha:** 17 de agosto de 2026.
- **Objetivo:** extender el modelo existente sin borrar Piscina, Reserva ni Pago; superar ocho tablas útiles y preparar persistencia real para seguridad, servicios y compras.
- **Situación antes del cambio:** 6 entidades, 6 tablas y 6 repositorios. Usuario no tenía contraseña ni roles. No existían Cliente, Servicio, SolicitudServicio, Pedido o DetallePedido. Las pruebas dependían de las credenciales MySQL locales.
- **Archivos creados:** `Rol.java`, `Cliente.java`, `Servicio.java`, `SolicitudServicio.java`, `Pedido.java`, `DetallePedido.java`, `EstadoSolicitudServicio.java`, `EstadoPedido.java`; repositorios de las seis entidades nuevas; `ModeloPersistenciaTests.java`; `src/test/resources/application.properties`.
- **Archivos modificados:** `pom.xml`; Usuario, Producto, Categoria, Piscina, Reserva y Pago; UsuarioRepository; las dos copias de `piscinas_script.sql`; esta bitácora.
- **Funcionalidad implementada:** modelo JPA de roles y usuarios, perfil de cliente, catálogo, solicitudes de servicio, cabecera/detalle de compra y estados simples. Se añadieron validaciones de entidad, restricciones de nulidad y repositorios con consultas derivadas iniciales.
- **Temas del curso relacionados:** entidades JPA, Hibernate, validaciones Jakarta, Repository, `ManyToOne`, `OneToOne`, `ManyToMany`, claves foráneas, SQL, consultas derivadas y pruebas de persistencia.
- **Explicación sencilla:** Usuario se relaciona con Rol mediante `usuarios_roles`; Cliente agrega la dirección necesaria para compras/servicios; una Solicitud conecta Cliente y Servicio; un Pedido pertenece al Cliente y cada Detalle conserva producto, cantidad y precio histórico.

### Modelo resultante

- Entidades JPA: 12.
- Tablas físicas: 13, porque `usuarios_roles` representa la relación real entre usuarios y roles.
- Tablas transaccionales: `reservas`, `pagos`, `solicitudes_servicio`, `pedidos` y `detalles_pedido`.
- Roles previstos: `ADMINISTRADOR` y `CLIENTE`.
- Estados de solicitud: `PENDIENTE`, `EN_PROCESO`, `COMPLETADA`, `CANCELADA`.
- Estados de pedido: `PENDIENTE`, `CONFIRMADO`, `PREPARANDO`, `COMPLETADO`, `CANCELADO`.
- Las dos copias del SQL tienen el mismo SHA-256 en este bloque.
- El script ya no elimina la base ni crea/elimina usuarios MySQL; deja las credenciales fuera del SQL.
- Los usuarios demo BCrypt se posponen al bloque de seguridad, para no insertar contraseñas en texto plano ni hashes ficticios inválidos.

### Pruebas ejecutadas y resultados

1. `mvn clean test` sobre una copia aislada sin el `target` versionado: **PASA**.
2. Compilación: 32 fuentes principales y 2 fuentes de prueba: **PASA**.
3. `GestionPiscinasApplicationTests.contextLoads`: **PASA** con H2 de alcance de prueba.
4. `ModeloPersistenciaTests.persisteRelacionesDelSegundoCincuentaPorCiento`: **PASA**.
5. Resultado Maven: 2 pruebas, 0 fallos, 0 errores, `BUILD SUCCESS`.
6. Validación SQL: ambas copias sincronizadas: **PASA**.

- **Resultado general:** modelo y relaciones funcionales en prueba automatizada. MySQL real sigue pendiente de credenciales/autorización para ejecutar el script en una base limpia.
- **Commit realizado:** `Implementar modelo de usuarios roles y entidades transaccionales`.
- **Hash del commit:** `dc8b1ae7074234e049acc8fdad440b63b1df9b34`.
- **Rama:** `feature/samuel-segundo-50`.
- **Push confirmado:** **NO**; el permiso remoto continúa bloqueado por HTTP `403`.
- **Criterio de rúbrica relacionado:** almacenamiento (más de 8 tablas y tablas transaccionales), uso efectivo de BD, temáticas JPA/relaciones/validación y base para autenticación.
- **Pendientes:** servicios/controladores/vistas de las entidades; seguridad; BCrypt; registro; transacción de compra; ejecutar el SQL contra MySQL limpio cuando existan credenciales autorizadas.
- **Estado del bloque:** **HECHO Y CONFIRMADO LOCALMENTE / PENDIENTE PUSH**.

## Bloque 2 — Autenticación, registro y control de acceso por roles

- **Fecha:** 17 de agosto de 2026.
- **Objetivo:** implementar autenticación persistida con Spring Security, roles reales, registro público seguro y restricciones tanto en servidor como en Thymeleaf.
- **Situación antes del cambio:** existían las tablas `usuarios`, `roles`, `usuarios_roles` y `clientes`, pero no había configuración de seguridad, cifrado, login, logout, registro ni rutas protegidas.

### Archivos creados

- `src/main/java/com/piscinas/gestion_piscinas/config/SecurityConfig.java`.
- `src/main/java/com/piscinas/gestion_piscinas/config/DatosInicialesConfig.java`.
- `src/main/java/com/piscinas/gestion_piscinas/controller/InicioController.java`.
- `src/main/java/com/piscinas/gestion_piscinas/controller/RegistroController.java`.
- `src/main/java/com/piscinas/gestion_piscinas/controller/AdministracionController.java`.
- `src/main/java/com/piscinas/gestion_piscinas/domain/RegistroUsuario.java`.
- `src/main/java/com/piscinas/gestion_piscinas/service/UsuarioDetallesService.java`.
- `src/main/java/com/piscinas/gestion_piscinas/service/UsuarioService.java`.
- `src/main/java/com/piscinas/gestion_piscinas/service/UsuarioServiceImpl.java`.
- `src/main/resources/templates/seguridad/login.html`.
- `src/main/resources/templates/seguridad/registro.html`.
- `src/main/resources/templates/administracion/inicio.html`.
- `src/main/resources/templates/administracion/usuarios.html`.
- `src/main/resources/templates/error/403.html`.
- `src/test/java/com/piscinas/gestion_piscinas/RegistroUsuarioTests.java`.

### Archivos modificados

- `pom.xml`.
- `src/main/resources/templates/fragmentos/encabezado.html`.
- `docs/bitacora-integrante2.md`.

### Funcionalidad implementada

- Autenticación contra usuarios almacenados en la base de datos mediante `UserDetailsService`.
- Contraseñas codificadas con `BCryptPasswordEncoder`.
- Roles `ADMINISTRADOR` y `CLIENTE` transformados en autoridades `ROLE_ADMINISTRADOR` y `ROLE_CLIENTE`.
- Login propio, mensajes de error, logout POST e invalidación de sesión/cookie.
- Registro público con nombre, apellido, correo, teléfono, dirección, contraseña y confirmación.
- Correo normalizado y único; el duplicado se rechaza antes de persistir.
- El rol público siempre se asigna en el servidor como `CLIENTE`; el formulario no ofrece selección de roles.
- Creación conjunta y transaccional de `Usuario` y `Cliente`.
- Rutas `/administracion/**` protegidas para `ADMINISTRADOR`.
- Rutas `/cliente/**` y `/carrito/**` protegidas para `CLIENTE`.
- Menú condicionado con Thymeleaf Security y logout mediante formulario protegido por CSRF.
- Consulta administrativa de usuarios sin mostrar hashes de contraseña.
- Cuentas ficticias de demostración inicializadas con BCrypt únicamente si no existen.

### Temas del curso relacionados

- Spring Security, autenticación, roles, restricción de rutas, Thymeleaf Security, MVC, formularios, validación, servicios, repositorios, transacciones y sesión HTTP.

### Explicación sencilla

Spring Security recibe el correo y la contraseña del formulario. `UsuarioDetallesService` busca el usuario real y carga sus roles. BCrypt compara la contraseña escrita con el hash almacenado. Las reglas del servidor deciden qué rutas puede abrir cada rol; adicionalmente, Thymeleaf oculta las opciones que no corresponden. En el registro, el servidor ignora cualquier intento de escoger privilegios y asigna únicamente `CLIENTE`.

### Credenciales ficticias exclusivas para demostración académica

| Rol | Usuario | Contraseña |
| --- | --- | --- |
| ADMINISTRADOR | `admin@tiendapiscinas.test` | `AdminPiscinas2026!` |
| CLIENTE | `cliente@tiendapiscinas.test` | `ClientePiscinas2026!` |

La base de datos guarda hashes BCrypt, no las contraseñas anteriores en texto plano.

### Pruebas ejecutadas y resultados

1. `mvn clean test` en copia aislada sin el `target` versionado: **PASA**.
2. Compilación de 41 fuentes principales y 3 fuentes de prueba: **PASA**.
3. Carga del contexto con la cadena de seguridad y H2: **PASA**.
4. Persistencia del modelo anterior: **PASA**.
5. Registro crea `Usuario` y `Cliente` con rol único `CLIENTE`: **PASA**.
6. La contraseña guardada no coincide con el texto y `PasswordEncoder.matches` la valida: **PASA**.
7. Segundo registro con el mismo correo: **RECHAZADO como se esperaba**.
8. Resultado Maven: 4 pruebas, 0 fallos, 0 errores, `BUILD SUCCESS`.

- **Commit realizado:** `Configurar autenticacion registro y control de acceso por roles`.
- **Hash del commit:** `b6b2fb6e89ba917531ba54b83b7a0a2f7bc7d2c4`.
- **Rama:** `feature/samuel-segundo-50`.
- **Push confirmado:** **NO**; GitHub volvió a responder HTTP `403` porque la cuenta autenticada `Erian158` no tiene permiso de escritura en el repositorio de `SAMuel-wk-bot`.
- **Criterio de rúbrica relacionado:** autenticación y roles (10%), uso efectivo de BD, temáticas del curso y diseño consistente.
- **Pendientes:** pruebas MockMvc de rutas/login por cada rol, completar módulos administrativos enlazados, incorporar los hashes demo al script SQL final y publicar cuando exista permiso remoto.
- **Estado del bloque:** **HECHO Y CONFIRMADO LOCALMENTE / PENDIENTE PUSH**.

## Bloque 3 — Pruebas de autenticación y autorización

- **Fecha:** 17 de agosto de 2026.
- **Objetivo:** demostrar automáticamente que las restricciones de Spring Security funcionan en servidor y que el login consulta usuarios reales de la base de datos.
- **Situación antes del cambio:** el bloque de seguridad compilaba y el registro tenía pruebas, pero aún no existían pruebas HTTP para invitado, CLIENTE y ADMINISTRADOR.

### Archivos creados

- `src/main/java/com/piscinas/gestion_piscinas/controller/ClienteController.java`.
- `src/main/resources/templates/cliente/inicio.html`.
- `src/test/java/com/piscinas/gestion_piscinas/SeguridadAccesoTests.java`.

### Archivos modificados

- `pom.xml` para agregar `spring-security-test` únicamente con alcance de prueba.
- `docs/bitacora-integrante2.md`.

### Funcionalidad implementada

- Zona `/cliente` protegida para completar un destino real de la regla CLIENTE.
- Suite MockMvc que ejecuta peticiones a través de toda la cadena de filtros de seguridad.
- Prueba de login con correo y contraseña demo reales, token CSRF y autenticación desde H2.
- Usuarios simulados se adjuntan directamente a la petición para comprobar cada autoridad sin depender de la sesión de otra prueba.

### Temas del curso relacionados

- Spring Security, MockMvc, autenticación, autorización, roles, CSRF, MVC y pruebas de integración.

### Explicación sencilla

MockMvc se comporta como un navegador dentro de la prueba: solicita rutas sin sesión o con un rol definido y comprueba el código HTTP, la redirección y la vista. Para el login válido no se simula la contraseña: Security consulta el usuario demo en H2 y BCrypt verifica la clave.

### Pruebas ejecutadas y resultados

Primera ejecución: 11 pruebas, 5 fallos. La causa fue que `@WithMockUser` no se aplicó a las peticiones con la configuración de pruebas de Spring Security 7, por lo que todas llegaron como anónimas. Se corrigió usando el procesador oficial `user(...)` directamente en cada petición MockMvc y se repitió la suite completa.

Ejecución final:

1. Ruta pública `/` sin login: **200 / PASA**.
2. Ruta `/administracion` sin login: **redirección a `/login` / PASA**.
3. CLIENTE solicita `/administracion`: **403 / PASA**.
4. ADMINISTRADOR solicita `/administracion`: **200 / PASA**.
5. CLIENTE solicita `/cliente`: **200 / PASA**.
6. Invitado solicita `/cliente`: **redirección a `/login` / PASA**.
7. Login con cuenta CLIENTE persistida y BCrypt: **autenticado y redirigido a `/` / PASA**.
8. Suite completa: 11 pruebas, 0 fallos, 0 errores, `BUILD SUCCESS`.

- **Commit realizado:** `Agregar pruebas de autenticacion y autorizacion`.
- **Hash del commit:** `beb01504316d1588645fbb345bd55f0e7eee3430`.
- **Rama:** `feature/samuel-segundo-50`.
- **Push confirmado:** **NO**; permanece el bloqueo HTTP `403` del repositorio remoto.
- **Criterio de rúbrica relacionado:** autenticación y roles (10%), temáticas del curso, evidencia comprobable y calidad para la defensa.
- **Pendientes:** publicar commits al obtener permisos; ampliar pruebas cuando existan carrito, pedidos y solicitudes.
- **Estado del bloque:** **HECHO Y CONFIRMADO LOCALMENTE / PENDIENTE PUSH**.

## Bloque 4 — CRUD completo de categorías y productos

- **Fecha:** 17 de agosto de 2026.
- **Objetivo:** completar altas, consultas, modificaciones y eliminaciones controladas de categorías y productos, respetando la separación MVC estudiada.
- **Situación antes del cambio:** los repositorios y servicios tenían operaciones básicas; solo existía un listado técnico de productos. No había controladores/vistas administrativos, validación de duplicados ni protección de relaciones al borrar.

### Archivos creados

- `src/main/java/com/piscinas/gestion_piscinas/controller/CategoriaController.java`.
- `src/main/java/com/piscinas/gestion_piscinas/controller/ProductoAdministracionController.java`.
- `src/main/resources/templates/categorias/listado.html`.
- `src/main/resources/templates/categorias/formulario.html`.
- `src/main/resources/templates/productos/administracion.html`.
- `src/main/resources/templates/productos/formulario.html`.
- `src/test/java/com/piscinas/gestion_piscinas/CrudCategoriaProductoTests.java`.
- `src/test/java/com/piscinas/gestion_piscinas/CrudWebTests.java`.

### Archivos modificados

- `CategoriaRepository.java`, `ProductoRepository.java` y `DetallePedidoRepository.java`.
- `CategoriaServiceImpl.java` y `ProductoServiceImpl.java`.
- `ProductoController.java`.
- `templates/productos/listado.html`.
- `templates/administracion/inicio.html`.
- `docs/bitacora-integrante2.md`.

### Funcionalidad implementada

- CRUD administrativo de categorías y productos bajo `/administracion/**`.
- Formularios Thymeleaf con validaciones Jakarta y mensajes de campo.
- Nombres de categoría únicos sin distinguir mayúsculas/minúsculas.
- Alta y modificación de productos con precio, stock, descripción y categoría existente.
- Las modificaciones de producto preservan la fecha original de ingreso.
- La categoría se vuelve a consultar en el servidor; no se confía en un objeto enviado por el navegador.
- Una categoría con productos asociados no puede eliminarse.
- Un producto usado en un detalle de pedido no puede eliminarse.
- Eliminaciones mediante POST protegido por CSRF, no mediante enlaces GET.
- Catálogo público responsivo con cards, precios, existencias, agotados y listas vacías.
- Botones administrativos solo dentro de rutas protegidas y, en el catálogo, condicionados por Thymeleaf Security.

### Temas del curso relacionados

- MVC, Controller, Service, ServiceImpl, Repository, JPA/Hibernate, CRUD, `ManyToOne`, validaciones, Thymeleaf, Bootstrap, formularios y Spring Security.

### Explicación sencilla

El controlador recibe y valida el formulario. El servicio aplica las reglas del negocio y busca la categoría real. El repositorio guarda o consulta la información. Al borrar, el servicio pregunta primero si existen relaciones que deban conservarse y devuelve un mensaje claro en lugar de provocar un error de llave foránea.

### Pruebas ejecutadas y resultados

1. Categoría duplicada con diferente combinación de mayúsculas: **RECHAZADA / PASA**.
2. Eliminación de categoría asociada a un producto: **RECHAZADA / PASA**.
3. Creación y modificación de producto con fecha preservada: **PASA**.
4. Producto con categoría inexistente: **RECHAZADO / PASA**.
5. Catálogo público renderizado con Thymeleaf: **200 / PASA**.
6. Listados administrativos de categorías y productos: **200 para ADMINISTRADOR / PASA**.
7. Formularios inválidos devuelven errores de campo: **PASA**.
8. Suite limpia completa: 18 pruebas, 0 fallos, 0 errores, `BUILD SUCCESS`.

- **Commit realizado:** `Completar CRUD de categorias y productos con validaciones`.
- **Hash del commit:** `4890e386668990c7be830ad0fd2a16fe9094d9d3`.
- **Rama:** `feature/samuel-segundo-50`.
- **Push confirmado:** **NO**; permanece el bloqueo HTTP `403` del repositorio remoto.
- **Criterio de rúbrica relacionado:** uso medular de BD, temáticas del curso, autenticación/roles y diseño final.
- **Pendientes:** búsquedas/filtros JPQL, botones funcionales del carrito y publicación remota.
- **Estado del bloque:** **HECHO Y CONFIRMADO LOCALMENTE / PENDIENTE PUSH**.

## Bloque 5 — Búsquedas, filtros y consulta JPQL

- **Fecha:** 18 de agosto de 2026.
- **Objetivo:** agregar búsquedas útiles al catálogo y dejar evidencia diferenciada de consultas derivadas de Spring Data y una consulta JPQL explícita.
- **Situación antes del cambio:** el catálogo mostraba todos los productos, pero no permitía buscar por nombre, filtrar por categoría ni ocultar agotados.

### Archivos creados

- `src/test/java/com/piscinas/gestion_piscinas/BusquedaProductoTests.java`.

### Archivos modificados

- `src/main/java/com/piscinas/gestion_piscinas/repository/ProductoRepository.java`.
- `src/main/java/com/piscinas/gestion_piscinas/service/ProductoService.java`.
- `src/main/java/com/piscinas/gestion_piscinas/service/ProductoServiceImpl.java`.
- `src/main/java/com/piscinas/gestion_piscinas/controller/ProductoController.java`.
- `src/main/resources/templates/productos/listado.html`.
- `docs/bitacora-integrante2.md`.

### Funcionalidad implementada

- Búsqueda de productos por parte del nombre, sin distinguir mayúsculas/minúsculas.
- Filtro por categoría.
- Opción para mostrar solamente productos con stock mayor a cero.
- Combinación de nombre, categoría y disponibilidad desde un único formulario GET.
- Conservación visual de los filtros seleccionados.
- Botón para limpiar filtros.

### Consultas que evidencian el tema

- **Consulta derivada por nombre:** `findByNombreProductoContainingIgnoreCaseOrderByNombreProductoAsc`.
- **Consulta derivada por categoría:** `findByCategoriaIdCategoriaOrderByNombreProductoAsc`.
- **JPQL:** `ProductoRepository.buscarConFiltros`, con parámetros opcionales para nombre, categoría y productos disponibles, más ordenamiento alfabético.

### Temas del curso relacionados

- Spring Data JPA, consultas derivadas, JPQL, Repository, Service, MVC, formularios GET, Thymeleaf y Bootstrap.

### Explicación sencilla

El controlador recibe los filtros y los entrega al servicio. Cuando solo se busca por nombre o por categoría, el servicio usa métodos derivados. Cuando hay que combinar condiciones, utiliza una consulta JPQL pequeña que activa cada condición únicamente si el usuario la seleccionó.

### Pruebas ejecutadas y resultados

1. Búsqueda `cloro` devuelve dos coincidencias en orden alfabético mediante consulta derivada: **PASA**.
2. Filtro por categoría devuelve únicamente el producto relacionado mediante consulta derivada: **PASA**.
3. Filtro combinado JPQL devuelve el cloro disponible y excluye el agotado: **PASA**.
4. Suite limpia completa: 21 pruebas, 0 fallos, 0 errores, `BUILD SUCCESS`.

- **Commit realizado:** `Agregar busquedas y filtros con consultas JPA y JPQL`.
- **Hash del commit:** `3f211df301c2e63060a9df6d36b5d270ecbd0469`.
- **Rama:** `feature/samuel-segundo-50`.
- **Push confirmado:** **NO**; permanece el bloqueo HTTP `403` del repositorio remoto.
- **Criterio de rúbrica relacionado:** uso efectivo de BD, temáticas del curso y diseño/experiencia del catálogo.
- **Pendientes:** filtros administrativos de pedidos y solicitudes cuando esos módulos estén completos; publicación remota.
- **Estado del bloque:** **HECHO Y CONFIRMADO LOCALMENTE / PENDIENTE PUSH**.

## Bloque 6 — Gestión de servicios y solicitudes

- **Fecha:** 18 de agosto de 2026.
- **Objetivo:** implementar un catálogo real de servicios, su mantenimiento administrativo y una transacción de solicitud controlada por identidad y rol.
- **Situación antes del cambio:** las entidades y repositorios existían desde el modelo inicial, pero no tenían capa de servicio, controladores, vistas ni flujo utilizable.

### Archivos creados

- `domain/SolicitudServicioForm.java`.
- `service/ServicioService.java` y `ServicioServiceImpl.java`.
- `service/SolicitudServicioService.java` y `SolicitudServicioServiceImpl.java`.
- `controller/ServicioController.java`.
- `controller/ServicioAdministracionController.java`.
- `controller/SolicitudServicioController.java`.
- `controller/SolicitudAdministracionController.java`.
- `templates/servicios/listado.html`.
- `templates/servicios/administracion.html`.
- `templates/servicios/formulario.html`.
- `templates/solicitudes/formulario.html`.
- `templates/solicitudes/cliente-listado.html`.
- `templates/solicitudes/administracion.html`.
- `ServicioSolicitudTests.java`.
- `ServicioSolicitudWebTests.java`.

### Archivos modificados

- `ServicioRepository.java` y `SolicitudServicioRepository.java`.
- Fragmento de encabezado y paneles de cliente/administración.
- `docs/bitacora-integrante2.md`.

### Funcionalidad implementada

- Catálogo público que muestra únicamente servicios activos.
- CRUD administrativo de servicios con nombre, descripción, precio base y estado activo.
- Eliminación bloqueada si el servicio tiene historial de solicitudes; se recomienda desactivarlo.
- Formulario de solicitud disponible solo para CLIENTE.
- El servidor obtiene el cliente desde `Principal`, vuelve a consultar el servicio y exige que esté activo.
- Fecha y estado `PENDIENTE` definidos en servidor; el navegador no puede elegirlos.
- El cliente consulta exclusivamente solicitudes asociadas a su correo autenticado.
- El administrador consulta todas las solicitudes, filtra por estado y actualiza estados.
- Navegación y paneles actualizados según rol.

### Temas del curso relacionados

- MVC, Service/ServiceImpl, JPA, relaciones `ManyToOne`, consultas derivadas, formularios, validación, Thymeleaf, Bootstrap, Spring Security y transacciones.

### Explicación sencilla

El formulario del cliente solo contiene los datos que le corresponde decidir. Al guardar, la capa de servicio usa el correo autenticado para localizar su perfil, comprueba que el servicio exista y esté activo, y crea la solicitud pendiente. El administrador cambia su estado con una operación POST protegida por CSRF.

### Pruebas ejecutadas y resultados

1. Creación con cliente obtenido del servidor y estado PENDIENTE: **PASA**.
2. Solicitud de servicio inactivo: **RECHAZADA / PASA**.
3. Cambio y filtro por estado EN_PROCESO: **PASA**.
4. Eliminación de servicio con solicitudes: **RECHAZADA / PASA**.
5. Catálogo público de servicios: **200 / PASA**.
6. Vistas de solicitudes de CLIENTE: **200 / PASA**.
7. Vistas administrativas de servicios y solicitudes: **200 / PASA**.
8. Suite limpia completa: 28 pruebas, 0 fallos, 0 errores, `BUILD SUCCESS`.

- **Commit realizado:** `Implementar gestion de servicios y solicitudes`.
- **Hash del commit:** `a1942325a41a006fcd7db9312f54b44c32187ab2`.
- **Rama:** `feature/samuel-segundo-50`.
- **Push confirmado:** **NO**; permanece el bloqueo HTTP `403` del repositorio remoto.
- **Criterio de rúbrica relacionado:** almacenamiento transaccional, uso efectivo de BD, autenticación/roles, temáticas del curso y solución para un cliente potencial.
- **Pendientes:** cancelar solicitud por su propio cliente si se define como necesario, internacionalizar estados y publicar remotamente.
- **Estado del bloque:** **HECHO Y CONFIRMADO LOCALMENTE / PENDIENTE PUSH**.

## Bloque 7 — Carrito de compras basado en sesión

- **Fecha:** 18 de agosto de 2026.
- **Objetivo:** implementar el carrito por sesión visto en el curso, validando productos, cantidades, existencias y precios exclusivamente en el servidor.
- **Situación antes del cambio:** la navegación mencionaba el carrito, pero no existían modelo de sesión, servicio, controlador, vista ni operaciones.

### Archivos creados

- `domain/CarritoSesion.java`.
- `domain/ItemCarrito.java`.
- `domain/ResumenCarrito.java`.
- `service/CarritoService.java` y `CarritoServiceImpl.java`.
- `controller/CarritoController.java`.
- `templates/carrito/ver.html`.
- `CarritoTests.java`.
- `CarritoWebTests.java`.

### Archivos modificados

- `templates/productos/listado.html`.
- `docs/bitacora-integrante2.md`.

### Funcionalidad implementada

- Carrito almacenado como atributo de sesión con mapa `idProducto -> cantidad`.
- Agregar, aumentar, reducir, eliminar y vaciar mediante operaciones POST con CSRF.
- Validación de producto existente, cantidad mayor a cero y cantidad acumulada no superior al stock.
- El carrito no almacena ni recibe precios del navegador; reconstruye cada resumen desde la base de datos.
- Subtotales por línea, cantidad total de unidades y subtotal general calculados en servidor.
- Detección de cambios de stock posteriores a la adición.
- Productos eliminados de la base se retiran de forma segura del resumen de sesión.
- Catálogo con botón real de agregar únicamente para CLIENTE y deshabilitado cuando no hay stock.
- Vista responsiva con controles de cantidad, estado de inventario, lista vacía y mensajes.

### Temas del curso relacionados

- Variables de sesión HTTP, carrito de compras, MVC, Service, JPA, Thymeleaf, formularios POST, CSRF, Bootstrap y validaciones de negocio.

### Explicación sencilla

La sesión conserva únicamente identificadores y cantidades. Cada vez que se muestra o cambia el carrito, el servicio consulta el producto real. De esta manera, un usuario no puede enviar un precio falso desde HTML, agregar cantidades negativas ni superar las existencias conocidas.

### Pruebas ejecutadas y resultados

1. Adición y subtotal con precio de BD: **PASA**.
2. Cantidad cero o mayor al stock: **RECHAZADA / PASA**.
3. Aumentar, reducir, eliminar y vaciar: **PASA**.
4. Cambio de stock posterior detectado: **PASA**.
5. Petición web incluye `precio=0.01`, pero el subtotal conserva `₡7.250,00` de BD: **PASA**.
6. Vista del carrito renderizada para CLIENTE: **200 / PASA**.
7. Suite limpia completa: 33 pruebas, 0 fallos, 0 errores, `BUILD SUCCESS`.

- **Commit realizado:** `Implementar carrito de compras basado en sesion`.
- **Hash del commit:** `c9f5ea48f0017f372cbbda17d6eb34d7cc22f193`.
- **Rama:** `feature/samuel-segundo-50`.
- **Push confirmado:** **NO**; permanece el bloqueo HTTP `403` del repositorio remoto.
- **Criterio de rúbrica relacionado:** carrito de compras como temática del curso, autenticación por rol, diseño y base para la transacción real de pedido.
- **Pendientes:** habilitar finalizar compra en el siguiente bloque, mostrar contador global y publicar remotamente.
- **Estado del bloque:** **HECHO Y CONFIRMADO LOCALMENTE / PENDIENTE PUSH**.

## Bloque 8 — Creación transaccional de pedidos e inventario

- **Fecha:** 18 de agosto de 2026.
- **Objetivo:** convertir el carrito en una compra persistida y atómica, guardando precio histórico, calculando totales y descontando inventario sin resultados parciales.
- **Situación antes del cambio:** el carrito estaba completo, pero el botón de compra estaba deshabilitado y no existía servicio transaccional ni confirmación de pedido.

### Archivos creados

- `service/PedidoService.java` y `PedidoServiceImpl.java`.
- `controller/PedidoController.java`.
- `templates/pedidos/detalle.html`.
- `PedidoTransaccionTests.java`.
- `PedidoWebTests.java`.

### Archivos modificados

- `ProductoRepository.java`.
- `PedidoRepository.java`.
- `application.properties` principal y de prueba.
- `templates/carrito/ver.html`.
- `docs/bitacora-integrante2.md`.

### Funcionalidad implementada

- Finalización completa dentro de un método `@Transactional` en la capa Service.
- Cliente obtenido desde el correo autenticado.
- Validación integral del carrito antes de crear registros.
- Productos consultados nuevamente con bloqueo de escritura durante la transacción.
- Validación de cantidades y existencias actuales.
- Precio unitario recuperado desde BD y conservado en `DetallePedido`.
- Subtotal de línea, subtotal del pedido, impuesto y total calculados en servidor con redondeo a dos decimales.
- Creación de `Pedido` en estado PENDIENTE y sus `DetallePedido`.
- Descuento de stock dentro de la misma transacción.
- El carrito se vacía únicamente después de que el servicio retorna con éxito.
- Vista de confirmación/detalle y verificación de pertenencia en servidor.
- Un cliente no puede obtener un pedido ajeno cambiando el ID de la URL.

### Supuesto tributario centralizado

Se configuró `tienda.impuesto.tasa=0.13`. La fuente oficial consultada fue [Tarifas del Impuesto sobre el Valor Agregado — Ministerio de Hacienda de Costa Rica](https://www.hacienda.go.cr/docs/TarifasdelIVA.pdf), consultada el 18 de agosto de 2026, que identifica el 13% como tarifa general.

Este proyecto académico aplica esa tarifa general a los productos del catálogo. Bienes exentos o con tarifa reducida requieren validación fiscal antes de un uso comercial real.

### Temas del curso relacionados

- Transacciones Spring, JPA/Hibernate, relaciones, carrito, Service, inventario, persistencia, seguridad, Thymeleaf y reglas de negocio.

### Explicación sencilla

Antes de guardar, el servicio bloquea y revisa todos los productos. Si cualquiera falla, lanza una excepción y la transacción no crea pedido, detalles ni descuentos parciales. Si todo es válido, guarda la cabecera, las líneas con el precio de ese momento y reduce el stock. El controlador elimina el carrito de sesión solamente tras recibir el pedido creado.

### Pruebas ejecutadas y resultados

1. Compra exitosa con dos productos: **PASA**.
2. Pedido en estado PENDIENTE y dos detalles creados: **PASA**.
3. Precio histórico guardado: **PASA**.
4. Subtotal `₡25.000,00`, IVA `₡3.250,00`, total `₡28.250,00`: **PASA**.
5. Inventario descontado exactamente: **PASA**.
6. Stock insuficiente: **RECHAZADO / PASA**.
7. Sin pedido ni detalles parciales y stock sin alteración: **PASA**.
8. Pedido ajeno por ID: **RECHAZADO / PASA**.
9. Confirmación web vacía carrito solo tras éxito y renderiza detalle: **PASA**.
10. Suite limpia completa: 37 pruebas, 0 fallos, 0 errores, `BUILD SUCCESS`.

- **Commit realizado:** `Implementar creacion transaccional de pedidos e inventario`.
- **Hash del commit:** `e055b16b368015e967b0c2eaa58fc564a15e6f85`.
- **Rama:** `feature/samuel-segundo-50`.
- **Push confirmado:** **NO**; permanece el bloqueo HTTP `403` del repositorio remoto.
- **Criterio de rúbrica relacionado:** tabla transaccional real, uso medular de BD, transacciones/rollback, solución real y temáticas del curso.
- **Pendientes:** historial completo y administración de estados en el siguiente bloque; validación tributaria comercial; publicación remota.
- **Estado del bloque:** **HECHO Y CONFIRMADO LOCALMENTE / PENDIENTE PUSH**.

## Bloque 9 — Historial de pedidos y gestión administrativa de estados

- **Fecha:** 18 de agosto de 2026.
- **Objetivo:** permitir que cada cliente consulte únicamente sus compras y que el administrador revise todos los pedidos y actualice su estado.
- **Situación antes del cambio:** el pedido podía crearse y mostrarse al finalizar la compra, pero no existía un historial persistente accesible ni una gestión administrativa de estados.

### Archivos creados

- `controller/PedidoAdministracionController.java`.
- `templates/pedidos/cliente-listado.html`.
- `templates/pedidos/administracion.html`.
- `PedidoHistorialTests.java`.
- `PedidoHistorialWebTests.java`.

### Archivos modificados

- `controller/PedidoController.java`.
- `repository/PedidoRepository.java`.
- `service/PedidoService.java` y `PedidoServiceImpl.java`.
- `templates/pedidos/detalle.html`.
- `templates/fragmentos/encabezado.html`.
- `templates/cliente/inicio.html`.
- `templates/administracion/inicio.html`.
- `docs/bitacora-integrante2.md`.

### Funcionalidad implementada

- Historial del CLIENTE ordenado por fecha, obtenido siempre a partir de su correo autenticado.
- Detalle protegido por pertenencia: un cliente no puede consultar el pedido de otra cuenta modificando el ID.
- Listado administrativo de todos los pedidos.
- Filtro administrativo por estado mediante consulta JPA.
- Vista administrativa del detalle del pedido.
- Actualización de estado mediante POST y únicamente bajo ruta ADMINISTRADOR.
- Enlaces de navegación para “Mis pedidos” y “Gestionar pedidos” según rol.
- Estados disponibles limitados al enum del dominio: PENDIENTE, CONFIRMADO, PREPARANDO, COMPLETADO y CANCELADO.

### Temas del curso relacionados

- Spring MVC, Spring Security, JPA, Service, consultas derivadas, Thymeleaf, formularios POST, CSRF y Bootstrap.

### Explicación sencilla

El controlador nunca acepta como verdad la identidad enviada por el navegador. Para el cliente toma el correo de la sesión autenticada y solicita al servicio solamente sus pedidos. La administración utiliza una ruta protegida distinta, puede filtrar todos los pedidos y cambiar el estado mediante una operación POST.

### Pruebas ejecutadas y resultados

1. Cliente obtiene únicamente su propio historial: **PASA**.
2. Cliente abre el detalle de un pedido propio: **PASA**.
3. Pedido ajeno continúa protegido: **PASA** dentro de la regresión completa.
4. Administrador lista todos los pedidos: **PASA**.
5. Administrador filtra por estado: **PASA**.
6. Administrador actualiza el estado a CONFIRMADO: **PASA**.
7. Vistas web de historial de cliente y administración: **200 / PASA**.
8. Suite limpia completa: 41 pruebas, 0 fallos, 0 errores, `BUILD SUCCESS`.

- **Commit realizado:** `Agregar historial de pedidos y gestion administrativa de estados`.
- **Hash del commit:** `2c5dfc4e0c329aee55a8d51ec33db3aadeda877d`.
- **Rama:** `feature/samuel-segundo-50`.
- **Push confirmado:** **NO**; permanece el bloqueo HTTP `403` del repositorio remoto.
- **Criterio de rúbrica relacionado:** autenticación y roles, base de datos medular, transacciones reales, seguridad de acciones, diseño usable y temática Spring MVC/JPA.
- **Pendientes:** publicar cuando la cuenta tenga permiso remoto.
- **Estado del bloque:** **HECHO Y CONFIRMADO LOCALMENTE / PENDIENTE PUSH**.

## Bloque 10 — Manejo de errores y páginas de estado

- **Fecha:** 18 de agosto de 2026.
- **Objetivo:** ofrecer respuestas comprensibles y coherentes para acceso denegado, recursos inexistentes y errores inesperados, sin introducir una arquitectura de excepciones innecesaria.
- **Situación antes del cambio:** existía una página 403 básica, pero no había páginas 404/500, prueba explícita del recurso inexistente ni verificación del despacho de acceso denegado.

### Archivos creados

- `templates/error/404.html`.
- `templates/error/500.html`.
- `ManejoErroresTests.java`.

### Archivos modificados

- `controller/InicioController.java`.
- `application.properties`.
- `docs/bitacora-integrante2.md`.

### Funcionalidad implementada

- Página personalizada 403 para acciones sin permiso.
- Página personalizada 404 para rutas o recursos inexistentes.
- Página personalizada 500 para errores inesperados.
- Desactivación de la página blanca genérica mediante `server.error.whitelabel.enabled=false`.
- Accesos de demostración a las vistas de error para comprobar su presentación.
- Conservación de los mensajes de negocio ya controlados para producto inexistente, categoría asociada, stock insuficiente y pedido ajeno.
- Uso del resolvedor de errores de Spring Boot y plantillas Thymeleaf, como alternativa mínima compatible con la estructura del curso.

### Temas del curso relacionados

- Spring MVC, Spring Security, controladores, Thymeleaf, fragmentos, Bootstrap y pruebas MockMvc.

### Explicación sencilla

Spring Boot selecciona automáticamente una plantilla según el código HTTP. La aplicación aporta las páginas `403`, `404` y `500` con navegación segura para que el usuario no vea la pantalla genérica. Los errores esperados de negocio siguen convirtiéndose en mensajes específicos en sus controladores, sin mostrar detalles internos.

### Pruebas ejecutadas y resultados

1. CLIENTE abre `/administracion`: **403 / PASA**.
2. El despacho de seguridad utiliza `/error/403`: **PASA**.
3. Usuario autenticado abre una ruta inexistente: **404 / PASA**.
4. Página 403 renderiza mensaje comprensible: **PASA**.
5. Página 404 renderiza mensaje y navegación: **PASA**.
6. Página 500 renderiza mensaje seguro: **PASA**.
7. Suite limpia completa: 44 pruebas, 0 fallos, 0 errores, `BUILD SUCCESS`.

- **Commit realizado:** `Agregar manejo de errores y paginas de estado`.
- **Hash del commit:** `3658938f44ec9a3346331998e4f993bb60410594`.
- **Rama:** `feature/samuel-segundo-50`.
- **Push confirmado:** **NO**; permanece el bloqueo HTTP `403` del repositorio remoto.
- **Criterio de rúbrica relacionado:** diseño final, seguridad, solución usable, pruebas y temáticas Spring MVC/Thymeleaf.
- **Pendientes:** crear commit, publicar cuando exista permiso remoto e internacionalizar los textos de error.
- **Estado del bloque:** **HECHO Y CONFIRMADO LOCALMENTE / PENDIENTE PUSH**.

## Bloque 11 — Internacionalización en español e inglés

- **Fecha:** 18 de agosto de 2026.
- **Objetivo:** integrar un cambio de idioma visible y persistente que cubra los flujos principales y aporte evidencia directa al 8% de internacionalización de la rúbrica.
- **Situación antes del cambio:** todos los textos estaban escritos directamente en español, no existían archivos `messages`, selector, configuración de locale ni pruebas bilingües.

### Archivos creados

- `config/InternacionalizacionConfig.java`.
- `resources/messages.properties`.
- `resources/messages_es.properties`.
- `resources/messages_en.properties`.
- `InternacionalizacionTests.java`.

### Archivos modificados

- Entidades/formularios visibles: `Categoria`, `Producto`, `RegistroUsuario`, `Servicio` y `SolicitudServicioForm`.
- Controladores con confirmaciones: carrito, categorías, productos, servicios, solicitudes, pedidos y registro.
- Fragmentos comunes de encabezado y pie de página.
- Vistas de inicio, seguridad, administración, cliente, categorías, productos, servicios, solicitudes, carrito, pedidos y errores.
- `docs/bitacora-integrante2.md`.

### Funcionalidad implementada

- Locale español predeterminado mediante `SessionLocaleResolver`.
- Cambio con parámetro `lang` y `LocaleChangeInterceptor`.
- Selector Español/English visible en la barra de navegación y persistencia del idioma en sesión.
- Archivos de mensajes separados para español e inglés, más archivo base según el patrón de clase.
- Navegación adaptada por rol y traducida.
- Encabezados, botones, filtros, formularios, listas vacías, confirmaciones y páginas de error traducidos.
- Login, registro, carrito, categorías, productos, servicios, solicitudes y pedidos traducidos.
- Estados de pedidos y solicitudes resueltos dinámicamente desde las claves `estado.*`.
- Validaciones visibles de registro, categorías, productos, servicios y solicitudes trasladadas a claves de mensajes.
- Confirmaciones de operaciones guardadas como códigos y resueltas según el locale al renderizar.

> Funcionalidad incorporada por requisito explícito de la entrega final; implementación realizada con la alternativa mínima compatible con la estructura estudiada.

### Temas del curso relacionados

- Internacionalización, archivos `messages`, locale, variables de sesión, Thymeleaf, fragmentos, formularios, validaciones y Bootstrap.

### Explicación sencilla

Al seleccionar un idioma, un interceptor guarda el locale en la sesión. Thymeleaf obtiene cada texto desde `messages_es.properties` o `messages_en.properties`; las reglas y rutas permanecen iguales. Los estados y validaciones usan claves para que no dependan de texto fijo en español.

### Pruebas ejecutadas y resultados

1. Español se utiliza de forma predeterminada: **PASA**.
2. `?lang=en` cambia la página a inglés: **PASA**.
3. El cambio a inglés permanece al navegar a login: **PASA**.
4. Navegación y selector muestran textos en inglés: **PASA**.
5. Validaciones de nombre y contraseña se muestran en inglés: **PASA**.
6. Confirmación del carrito se muestra en inglés: **PASA**.
7. Renderizado de vistas administrativas y de cliente dentro de la regresión: **PASA**.
8. Suite limpia completa: 48 pruebas, 0 fallos, 0 errores, `BUILD SUCCESS`.

- **Commit realizado:** `Integrar internacionalizacion en español e ingles`.
- **Hash del commit:** `5d0bc4dda6f4d35ed56a37b04fcb6ceedc439ca0`.
- **Rama:** `feature/samuel-segundo-50`.
- **Push confirmado:** **NO**; permanece el bloqueo HTTP `403` del repositorio remoto.
- **Criterio de rúbrica relacionado:** internacionalización — 8%, diseño final, temáticas del curso y presentación/defensa.
- **Pendientes:** crear commit, publicar cuando exista permiso remoto y ampliar en el bloque visual los textos comerciales secundarios de la portada.
- **Estado del bloque:** **HECHO Y CONFIRMADO LOCALMENTE / PENDIENTE PUSH**.

## Bloque 12 — Interfaz comercial y navegación de Tienda Piscinas

- **Fecha:** 18 de agosto de 2026.
- **Objetivo:** transformar la portada estática en una entrada comercial conectada a los módulos reales, conservar Bootstrap/Thymeleaf y aplicar una identidad visual coherente con piscinas.
- **Situación antes del cambio:** la portada contenía productos y un formulario de contacto decorativos sin conexión al backend; enlazaba `/css/estilos.css`, pero el archivo y la carpeta `static` no existían.

### Archivos creados

- `static/css/estilos.css`.
- `DisenoComercialTests.java`.

### Archivos modificados

- `controller/InicioController.java`.
- `templates/index.html`.
- Archivos `messages_es.properties` y `messages_en.properties`.
- Vistas principales para cargar la hoja de estilos compartida.
- `docs/bitacora-integrante2.md`.

### Funcionalidad implementada

- Portada con hero, llamados a acción, categorías, productos, servicios, beneficios y cierre comercial.
- Categorías, productos disponibles y servicios activos recuperados desde sus capas Service y limitados a una selección destacada.
- Cards responsivas Bootstrap con precio, stock, categoría y acciones según rol.
- Agregar al carrito visible para CLIENTE e inicio de sesión para invitado.
- Se eliminaron el formulario y los elementos estáticos que aparentaban operaciones no implementadas.
- Paleta azul, celeste y turquesa, contrastes, sombras discretas y estados visuales.
- Hoja de estilos compartida en todas las vistas principales.
- Navegación y contenido comercial conservan traducción español/inglés.
- Corrección del enlace roto preexistente a `/css/estilos.css`.

### Temas del curso relacionados

- HTML5, CSS, Bootstrap, Spring MVC, Service, Thymeleaf, fragmentos, JPA, Spring Security visual e internacionalización.

### Explicación sencilla

El controlador consulta las mismas capas Service usadas por los CRUD y entrega una selección a Thymeleaf. La portada ya no presenta productos inventados: si existen registros disponibles los muestra; si no, muestra un estado vacío. Bootstrap organiza las secciones y el CSS compartido aporta la identidad acuática.

### Pruebas ejecutadas y resultados

1. Compilación limpia y regresión completa: **PASA**.
2. Portada con producto, categoría y servicio persistidos dentro de una prueba: **PASA**.
3. Acción “Agregar al carrito” visible para CLIENTE: **PASA**.
4. Enlace a `/css/estilos.css`: **PASA**.
5. Recurso CSS accesible públicamente y contiene estilos del hero: **PASA**.
6. Inicio temporal real con H2 en puerto 8087: **PASA**.
7. Suite limpia completa: 50 pruebas, 0 fallos, 0 errores, `BUILD SUCCESS`.
8. Verificación visual automatizada con Browser: **BLOQUEADA POR EL ENTORNO**; el complemento rechazó su módulo interno por restricción de ruta confiable. No se registran resultados visuales inventados.

- **Commit realizado:** `Mejorar interfaz comercial y navegacion de Tienda Piscinas`.
- **Hash del commit:** `bfe01d50b0bc5c6c1e315117d4c4ba8bc0b80982`.
- **Rama:** `feature/samuel-segundo-50`.
- **Push confirmado:** **NO**; permanece el bloqueo HTTP `403` del repositorio remoto.
- **Criterio de rúbrica relacionado:** diseño final — 10%, solución real, internacionalización, temáticas del curso y defensa.
- **Pendientes:** publicar cuando exista permiso remoto y realizar revisión visual manual real en tamaños móvil/escritorio antes de la entrega.
- **Estado del bloque:** **HECHO, PROBADO Y CONFIRMADO LOCALMENTE / PENDIENTE PUSH Y QA VISUAL MANUAL**.

## Bloque 13 — Cotización estimada según volumen y estado del agua

- **Fecha:** 18 de agosto de 2026.
- **Objetivo:** incorporar la funcionalidad investigada de la rúbrica mediante una herramienta útil que calcula volumen, orienta al usuario y relaciona la necesidad con servicios activos del catálogo.
- **Situación antes del cambio:** el sistema permitía consultar y solicitar servicios, pero no ayudaba a estimar el tamaño de la piscina ni a relacionar una condición general con el catálogo. Tampoco existía documento de investigación adicional.

### Archivos creados

- `domain/EstadoAgua.java`.
- `domain/CotizacionPiscinaForm.java`.
- `domain/CotizacionPiscinaResultado.java`.
- `service/CotizacionPiscinaService.java`.
- `service/CotizacionPiscinaServiceImpl.java`.
- `controller/CotizacionPiscinaController.java`.
- `templates/cotizacion/formulario.html`.
- `CotizacionPiscinaTests.java`.
- `CotizacionPiscinaWebTests.java`.
- `docs/investigacion-cotizacion.md`.

### Archivos modificados

- `config/SecurityConfig.java`.
- `templates/fragmentos/encabezado.html`.
- `templates/fragmentos/piePagina.html`.
- `messages_es.properties`.
- `messages_en.properties`.
- `docs/bitacora-integrante2.md`.

### Funcionalidad implementada

- Ruta pública `/cotizador` integrada a la navegación principal y al pie de página.
- Formulario validado para largo, ancho, profundidad promedio y estado/necesidad principal.
- Cálculo servidor de volumen en metros cúbicos y litros para piscinas rectangulares.
- Clasificación simple de agua clara, turbia, verde o problema de equipo.
- Recomendación inicial bilingüe según la necesidad elegida.
- Búsqueda de un servicio activo relacionado a partir de nombre y descripción persistidos.
- Estimación basada en el precio real del servicio y factores internos centralizados por volumen y estado.
- Resultado sin precio cuando no hay un servicio relacionado; no se inventan registros ni valores.
- Advertencia expresa: la herramienta no diagnostica ni prescribe dosificaciones químicas.
- Documento con fuentes, fórmula, algoritmo, supuestos, limitaciones y casos de prueba.
- Separación explícita entre hechos sustentados por NatHERS/CDC y reglas comerciales académicas pendientes de validación real.

> Funcionalidad incorporada por requisito explícito de la entrega final; implementación realizada con la alternativa mínima compatible con la estructura estudiada.

### Temas del curso relacionados

- Spring MVC, Controller/Service, formularios, Bean Validation, Thymeleaf, Bootstrap, Spring Security, JPA mediante servicios activos, internacionalización y pruebas MockMvc.

### Explicación sencilla

El usuario ingresa las dimensiones y selecciona el estado general. El servicio calcula `largo × ancho × profundidad promedio`, convierte el resultado a litros y busca un servicio activo relacionado. Si lo encuentra, multiplica su precio base por factores visibles y documentados. La vista presenta el cálculo como estimación, nunca como diagnóstico químico.

### Investigación y fuentes

- NatHERS, *Whole of Home Calculations Method* (ecuación 94): fórmula de volumen a partir de área y profundidad promedio.
- CDC, *Home Pool and Hot Tub Water Treatment and Testing*: pruebas periódicas de pH/desinfectante y seguimiento de instrucciones del fabricante.
- CDC, *Guidelines for Keeping Your Pool Safe and Healthy*: importancia de pH/desinfectante y manejo seguro conforme a etiquetas.
- Fecha de consulta: 18 de agosto de 2026.
- Detalle completo y enlaces: `docs/investigacion-cotizacion.md`.

### Pruebas ejecutadas y resultados

1. Piscina de 10 × 4 × 1.25 m: 50 m³ y 50 000 L: **PASA**.
2. Agua verde, servicio base ₡20 000 y factores 1.25 × 1.30: total estimado ₡32 500: **PASA**.
3. Límite de 30 m³ conserva factor de volumen 1.00: **PASA**.
4. Ausencia de servicio conserva volumen y no inventa precio: **PASA**.
5. Invitado abre el cotizador público: **PASA**.
6. Envío válido renderiza resultado y advertencia: **PASA**.
7. Dimensiones inválidas muestran validaciones: **PASA**.
8. Vista del cotizador en inglés: **PASA**.
9. Suite limpia completa: 57 pruebas, 0 fallos, 0 errores, `BUILD SUCCESS`.

- **Commit realizado:** `Implementar cotizacion inteligente para piscinas`.
- **Hash del commit:** `1bb90862e7d8af08aa3671271ad1e2a6cfba514b`.
- **Rama:** `feature/samuel-segundo-50`.
- **Push confirmado:** **NO**; permanece el bloqueo HTTP `403` del repositorio remoto.
- **Criterio de rúbrica relacionado:** investigación adicional — 7%, solución real, uso de base de datos, internacionalización, diseño y temáticas del curso.
- **Pendientes:** publicar cuando exista permiso remoto, validar los factores de precio con un cliente real y ampliar a otras formas solamente si el equipo lo requiere.
- **Estado del bloque:** **HECHO, PROBADO Y CONFIRMADO LOCALMENTE / PENDIENTE PUSH**.

## Bloque 14 — Configuración segura y script oficial de MySQL

- **Fecha:** 18 de agosto de 2026.
- **Objetivo:** retirar credenciales fijas, hacer reproducible la conexión local y consolidar un esquema MySQL coherente con las entidades finales y datos demostrativos seguros.
- **Situación antes del cambio:** `application.properties` guardaba `usuario/contrasenna`, usaba el puerto 80 y mostraba SQL; no existía `.gitignore`; había dos scripts fuente y una copia antigua dentro de `target` que creaba un usuario MySQL con contraseña fija. Además, 24 archivos generados de `target` estaban rastreados por Git.

### Archivos creados

- `.gitignore`.
- `proyecto_piscinas/.env.example`.
- `docs/configuracion-mysql.md`.
- `ConfiguracionMySqlTests.java`.

### Archivos modificados

- `src/main/resources/application.properties`.
- `proyecto_piscinas/piscinas_script.sql`.
- `docs/bitacora-integrante2.md`.

### Archivos retirados del código fuente o del índice

- `src/main/resources/piscinas_script.sql`: copia duplicada eliminada para mantener una sola fuente oficial.
- 24 archivos de `proyecto_piscinas/target`: retirados únicamente del índice con `git rm --cached`; los archivos físicos existentes se preservaron y la carpeta queda ignorada en adelante.

### Funcionalidad implementada

- `DB_URL`, `DB_USER`, `DB_PASSWORD` y `SERVER_PORT` configurables mediante variables de entorno.
- Contraseña MySQL sin valor fijo en el repositorio.
- Puerto local predeterminado 8080 y SQL de Hibernate oculto en ejecución normal.
- `.env` real ignorado y `.env.example` conservado como referencia, con advertencia de que Spring no lo carga automáticamente.
- Único script oficial: `proyecto_piscinas/piscinas_script.sql`.
- 13 tablas funcionales con claves primarias, foráneas, validaciones e índices.
- Roles, usuarios demo con hashes BCrypt, cliente, categorías, productos, servicios, piscina, solicitud y pedido con detalle ficticios.
- Pedido demostrativo insertado dentro de una transacción SQL y ajuste coherente de stock.
- El script no crea usuarios MySQL, no concede privilegios y no guarda credenciales personales.
- Guía PowerShell, ejecución con Workbench, variables, usuarios demo y consultas de comprobación.

### Temas del curso relacionados

- MySQL, SQL, JPA/Hibernate, relaciones, transacciones, configuración Spring Boot, Spring Security y BCrypt.

### Explicación sencilla

Cada integrante define su propia conexión MySQL en variables de entorno. La aplicación usa esos valores al iniciar y el repositorio no conoce la contraseña personal. El script oficial reconstruye una base demostrativa coherente, mientras que las salidas generadas por Maven dejan de formar parte del historial Git.

### Pruebas ejecutadas y resultados

1. Propiedades principales usan variables y no contienen `spring.datasource.password=contrasenna`: **PASA**.
2. Script contiene exactamente 13 sentencias `CREATE TABLE`: **PASA**.
3. Script no contiene `CREATE USER` ni `IDENTIFIED BY`: **PASA**.
4. Hash BCrypt de ADMINISTRADOR coincide con su clave demo: **PASA**.
5. Hash BCrypt de CLIENTE coincide con su clave demo: **PASA**.
6. Los 24 archivos físicos de `target` permanecen en disco después de retirarlos del índice: **PASA**.
7. Reglas de `.gitignore` excluyen `target` y `.env`, pero permiten `.env.example`: **PASA**.
8. Suite limpia completa: 60 pruebas, 0 fallos, 0 errores, `BUILD SUCCESS`.
9. Conexión de solo lectura a MySQL 8.4 local sin contraseña: **NO EJECUTADA CON ÉXITO**; el servidor respondió `Access denied for user 'root'@'localhost'`. No se intentó adivinar ni solicitar una clave personal.
10. Ejecución destructiva del script sobre `gestion_piscinas`: **PENDIENTE**; no se sobrescribió una base local potencialmente perteneciente al equipo sin una copia o instancia aislada autorizada.

- **Commit realizado:** pendiente al momento de redactar; mensaje previsto `Actualizar configuracion MySQL y script de base de datos`.
- **Hash del commit:** se registrará en la siguiente actualización.
- **Rama:** `feature/samuel-segundo-50`.
- **Push confirmado:** **NO**; permanece el bloqueo HTTP `403` del repositorio remoto.
- **Criterio de rúbrica relacionado:** almacenamiento — 10%, uso efectivo de base de datos — 10%, autenticación, solución reproducible y GitHub.
- **Pendientes:** crear commit, publicar cuando exista permiso remoto y ejecutar el script en una instancia MySQL limpia o respaldada con una credencial proporcionada por el equipo.
- **Estado del bloque:** **HECHO Y PROBADO ESTÁTICAMENTE / PENDIENTE COMMIT, PUSH Y PRUEBA MYSQL LIMPIA**.

## Checklist oficial del Integrante 2

### Prioridad 1 — Seguridad y usuarios

- [ ] Integrar Spring Security. Implementado y probado localmente; pendiente push.
- [ ] Implementar registro. Implementado y probado localmente; pendiente push.
- [ ] Implementar inicio de sesión. Implementado y probado con MockMvc localmente; pendiente push.
- [ ] Implementar cierre de sesión. Implementado localmente; pendiente prueba MockMvc de logout y push.
- [ ] Usar BCrypt. Implementado y probado localmente; pendiente push.
- [ ] Crear rol ADMINISTRADOR. Implementado y probado localmente; pendiente push.
- [ ] Crear rol CLIENTE. Implementado y probado localmente; pendiente push.
- [ ] Restringir menú. Implementado localmente; pendiente prueba de vista y push.
- [ ] Restringir rutas. Implementado y probado con MockMvc localmente; pendiente push.
- [ ] Restringir acciones. Implementado localmente; pendiente completar los CRUD y push.
- [ ] Administración solo ADMINISTRADOR. Implementado y probado con MockMvc localmente; pendiente push.
- [ ] Compra para CLIENTE. Implementada y probada localmente; pendiente push.
- [ ] Historial para CLIENTE. Implementado y probado localmente; pendiente push.
- [ ] Usuarios de prueba. Implementados con BCrypt localmente; pendiente push y SQL final.
- [ ] Credenciales demo documentadas. Documentadas localmente; pendiente push.
- [ ] Pruebas de acceso permitido. Implementadas y aprobadas localmente; pendiente push.
- [ ] Pruebas de acceso denegado. Implementadas y aprobadas localmente; pendiente push.

### Prioridad 2 — CRUD y persistencia

- [ ] Usuario.
- [ ] Rol.
- [ ] Cliente.
- [ ] Servicio. Módulo completo y probado localmente; pendiente push.
- [ ] SolicitudServicio. Flujo completo y probado localmente; pendiente push.
- [ ] Pedido. Persistencia, flujo e historial implementados localmente; pendiente push.
- [ ] DetallePedido. Persistencia con precio histórico implementada localmente; pendiente push.
- [ ] CRUD Categoria. Implementado y probado localmente; pendiente push.
- [ ] CRUD Producto. Implementado y probado localmente; pendiente push.
- [ ] Validaciones. Implementadas y probadas localmente; pendiente push.
- [ ] Servicios. CRUD y catálogo aprobados localmente; pendiente push.
- [ ] Solicitudes. Cliente y administración aprobados localmente; pendiente push.
- [ ] Búsqueda. Implementada y probada localmente; pendiente push.
- [ ] Filtros. Implementados y probados localmente; pendiente push.
- [ ] JPQL/consultas. Consulta JPQL y derivadas aprobadas localmente; pendiente push.
- [x] Separación Controller/Service/Repository existente parcialmente.
- [ ] Separación Controller/Service/Repository completa.
- [ ] 403. Página y prueba aprobadas localmente; pendiente push.
- [ ] 404. Página y prueba aprobadas localmente; pendiente push.
- [ ] 500. Página y renderizado aprobados localmente; pendiente push.

### Prioridad 3 — Transacciones

- [ ] Carrito: agregar, incrementar, reducir, eliminar y vaciar. Aprobado localmente; pendiente push.
- [ ] Validar existencias, cantidades y precios en servidor. Aprobado localmente; pendiente push.
- [ ] Crear Pedido y DetallePedido. Implementado y probado localmente; pendiente push.
- [ ] Transacción y rollback. Aprobado sin registros parciales localmente; pendiente push.
- [ ] Descontar inventario. Implementado y probado localmente; pendiente push.
- [ ] Calcular subtotal, impuesto y total. Implementado y probado localmente; pendiente push.
- [ ] Historial cliente. Implementado y probado localmente; pendiente push.
- [ ] Gestión de estados. Implementada y probada para ADMINISTRADOR localmente; pendiente push.
- [ ] Prueba compra exitosa. Aprobada localmente; pendiente push.
- [ ] Prueba stock insuficiente. Aprobada localmente; pendiente push.
- [ ] Prueba rollback. Aprobada sin registros parciales localmente; pendiente push.

### Prioridad 4 — Internacionalización e investigación

- [ ] `messages_es.properties`. Implementado y probado localmente; pendiente push.
- [ ] `messages_en.properties`. Implementado y probado localmente; pendiente push.
- [ ] Navegación, formularios, validaciones y estados traducidos. Aprobado localmente; pendiente push.
- [ ] Selector de idioma. Persistencia en sesión aprobada localmente; pendiente push.
- [ ] Cotización inteligente. Implementada y aprobada localmente; pendiente push.
- [ ] Fuente de investigación real. NatHERS y CDC documentados con enlaces y fecha; pendiente push.
- [ ] Algoritmo, supuestos y limitaciones documentados. Completos localmente; factores comerciales pendientes de validación real y push.
- [ ] Pruebas del cotizador. Siete pruebas nuevas aprobadas dentro de la suite de 57; pendiente push.

### Prioridad 5 — Entrega

- [ ] MySQL mediante variables de entorno. Implementado y probado estáticamente; pendiente push y conexión con credencial local real.
- [ ] Script/respaldo reproducible. Script único de 13 tablas validado estáticamente; pendiente ejecución en instancia MySQL limpia y push.
- [ ] Guía de instalación y README.
- [ ] Usuarios demo. Incorporados al SQL con BCrypt y hashes validados; pendiente push.
- [ ] Evidencia real/mercado preparada.
- [ ] Pruebas de usabilidad preparadas.
- [ ] Resultados reales incorporados cuando sean proporcionados.
- [ ] Borrador IEEE.
- [ ] Guion de demostración.
- [ ] Matriz de rúbrica.
- [ ] Push de rama.
- [ ] Pull Request.
- [ ] Revisión de compañero.
- [ ] Correcciones posteriores.
