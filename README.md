# Tienda Piscinas Santamaría

Aplicación web académica para comercializar productos y coordinar servicios de mantenimiento de piscinas en Costa Rica. Este repositorio contiene el **primer 50% del proyecto**, construido como base verificable para que un segundo integrante complete el flujo transaccional y los requisitos avanzados.

## Estado actual

- Portada responsive en azul, celeste, blanco y turquesa.
- Spring Boot, MVC, Thymeleaf, Bootstrap y JavaScript.
- Catálogo dinámico: los productos visibles se consultan mediante JPA.
- Esquema reproducible de 10 tablas; `pedidos` y `detalles_pedido` registran transacciones.
- Datos de demostración y base H2 lista al iniciar.
- Compatibilidad prevista con MySQL mediante el conector incluido.
- Pruebas de contexto y de integración de la portada.

No están implementados todavía autenticación, autorización, CRUD administrativo, carrito final ni internacionalización. Esta separación es intencional y corresponde al segundo 50%.

## Requisitos

- JDK 21
- Maven 3.9+ (o el Maven integrado de NetBeans)

## Ejecución

```bash
mvn spring-boot:run
```

Abrir `http://localhost:8080`. La consola H2 de desarrollo está en `http://localhost:8080/h2-console`, con URL JDBC `jdbc:h2:mem:tiendapiscinas`, usuario `sa` y contraseña vacía.

## Verificación

```bash
mvn clean test
```

## Documentación

- [`docs/AVANCE_50_PORCIENTO.md`](docs/AVANCE_50_PORCIENTO.md): alcance, arquitectura y trazabilidad con la rúbrica.
- [`docs/TAREAS_PARA_COMPANERO.md`](docs/TAREAS_PARA_COMPANERO.md): segundo 50% listo para asignar.
- [`docs/DECISIONES_TECNICAS.md`](docs/DECISIONES_TECNICAS.md): decisiones y justificación para el artículo científico.
- [`src/main/resources/schema.sql`](src/main/resources/schema.sql): creación reproducible de la base.
- [`src/main/resources/data.sql`](src/main/resources/data.sql): datos de prueba.

## Flujo colaborativo sugerido

Cada integrante trabaja en su propia rama, abre un pull request y solicita revisión antes de integrar a `master`. No compartir credenciales ni subir secretos al repositorio.
