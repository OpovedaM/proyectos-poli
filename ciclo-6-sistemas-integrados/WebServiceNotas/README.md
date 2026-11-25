<!--
  PROYECTO CONSTRUIDO POR:
    Soleny Guarín Quintero
    Jeff Alan Paladines Ordoñez
    Omar Armando Poveda Mahecha
-->

# Web Service - Cálculo de Nota Definitiva

**Caso de Estudio Integrador — Sistemas Integrados**
Politécnico Internacional · Tecnología en Desarrollo de Software y Aplicativos Móviles
Docente: Leonel Andrés López Arias · 2026

**Integrantes:** Soleny Guarín Quintero · Jeff Alan Paladines Ordoñez · Omar Armando Poveda Mahecha

## ¿Qué hace?

Un Servicio Web RESTful que recibe **tres notas** por parámetros en la URL (método GET)
y devuelve la **nota definitiva** del estudiante en formato **JSON**.

- Endpoint: `definitiva.php`
- Cliente de prueba: `index.html` (formulario que consume el servicio)

## Estructura del proyecto

```
WebServiceNotas/
├── definitiva.php   → El servicio web (back end)
├── index.html       → Cliente web de prueba (front end)
└── README.md        → Este archivo
```

## Cómo desplegarlo (paso a paso con XAMPP)

1. Instala **XAMPP** (trae Apache + PHP). Descarga: https://www.apachefriends.org
2. Copia la carpeta `WebServiceNotas` completa dentro de `xampp/htdocs/`.
3. Abre el **Panel de Control de XAMPP** y presiona **Start** en **Apache**.
4. Prueba el servicio en el navegador:

   ```
   http://localhost/WebServiceNotas/definitiva.php?nota1=1.3&nota2=1.5&nota3=4.9
   ```

5. O abre el cliente visual:

   ```
   http://localhost/WebServiceNotas/index.html
   ```

### Alternativa sin XAMPP (servidor embebido de PHP)

Si tienes PHP instalado, desde la carpeta del proyecto ejecuta:

```bash
php -S localhost:8000
```

Y visita `http://localhost:8000/definitiva.php?nota1=4.2&nota2=3.8&nota3=4.5`

## Ejemplos de respuesta

**Caso correcto** — `?nota1=4.2&nota2=3.8&nota3=4.5`
```json
{
  "estado": "correcto",
  "definitiva": "4,20",
  "definitiva_num": 4.2,
  "aprobo": true,
  "mensaje": "Calculo realizado correctamente.",
  "detalle": { "nota1": 4.2, "nota2": 3.8, "nota3": 4.5, "ponderacion": "30% / 30% / 40%", "escala": "0 a 5" }
}
```

**Caso con error** — `?nota1=abc&nota2=&nota3=9`
```json
{
  "estado": "error",
  "definitiva": null,
  "mensaje": "No fue posible calcular la nota definitiva. Revise los datos enviados.",
  "errores": [
    "El parametro 'nota1' debe ser un numero (se recibio: 'abc').",
    "El parametro 'nota2' esta vacio.",
    "El parametro 'nota3' debe estar entre 0 y 5 (se recibio: 9)."
  ]
}
```

## Fórmula de la nota definitiva

```
definitiva = nota1 * 0.30 + nota2 * 0.30 + nota3 * 0.40
```