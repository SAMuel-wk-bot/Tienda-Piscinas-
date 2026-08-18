# Investigación — cotización estimada para piscinas

## Problema

Una persona puede conocer las dimensiones de su piscina, pero no necesariamente su volumen ni qué servicio del catálogo se relaciona con el estado observado. La tienda necesita convertir esos datos en una orientación inicial comprensible, sin presentar el resultado como diagnóstico técnico ni indicar dosificaciones químicas.

## Objetivo

Calcular el volumen de una piscina rectangular, relacionar la necesidad indicada con un servicio activo y producir una estimación transparente a partir del precio base registrado en la base de datos.

## Fuentes consultadas

Consulta realizada el 18 de agosto de 2026.

1. Australian Government, Nationwide House Energy Rating Scheme (NatHERS), *Whole of Home Calculations Method* (23 de enero de 2025), ecuación 94, página 114: `Vp = Ap × Dp × 1000`, donde el volumen se expresa en litros, el área en metros cuadrados y la profundidad promedio en metros. Para una piscina rectangular, el sistema obtiene el área mediante largo por ancho. <https://www.nathers.gov.au/sites/default/files/2025-01/NatHERS%20Whole%20of%20Home%20Calculations%20Method%2020250123.pdf>
2. Centers for Disease Control and Prevention (CDC), *Home Pool and Hot Tub Water Treatment and Testing* (10 de mayo de 2024): sustenta la recomendación de probar regularmente el desinfectante y el pH, mantener el pH entre 7.0 y 7.8 y seguir las instrucciones del fabricante. <https://www.cdc.gov/healthy-swimming/about/home-pool-and-hot-tub-water-treatment-and-testing.html>
3. Centers for Disease Control and Prevention (CDC), *Guidelines for Keeping Your Pool Safe and Healthy* (29 de mayo de 2025): sustenta que desinfectante y pH son la primera defensa frente a gérmenes y que los productos químicos deben manejarse siguiendo sus etiquetas. <https://www.cdc.gov/healthy-swimming/safety/what-you-can-do-to-stay-healthy-in-swimming-pools.html>

Estas fuentes respaldan la fórmula de volumen y las advertencias de seguridad. **No respaldan ni avalan los factores comerciales de precio**, que son supuestos internos de esta demostración académica.

## Entradas

- Largo en metros: entre 0.50 y 50.
- Ancho en metros: entre 0.50 y 30.
- Profundidad promedio en metros: entre 0.30 y 5.
- Estado o necesidad principal: agua clara, turbia, verde o problema de equipo.

## Algoritmo y fórmulas

Para la forma rectangular implementada:

```text
área_m2 = largo_m × ancho_m
volumen_m3 = área_m2 × profundidad_promedio_m
volumen_litros = volumen_m3 × 1000
precio_estimado = precio_base_servicio × factor_volumen × factor_estado
```

El sistema recupera los servicios activos desde la base de datos y busca el primero relacionado por palabras del nombre o descripción: mantenimiento/limpieza, tratamiento/agua o bomba/equipo/revisión. Si no existe coincidencia, conserva el cálculo de volumen y la recomendación, pero no inventa un precio.

## Reglas comerciales internas

Estas reglas son supuestos demostrativos visibles y centralizados en `CotizacionPiscinaServiceImpl`; deben validarse con el negocio antes de un uso comercial real.

| Condición | Factor |
| --- | ---: |
| Volumen hasta 30 m³ | 1.00 |
| Volumen mayor de 30 y hasta 60 m³ | 1.25 |
| Volumen mayor de 60 m³ | 1.50 |
| Agua clara | 1.00 |
| Problema de equipo | 1.00 |
| Agua turbia | 1.15 |
| Agua verde | 1.30 |

## Salidas

- Volumen en metros cúbicos.
- Equivalencia en litros.
- Recomendación inicial según la necesidad elegida.
- Servicio activo relacionado, si existe.
- Factores comerciales aplicados.
- Precio estimado, si existe un servicio relacionado.
- Advertencia explícita sobre los límites del resultado.

## Supuestos

- La piscina es rectangular.
- La profundidad ingresada es un promedio representativo.
- El precio base del servicio está actualizado en la base de datos.
- El estado indicado por la persona es orientativo y no reemplaza mediciones.
- La herramienta recomienda servicios; no calcula cantidades de químicos.

## Limitaciones

- No calcula piscinas circulares, ovaladas ni de forma irregular.
- No mide pH, concentración de desinfectante, alcalinidad, temperatura ni circulación.
- La coincidencia con servicios usa palabras simples y depende de la calidad de los datos del catálogo.
- La estimación no incluye visita, distancia, materiales adicionales, impuestos ni condiciones específicas del sitio.
- Los factores de precio requieren validación real del equipo o cliente antes de producción.

## Casos de prueba

| Caso | Entrada | Resultado esperado |
| --- | --- | --- |
| Piscina mediana, agua verde | 10 × 4 × 1.25 m; servicio base ₡20 000 | 50 m³, 50 000 L, factores 1.25 y 1.30, estimación ₡32 500 |
| Límite de piscina pequeña | 5 × 4 × 1.5 m; servicio base ₡18 000 | 30 m³, factor de volumen 1.00, estimación ₡18 000 para agua clara |
| Sin servicio relacionado | 8 × 4 × 1.5 m, problema de equipo y catálogo sin coincidencia | 48 m³, recomendación visible y precio no disponible |
| Datos inválidos | Largo vacío, ancho 0.10 m | Formulario rechazado con mensajes de validación |

Los casos están automatizados en `CotizacionPiscinaTests` y `CotizacionPiscinaWebTests`.

## Relación con la tienda

El cotizador reutiliza el módulo real de servicios: solamente recomienda registros activos y calcula a partir de su precio base persistido. De esta manera funciona como punto de entrada al catálogo y no como una calculadora aislada.

## Por qué es una funcionalidad medular

Conecta una necesidad concreta del dominio —comprender el tamaño y estado general de una piscina— con datos centrales de la aplicación. Aporta orientación previa a una solicitud de servicio, hace visible el valor del catálogo y documenta sus supuestos para que el cálculo pueda revisarse durante la defensa académica.

> Funcionalidad incorporada por requisito explícito de la entrega final; implementación realizada con la alternativa mínima compatible con la estructura estudiada.
