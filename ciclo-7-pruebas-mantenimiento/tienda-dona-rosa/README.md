# Tienda de Doña Rosa

Aplicación web para llevar el inventario de la tienda de Doña Rosa, sin instalar nada en el celular: solo se abre en el navegador.

**Integrantes:** Omar Poveda, Sol Guarin,
**Materia:** Pruebas y Mantenimiento de Software

## Qué hace

- Muestra el inventario completo (código, producto, precio y cantidad).
- **Insertar** productos nuevos.
- **Actualizar** productos (cuando hay una venta o llega mercancía).
- **Borrar** productos, con mensaje de confirmación.
- Muestra el **costo total del inventario**.
- Muestra el **producto más cerca de acabarse** y avisa con "¡Por acabarse!" cuando le queda el 10 % o menos de su cantidad inicial.
- Valida los datos y no permite productos repetidos.

## Tecnología

- Python 3 con Flask (la página web)
- SQLite (la base de datos, un archivo local `tienda.db`)
- pytest (las pruebas automáticas)

## Cómo ejecutarla

```bash
git clone [dirección del repositorio]
cd [carpeta del repositorio]
python -m venv venv
venv\Scripts\activate
pip install -r requirements.txt
python app.py
```

Luego abrir en el navegador: http://127.0.0.1:5000

La primera vez, la aplicación crea la base de datos sola y carga los 10 productos de la tabla de Doña Rosa.

## Cómo probarla

```bash
pytest
```

Resultado esperado: `47 passed`.

## Reglas de cálculo

- **Costo total del inventario** = suma de (precio × cantidad) de todos los productos.
- **Producto más cerca de acabarse**: para cada producto se calcula `cantidad − 10 % de la cantidad inicial`. El de menor resultado es el más cerca de acabarse. Cuando ese valor llega a 0 o menos, se muestra "¡Por acabarse!".
- **Cantidad inicial**: al crear un producto es igual a su cantidad. Una venta no la cambia. Si llega mercancía y la cantidad supera la inicial, la inicial sube a ese valor.

## Preguntas de Doña Rosa

### ¿Quién garantiza que mis datos (los del negocio) están a salvo?

Los datos se protegen en varias capas:

1. **Validación en tres niveles:** el navegador (campos obligatorios y mínimos), el servidor (`validaciones.py`) y la base de datos (restricciones `CHECK`: precio mayor que 0, cantidad no negativa). Un dato inválido no llega a guardarse.
2. **Consultas parametrizadas:** todas las consultas a la base de datos usan `?` en vez de pegar texto, lo que evita la inyección SQL.
3. **Borrado protegido:** solo se puede borrar con un formulario (POST) y después de confirmar. Entrar a la dirección `/borrar/1` directamente da error 405 y no borra nada.
4. **Sin productos repetidos:** no se puede crear ni renombrar un producto con un nombre que ya existe.
5. **Los datos no se publican:** `tienda.db` está en el `.gitignore`, así que no se sube al repositorio.

**Limitaciones actuales (mejoras pendientes):** la aplicación todavía no tiene usuario ni contraseña, no hace copias de seguridad automáticas y funciona sobre HTTP. Para uso real se recomienda agregar inicio de sesión, publicar con HTTPS y copiar `tienda.db` periódicamente.

### ¿Cómo me comprueba que sí funciona "su página"?

Con **47 pruebas automáticas** que se ejecutan con el comando `pytest` en poco más de un segundo:

| Archivo | Pruebas | Qué comprueba |
|---|---|---|
| `tests/test_validaciones.py` | 13 | Que se rechacen nombres vacíos, precios en cero o negativos, letras en vez de números, etc. |
| `tests/test_database.py` | 19 | Insertar, actualizar, borrar, los dos cálculos y la detección de repetidos |
| `tests/test_app.py` | 15 | La página completa: que cargue, que guarde, que rechace datos malos y que el borrado directo esté bloqueado |

Las pruebas usan una base de datos temporal, nunca los datos reales de la tienda. Además, se verificó que detectan errores: al cambiar a propósito la fórmula del costo total (`precio * cantidad` por `precio + cantidad`), las pruebas fallaron y avisaron.

[Aquí pegar una captura de pantalla de `pytest` con "47 passed".]

### ¿Cómo puedo luego saber que mis ventas sí se van a reflejar en la página?

Una venta se registra con el botón **Editar** del producto, bajando su cantidad. Al guardar, el cambio queda en la base de datos y la página recalcula todo de inmediato.

Ejemplo con los datos iniciales: se venden 11 de las 12 Fresas (la cantidad pasa de 12 a 1).

- El costo total baja de **$1.355.500** a **$1.322.500** (11 × $3.000 = $33.000 menos).
- Las Fresas aparecen como producto más cerca de acabarse, con el aviso **¡Por acabarse!**.

Esto está comprobado con pruebas automáticas (`test_costo_total_cambia_al_vender`, `test_una_venta_no_cambia_la_cantidad_inicial` y `test_aparece_la_alerta_cuando_queda_el_10_por_ciento_o_menos`).

**Limitación actual:** la aplicación guarda la cantidad actual, no un historial de cada venta. Como mejora futura se puede agregar una tabla de movimientos con fecha y hora.

## Mejoras hechas durante las pruebas

- Al probar la aplicación se encontró que se podía agregar dos veces el mismo producto. Se corrigió con la función `existe_producto` y se agregaron pruebas para que no vuelva a pasar.
- Se corrigió el cálculo del producto "más cerca de acabarse": con la primera regla, todos los productos empataban al inicio, y se cambió por una que usa el margen respecto al 10 %.
- Se eliminó código repetido en `database.py`.

## Estructura del proyecto

```
app.py              rutas de la página web
database.py         todo lo relacionado con la base de datos
validaciones.py     reglas para validar los datos
templates/          páginas HTML
tests/              pruebas automáticas
pytest.ini          configuración de pytest
requirements.txt    librerías necesarias
```
