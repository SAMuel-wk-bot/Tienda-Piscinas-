# Configuración reproducible de MySQL

## Requisitos

- MySQL 8.
- JDK 17 o compatible para ejecutar el proyecto.
- Maven.

## Script oficial

El único script fuente oficial es:

`proyecto_piscinas/piscinas_script.sql`

El script crea `gestion_piscinas`, crea 13 tablas relacionadas y agrega datos ficticios de demostración. También elimina las tablas existentes antes de recrearlas; por eso debe usarse en una base de desarrollo o demostración, no sobre información que deba conservarse.

La copia que existía en `src/main/resources` fue retirada para evitar dos fuentes contradictorias. El contenido de `target` es salida generada por Maven, no es fuente y queda excluido mediante `.gitignore`.

## Crear la base

Opción recomendada para el curso:

1. Abrir MySQL Workbench.
2. Abrir `proyecto_piscinas/piscinas_script.sql`.
3. Conectarse con un usuario local autorizado para crear bases.
4. Ejecutar el script completo.
5. Confirmar que existe `gestion_piscinas` y sus 13 tablas.

Desde el cliente de MySQL también puede ejecutarse el contenido del script con una cuenta local autorizada. El script no crea cuentas MySQL ni asigna privilegios.

## Variables de entorno

En PowerShell, antes de iniciar Spring Boot:

```powershell
$env:DB_URL="jdbc:mysql://localhost:3306/gestion_piscinas?serverTimezone=UTC"
$env:DB_USER="root"
$env:DB_PASSWORD="SU_CLAVE_MYSQL_LOCAL"
$env:SERVER_PORT="8080"
```

Después:

```powershell
cd proyecto_piscinas
mvn spring-boot:run
```

`DB_URL`, `DB_USER` y `SERVER_PORT` tienen valores locales de referencia. `DB_PASSWORD` queda vacío si no se define; cada integrante debe proporcionar su propia clave mediante el entorno. Ninguna contraseña personal debe escribirse en `application.properties`.

`.env.example` sirve únicamente como lista de referencia. Spring Boot no carga ese archivo automáticamente y un `.env` real queda ignorado por Git.

## Propiedades utilizadas

| Variable | Finalidad | Valor de referencia |
| --- | --- | --- |
| `DB_URL` | URL JDBC | `jdbc:mysql://localhost:3306/gestion_piscinas?serverTimezone=UTC` |
| `DB_USER` | Usuario local MySQL | `root` |
| `DB_PASSWORD` | Clave local MySQL | Sin valor predeterminado |
| `SERVER_PORT` | Puerto HTTP | `8080` |

## Datos de demostración

Las cuentas del script son ficticias y exclusivas para demostración académica. La base almacena hashes BCrypt.

| Rol | Usuario | Contraseña demostrativa |
| --- | --- | --- |
| ADMINISTRADOR | `admin@tiendapiscinas.test` | `AdminPiscinas2026!` |
| CLIENTE | `cliente@tiendapiscinas.test` | `ClientePiscinas2026!` |

## Comprobaciones mínimas

```sql
USE gestion_piscinas;
SHOW TABLES;
SELECT email, activo FROM usuarios;
SELECT u.email, r.nombre
FROM usuarios u
JOIN usuarios_roles ur ON ur.id_usuario = u.id_usuario
JOIN roles r ON r.id_rol = ur.id_rol;
SELECT id_pedido, subtotal, impuesto, total, estado FROM pedidos;
SELECT id_detalle, cantidad, precio_unitario, subtotal_linea FROM detalles_pedido;
```

El pedido y la solicitud incluidos son datos ficticios destinados a demostrar tablas transaccionales; no representan una compra ni un cliente real.
