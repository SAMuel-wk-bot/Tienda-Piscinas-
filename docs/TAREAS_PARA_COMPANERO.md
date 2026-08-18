# Segundo 50% — tareas para el compañero

Trabajar en una rama propia (por ejemplo, `feature/segundo-50`) y abrir un pull request hacia `samuel` o la rama de integración acordada. Cada bloque debe incluir pruebas, un commit descriptivo y actualización documental.

## Prioridad 1: seguridad y usuarios

- [ ] Integrar Spring Security.
- [ ] Implementar registro e inicio/cierre de sesión con BCrypt.
- [ ] Crear roles `ADMINISTRADOR` y `CLIENTE` usando las tablas existentes.
- [ ] Restringir menú, rutas y acciones: administración solo para ADMINISTRADOR; compra e historial para CLIENTE.
- [ ] Crear usuarios de prueba y documentar credenciales no sensibles.
- [ ] Probar accesos permitidos y denegados con MockMvc.

## Prioridad 2: CRUD y persistencia completa

- [ ] Crear entidades JPA restantes: Usuario, Rol, Cliente, Servicio, SolicitudServicio, Pedido y DetallePedido.
- [ ] Implementar CRUD de categorías y productos con validación.
- [ ] Implementar CRUD o gestión de servicios y solicitudes.
- [ ] Añadir búsqueda/filtros con consultas derivadas o JPQL.
- [ ] Mantener separación controlador/servicio/repositorio.
- [ ] Añadir manejo global de errores y páginas 403/404/500.

## Prioridad 3: flujo transaccional

- [ ] Implementar carrito por sesión o persistente.
- [ ] Validar existencias, cantidades y precios en servidor.
- [ ] Convertir el carrito en `pedido` y `detalles_pedido` dentro de una transacción.
- [ ] Descontar inventario y calcular subtotal, impuesto y total en servidor.
- [ ] Crear historial de pedidos del cliente y panel de estados para administración.
- [ ] Añadir pruebas de éxito, inventario insuficiente y rollback.

## Prioridad 4: internacionalización e investigación

- [ ] Crear `messages_es.properties` y `messages_en.properties`.
- [ ] Traducir navegación, formularios, validaciones y estados; añadir selector de idioma.
- [ ] Implementar una funcionalidad investigada con valor medular (recomendación: cotización inteligente según volumen de piscina y estado del agua).
- [ ] Documentar fuente, algoritmo, supuestos, limitaciones y pruebas de esa funcionalidad.

## Prioridad 5: entrega y defensa

- [ ] Configurar MySQL mediante variables de entorno y proporcionar script/respaldo.
- [ ] Preparar despliegue autorizado y guía de instalación.
- [ ] Actualizar README con módulos, configuración y credenciales de prueba.
- [ ] Recopilar evidencia real: entrevista, encuesta o análisis de mercado con fuentes.
- [ ] Ejecutar pruebas de usabilidad y registrar resultados/cambios.
- [ ] Completar artículo IEEE, presentación y guion de demostración.
- [ ] Abrir pull request, solicitar revisión del primer integrante y corregir observaciones.

## Criterio de terminado

Una tarea solo se marca completa si funciona, tiene prueba proporcional, queda documentada y aparece en un commit trazable. No subir claves, contraseñas reales ni archivos de configuración con secretos.
