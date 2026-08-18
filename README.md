# Tienda Piscinas

Aplicación web académica para una tienda y plataforma de servicios relacionados con piscinas. Permite consultar productos y servicios, registrar clientes, controlar accesos por rol, comprar con inventario real, gestionar solicitudes y obtener una cotización orientativa según el volumen de una piscina.

El proyecto corresponde al curso **Desarrollo de Aplicaciones Web y Patrones** y conserva la estructura MVC y los patrones trabajados durante las clases.

## Necesidad que resuelve

Centraliza en un mismo sistema el catálogo, el inventario, la compra de suministros y las solicitudes de mantenimiento. El cliente puede realizar operaciones sobre sus propios datos; la administración mantiene catálogos y revisa las transacciones. El cotizador ayuda a convertir dimensiones conocidas en volumen y en una orientación inicial vinculada con servicios activos.

Los datos comerciales y las cuentas incluidas son ficticios y se usan exclusivamente para demostración académica.

## Tecnologías utilizadas

- Java 17.
- Spring Boot 4.1.0.
- Spring MVC.
- Spring Security y BCrypt.
- Spring Data JPA / Hibernate.
- Thymeleaf y Thymeleaf Extras Spring Security.
- Bean Validation.
- Bootstrap 5.3.8 mediante WebJars.
- MySQL 8 para ejecución normal.
- H2 solamente en pruebas automatizadas.
- Maven.

## Arquitectura

La aplicación mantiene la separación estudiada en el curso:

```text
Navegador / Thymeleaf
          ↕
      Controller
          ↓
        Service
          ↓
      Repository
          ↓
       MySQL
```

- **Controller:** recibe rutas y formularios y prepara el modelo para Thymeleaf.
- **Service / ServiceImpl:** aplica reglas de negocio, validaciones y transacciones.
- **Repository:** consulta y persiste entidades JPA.
- **Thymeleaf:** renderiza vistas de servidor y muestra acciones según el rol.

## Módulos funcionales

### Autenticación y usuarios

- Registro público con rol fijo `CLIENTE`.
- Correo único y validaciones de formulario.
- Contraseñas codificadas con BCrypt.
- Login, logout y manejo de sesión.
- Consulta administrativa de usuarios sin exponer hashes.

### Autorización por roles

- Rutas protegidas en el servidor mediante Spring Security.
- Acciones y menús condicionados con Thymeleaf Security.
- Páginas personalizadas 403, 404 y 500.

### Categorías y productos

- CRUD administrativo con Controller, Service y Repository.
- Validaciones, eliminación controlada y asociación producto-categoría.
- Catálogo público con búsqueda por nombre, filtro por categoría y filtro de disponibilidad.
- Consulta JPQL para combinar filtros.
- Existencias y precio verificados nuevamente en el servidor.

### Servicios y solicitudes

- CRUD administrativo de servicios.
- Catálogo público de servicios activos.
- Solicitudes asociadas al cliente y al servicio.
- El cliente consulta solamente sus solicitudes.
- La administración filtra y modifica estados.

### Carrito, pedidos e inventario

- Carrito guardado en la sesión HTTP.
- Agregar, aumentar, reducir, eliminar y vaciar.
- Rechazo de cantidades no válidas o superiores al stock.
- Precios recuperados desde la base de datos, nunca desde el navegador.
- Compra atómica mediante `@Transactional`.
- Creación de `Pedido` y `DetallePedido` con precio histórico.
- Cálculo en servidor de subtotal, IVA académico configurado y total.
- Descuento de inventario y rollback si falta stock.
- Historial restringido al propietario y gestión administrativa de estados.

### Cotización estimada

- Cálculo para piscinas rectangulares con profundidad promedio.
- Resultado en metros cúbicos y litros.
- Orientación según agua clara, turbia, verde o problema de equipo.
- Relación con servicios activos y su precio base.
- Factores comerciales documentados y advertencia de que no es un diagnóstico ni una dosificación.

