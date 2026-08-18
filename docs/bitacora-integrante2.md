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

- **Commit realizado:** pendiente al momento de redactar; mensaje previsto `Agregar pruebas de autenticacion y autorizacion`.
- **Hash del commit:** se registrará en la siguiente actualización.
- **Rama:** `feature/samuel-segundo-50`.
- **Push confirmado:** **NO**; permanece el bloqueo HTTP `403` del repositorio remoto.
- **Criterio de rúbrica relacionado:** autenticación y roles (10%), temáticas del curso, evidencia comprobable y calidad para la defensa.
- **Pendientes:** publicar commits al obtener permisos; ampliar pruebas cuando existan carrito, pedidos y solicitudes.
- **Estado del bloque:** **HECHO Y PROBADO LOCALMENTE / PENDIENTE COMMIT Y PUSH**.

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
- [ ] Compra para CLIENTE.
- [ ] Historial para CLIENTE.
- [ ] Usuarios de prueba. Implementados con BCrypt localmente; pendiente push y SQL final.
- [ ] Credenciales demo documentadas. Documentadas localmente; pendiente push.
- [ ] Pruebas de acceso permitido. Implementadas y aprobadas localmente; pendiente push.
- [ ] Pruebas de acceso denegado. Implementadas y aprobadas localmente; pendiente push.

### Prioridad 2 — CRUD y persistencia

- [ ] Usuario.
- [ ] Rol.
- [ ] Cliente.
- [ ] Servicio.
- [ ] SolicitudServicio.
- [ ] Pedido.
- [ ] DetallePedido.
- [ ] CRUD Categoria.
- [ ] CRUD Producto.
- [ ] Validaciones.
- [ ] Servicios.
- [ ] Solicitudes.
- [ ] Búsqueda.
- [ ] Filtros.
- [ ] JPQL/consultas.
- [x] Separación Controller/Service/Repository existente parcialmente.
- [ ] Separación Controller/Service/Repository completa.
- [ ] 403.
- [ ] 404.
- [ ] 500.

### Prioridad 3 — Transacciones

- [ ] Carrito: agregar, incrementar, reducir, eliminar y vaciar.
- [ ] Validar existencias, cantidades y precios en servidor.
- [ ] Crear Pedido y DetallePedido.
- [ ] Transacción y rollback.
- [ ] Descontar inventario.
- [ ] Calcular subtotal, impuesto y total.
- [ ] Historial cliente.
- [ ] Gestión de estados.
- [ ] Prueba compra exitosa.
- [ ] Prueba stock insuficiente.
- [ ] Prueba rollback.

### Prioridad 4 — Internacionalización e investigación

- [ ] `messages_es.properties`.
- [ ] `messages_en.properties`.
- [ ] Navegación, formularios, validaciones y estados traducidos.
- [ ] Selector de idioma.
- [ ] Cotización inteligente.
- [ ] Fuente de investigación real.
- [ ] Algoritmo, supuestos y limitaciones documentados.
- [ ] Pruebas del cotizador.

### Prioridad 5 — Entrega

- [ ] MySQL mediante variables de entorno.
- [ ] Script/respaldo reproducible.
- [ ] Guía de instalación y README.
- [ ] Usuarios demo.
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
