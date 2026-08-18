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

- `CategoriaService` + `CategoriaServiceImpl`: listado simple.
- `ProductoService` + `ProductoServiceImpl`: listado simple.
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
- **Commit realizado:** pendiente al momento de redactar; mensaje previsto `Documentar estado inicial del segundo 50 por ciento`.
- **Hash del commit:** se registrará en la próxima actualización.
- **Rama:** `feature/samuel-segundo-50`.
- **Push confirmado:** **NO**. Intento fallido por `403`.
- **Criterio de rúbrica relacionado:** almacenamiento, uso efectivo de base de datos, GitHub colaborativo y temáticas del curso.
- **Pendientes inmediatos:** crear un perfil de pruebas reproducible; externalizar credenciales; corregir seguimiento de `target`; implementar el modelo de seguridad; resolver permiso de escritura en GitHub; acordar si el PR final apunta a `main` o a `samuel`.
- **Estado del bloque:** **HECHO LOCAL / PENDIENTE PUSH**.

## Checklist oficial del Integrante 2

### Prioridad 1 — Seguridad y usuarios

- [ ] Integrar Spring Security.
- [ ] Implementar registro.
- [ ] Implementar inicio de sesión.
- [ ] Implementar cierre de sesión.
- [ ] Usar BCrypt.
- [ ] Crear rol ADMINISTRADOR.
- [ ] Crear rol CLIENTE.
- [ ] Restringir menú.
- [ ] Restringir rutas.
- [ ] Restringir acciones.
- [ ] Administración solo ADMINISTRADOR.
- [ ] Compra para CLIENTE.
- [ ] Historial para CLIENTE.
- [ ] Usuarios de prueba.
- [ ] Credenciales demo documentadas.
- [ ] Pruebas de acceso permitido.
- [ ] Pruebas de acceso denegado.

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