La investigación, fuentes y limitaciones están en [docs/investigacion-cotizacion.md](docs/investigacion-cotizacion.md).

### Internacionalización

- Español predeterminado e inglés.
- Selector de idioma en la navegación.
- Locale conservado en sesión.
- Menús, formularios, validaciones, confirmaciones, estados y errores traducidos.

### Modelo heredado preservado

Las entidades `Piscina`, `Reserva` y `Pago` del proyecto original se conservaron y están representadas en el esquema. Su interfaz completa no forma parte del bloque funcional desarrollado por el Integrante 2.

## Base de datos

El esquema contiene 13 tablas físicas y relacionadas:

| Tabla | Finalidad |
| --- | --- |
| `usuarios` | Identidad y credenciales BCrypt |
| `roles` | Permisos disponibles |
| `usuarios_roles` | Relación entre cuentas y roles |
| `clientes` | Perfil específico del cliente |
| `categorias` | Clasificación del catálogo |
| `productos` | Precio, descripción e inventario |
| `servicios` | Servicios y precios base |
| `solicitudes_servicio` | Transacción de solicitud y estado |
| `pedidos` | Cabecera, totales y estado de compra |
| `detalles_pedido` | Líneas, cantidades y precio histórico |
| `piscinas` | Modelo de piscinas heredado |
| `reservas` | Reservas del modelo heredado |
| `pagos` | Pago asociado a una reserva |

El archivo oficial es [proyecto_piscinas/piscinas_script.sql](proyecto_piscinas/piscinas_script.sql). **El script elimina y recrea las tablas**, por lo que debe ejecutarse solamente en una base de desarrollo o demostración que pueda reemplazarse.

## Requisitos

- JDK 17 o una versión capaz de compilar con `--release 17`.
- Maven disponible en PATH o desde el IDE.
- MySQL 8.
- Un usuario local MySQL con permiso para crear la base `gestion_piscinas`.

## Instalación y configuración

1. Clonar el repositorio y entrar al proyecto:

   ```powershell
   git clone https://github.com/SAMuel-wk-bot/Tienda-Piscinas-.git
   cd Tienda-Piscinas-/proyecto_piscinas
   ```

2. Abrir `piscinas_script.sql` en MySQL Workbench y ejecutarlo con una cuenta local autorizada.

3. Definir la conexión en la misma terminal PowerShell:

   ```powershell
   $env:DB_URL="jdbc:mysql://localhost:3306/gestion_piscinas?serverTimezone=UTC"
   $env:DB_USER="root"
   $env:DB_PASSWORD="SU_CLAVE_MYSQL_LOCAL"
   $env:SERVER_PORT="8080"
   ```

4. Ejecutar las pruebas:

   ```powershell
   mvn clean test
   ```

5. Iniciar la aplicación:

   ```powershell
   mvn spring-boot:run
   ```

6. Abrir <http://localhost:8080>.

La guía ampliada se encuentra en [docs/configuracion-mysql.md](docs/configuracion-mysql.md). El archivo `proyecto_piscinas/.env.example` es solo una referencia; Spring Boot no carga `.env` automáticamente.

## Variables de entorno

| Variable | Uso | Predeterminado |
| --- | --- | --- |
| `DB_URL` | URL JDBC de MySQL | `jdbc:mysql://localhost:3306/gestion_piscinas?serverTimezone=UTC` |
| `DB_USER` | Usuario local MySQL | `root` |
| `DB_PASSWORD` | Contraseña local MySQL | Vacía |
| `SERVER_PORT` | Puerto HTTP | `8080` |

No se deben confirmar en Git contraseñas personales, tokens, claves privadas ni archivos `.env` reales.

## Usuarios de prueba

Credenciales ficticias exclusivas para demostración académica:

