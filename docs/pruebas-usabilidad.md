# Protocolo de pruebas de usabilidad

## Estado

> **PENDIENTE DE EJECUCIÓN CON USUARIOS REALES**

Los resultados, problemas y opiniones no se completan hasta observar una prueba verdadera. Los resultados automatizados del sistema no reemplazan esta evidencia humana.

## Preparación

- Usar una base de demostración respaldada o descartable.
- Entregar una cuenta CLIENTE ficticia y una ADMINISTRADOR ficticia.
- No solicitar credenciales personales.
- Obtener consentimiento y asignar un código anónimo.
- Pedir que la persona piense en voz alta sin dirigir cada clic.
- Registrar tiempo aproximado, dudas, errores y comentarios textuales breves.

## Escenarios

### Caso 1 — Cliente se registra

Partir como invitado. Crear una cuenta con datos ficticios y comprobar que no puede elegir el rol ADMINISTRADOR.

### Caso 2 — Cliente inicia sesión

Ingresar con la cuenta ficticia y reconocer que la navegación cambió.

### Caso 3 — Busca un producto

Buscar por nombre y filtrar por categoría o disponibilidad.

### Caso 4 — Agrega producto al carrito

Agregar una unidad, incrementar, reducir y eliminar o volver a agregar.

### Caso 5 — Finaliza compra

Confirmar el pedido y reconocer subtotal, impuesto, total y mensaje final.

### Caso 6 — Consulta historial

Abrir “Mis pedidos”, identificar estado y revisar el detalle recién creado.

### Caso 7 — Solicita servicio

Elegir un servicio, escribir una dirección ficticia y consultar la solicitud.

### Caso 8 — Usa cotizador

Calcular una piscina rectangular, explicar el resultado y reconocer la advertencia.

### Caso 9 — Cambia idioma

Cambiar a English, navegar a otra pantalla y volver a español.

### Caso 10 — Administrador gestiona pedido

Ingresar con la cuenta demo ADMINISTRADOR, encontrar el pedido y actualizar su estado.

## Registro obligatorio

| Usuario | Tarea | Resultado esperado | Resultado real | Problema observado | Cambio realizado |
| --- | --- | --- | --- | --- | --- |
| PENDIENTE | Caso 1: registro | Cuenta CLIENTE creada; correo duplicado rechazado | PENDIENTE | PENDIENTE | PENDIENTE |
| PENDIENTE | Caso 2: login | Acceso correcto y menú de CLIENTE visible | PENDIENTE | PENDIENTE | PENDIENTE |
| PENDIENTE | Caso 3: búsqueda | Lista coincide con filtros | PENDIENTE | PENDIENTE | PENDIENTE |
| PENDIENTE | Caso 4: carrito | Cantidades y subtotal cambian correctamente | PENDIENTE | PENDIENTE | PENDIENTE |
| PENDIENTE | Caso 5: compra | Pedido completo sin stock negativo | PENDIENTE | PENDIENTE | PENDIENTE |
| PENDIENTE | Caso 6: historial | Solamente pedidos propios y detalle visible | PENDIENTE | PENDIENTE | PENDIENTE |
| PENDIENTE | Caso 7: solicitud | Solicitud propia aparece como PENDIENTE | PENDIENTE | PENDIENTE | PENDIENTE |
| PENDIENTE | Caso 8: cotizador | Volumen, orientación y advertencia comprendidos | PENDIENTE | PENDIENTE | PENDIENTE |
| PENDIENTE | Caso 9: idioma | Idioma permanece al navegar | PENDIENTE | PENDIENTE | PENDIENTE |
| PENDIENTE | Caso 10: estado | ADMINISTRADOR actualiza el pedido | PENDIENTE | PENDIENTE | PENDIENTE |

## Métricas sencillas

| Código | Casos completados sin ayuda | Casos con ayuda | Casos no completados | Tiempo total | Satisfacción 1–5 |
| --- | ---: | ---: | ---: | --- | ---: |
| PENDIENTE | PENDIENTE | PENDIENTE | PENDIENTE | PENDIENTE | PENDIENTE |

## Cierre con la persona participante

1. ¿Cuál tarea fue más fácil?
2. ¿Cuál fue más confusa?
3. ¿Hubo algún texto o botón que no entendiera?
4. ¿Confiaría en que el pedido quedó registrado? ¿Por qué?
5. ¿Qué mejoraría primero?

## Después de cada sesión

- Guardar solamente evidencia autorizada.
- Separar hechos observados de interpretaciones del equipo.
- Priorizar problemas repetidos o que bloqueen una tarea.
- Probar cada corrección y registrarla en la bitácora y Git.
- No cambiar un resultado real para que parezca favorable.
