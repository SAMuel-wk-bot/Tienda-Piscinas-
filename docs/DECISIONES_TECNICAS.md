# Registro de decisiones técnicas

## DT-001 — Arquitectura MVC por capas

**Decisión:** separar vistas Thymeleaf, controladores, modelos JPA y repositorios.  
**Motivo:** facilita prueba, mantenimiento, reparto del trabajo y defensa del dominio técnico.  
**Consecuencia:** el compañero debe añadir servicios para la lógica transaccional y evitar lógica de negocio en controladores.

## DT-002 — H2 para reproducción y MySQL para entrega

**Decisión:** usar H2 en memoria con modo MySQL durante este corte y mantener el conector MySQL.  
**Motivo:** permite ejecutar pruebas sin credenciales ni instalaciones externas.  
**Consecuencia:** antes del despliegue se debe crear un perfil MySQL mediante variables de entorno y validar compatibilidad del script.

## DT-003 — Esquema SQL explícito

**Decisión:** versionar `schema.sql` y `data.sql`, con 10 tablas, relaciones, índices y una transacción demo.  
**Motivo:** hace la evidencia reproducible y satisface la necesidad de crear/poblar la base.  
**Consecuencia:** los cambios de entidades deben mantenerse sincronizados con el esquema; una migración Flyway sería una mejora futura.

## DT-004 — Catálogo dinámico como corte vertical

**Decisión:** completar el recorrido base de datos → repositorio → controlador → vista para productos.  
**Motivo:** demuestra uso efectivo de persistencia desde el primer 50%.  
**Consecuencia:** el segundo integrante puede repetir el patrón para CRUD, pedidos y solicitudes.

## DT-005 — División verificable del trabajo

**Decisión:** esta rama entrega fundación, catálogo, diseño, esquema, pruebas base y documentación; la otra mitad entrega seguridad, CRUD completo, transacciones, i18n y despliegue.  
**Motivo:** generar aportes GitHub significativos, distintos y auditables de ambos integrantes.  
**Consecuencia:** la integración debe realizarse mediante pull request y revisión entre pares.

## DT-006 — Carga explícita y sesión cerrada en la vista

**Decisión:** mantener `spring.jpa.open-in-view=false` y cargar `categoria` mediante `@EntityGraph` en la consulta del catálogo.  
**Motivo:** evita consultas inesperadas durante el renderizado y hace explícitas las necesidades de datos. La prueba de integración detectó y permitió corregir una `LazyInitializationException`.  
**Consecuencia:** cada caso de uso futuro debe definir las relaciones que necesita o mapear DTO específicos.