| Rol | Usuario | Contraseña |
| --- | --- | --- |
| ADMINISTRADOR | `admin@tiendapiscinas.test` | `AdminPiscinas2026!` |
| CLIENTE | `cliente@tiendapiscinas.test` | `ClientePiscinas2026!` |

El script guarda hashes BCrypt. Las claves visibles permiten solamente reproducir la demostración.

## Funcionalidades por rol

### Invitado

- Ver inicio, productos, servicios y cotizador.
- Buscar y filtrar el catálogo.
- Cambiar idioma.
- Registrarse e iniciar sesión.
- No puede confirmar compras, ver historiales ni abrir administración.

### CLIENTE

- Todas las consultas públicas.
- Usar carrito y finalizar pedidos.
- Consultar únicamente sus pedidos y sus detalles.
- Crear y consultar únicamente sus solicitudes de servicio.
- No puede ejecutar acciones administrativas.

### ADMINISTRADOR

- Gestionar categorías, productos y servicios.
- Consultar usuarios.
- Consultar y filtrar todas las solicitudes y pedidos.
- Modificar estados permitidos.
- No depende de botones ocultos: las rutas también están protegidas en el servidor.

## Cómo demostrar el cambio de idioma

1. Abrir cualquier página.
2. Usar el selector **Idioma / Language** de la barra superior.
3. Elegir Español o English.
4. Navegar a login, productos, carrito, pedidos o cotizador y comprobar que el idioma permanece en la sesión.

## Pruebas

Comando principal:

```powershell
mvn clean test
```

La suite actual incluye 64 pruebas y cubre, entre otros:

- persistencia y relaciones;
- registro BCrypt y correo duplicado;
- acceso público, autenticación y autorización;
- CRUD y filtros JPQL;
- solicitudes de servicio;
- operaciones del carrito;
- compra exitosa, totales, inventario y rollback;
- propiedad del historial y estados;
- páginas de error;
- internacionalización;
- interfaz comercial;
- cotizador y validaciones;
- variables de entorno, conteo de tablas y hashes del SQL.

La última ejecución documentada aprobó las 64 pruebas con 0 fallos y 0 errores. La ejecución sobre una base MySQL limpia debe repetirse en el equipo de entrega con una instancia respaldada o descartable.

## Documentación

- [Bitácora del Integrante 2](docs/bitacora-integrante2.md).
- [Configuración MySQL](docs/configuracion-mysql.md).
- [Investigación del cotizador](docs/investigacion-cotizacion.md).
- [Matriz de rúbrica](docs/matriz-rubrica.md).
- [Plantilla de evidencia de cliente](docs/evidencia-cliente.md).
- [Protocolo de usabilidad](docs/pruebas-usabilidad.md).
- [Borrador de artículo IEEE](docs/borrador-articulo-ieee.md).
- [Guion de defensa](docs/guion-defensa.md).

## Contribuciones

- **Proyecto base y primer 50%:** se conservan los aportes existentes del equipo; su autoría se verifica mediante el historial Git y no se reasigna en este documento.
- **Integrante 2 — rama `feature/samuel-segundo-50`:** auditoría, modelo transaccional, seguridad y roles, registro, pruebas de acceso, CRUD y filtros, servicios y solicitudes, carrito, pedidos e inventario, historial, errores, internacionalización, interfaz comercial, cotizador, configuración segura de MySQL y documentación correspondiente.

Los commits y hashes de cada bloque están registrados cronológicamente en la bitácora.

## Estado y pendientes reales

- La suite automatizada está aprobada localmente.
- Falta ejecutar el script oficial en una instancia MySQL limpia o respaldada con credenciales del equipo.
- Falta completar una prueba visual manual responsive en navegador real.
- La evidencia de cliente y los resultados de usabilidad deben provenir de personas reales; no se fabrican.
- El push y el Pull Request dependen de que la cuenta GitHub utilizada tenga permiso de escritura sobre el repositorio oficial.
