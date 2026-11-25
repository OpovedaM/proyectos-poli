# Calculadora de Notas — Politécnico Internacional

Aplicación web **ASP.NET Core MVC** que calcula la nota definitiva de los estudiantes
del programa de Tecnología en Desarrollo de Software y la almacena en **SQL Server**.

Proyecto del caso de estudio integrador de la asignatura **Aplicaciones II**.

## Integrantes

- Soleny Guarín Quintero
- Omar Poveda
- Jeff Alan

## Regla de negocio

La nota definitiva se calcula con los pesos de cada corte:

```
notaDefinitiva = (Corte1 × 15%) + (Corte2 × 35%) + (Corte3 × 50%)
```

Las notas van de **0.0 a 5.0**. La lógica reside en la clase `Models/NotaService.cs`.

## Tecnologías

- C# / .NET 8 (ASP.NET Core MVC)
- Entity Framework Core 8 (SQL Server)
- Razor + Bootstrap 5
- Visual Studio 2022 (Windows) / VS Code (Mac)

## Cómo ejecutar

Requisitos: tener el **SDK de .NET 8** instalado.

```bash
dotnet ef database update   # crea la tabla en la base de datos
dotnet run                  # ejecuta la aplicación
```

Luego abrir en el navegador la URL que muestra la consola (ej. `http://localhost:5210`).

### Base de datos

La cadena de conexión se lee de la configuración:

- Por defecto, `appsettings.json` está configurado para **SQL Server Express LocalDB**
  (incluido con Visual Studio en Windows). No hay que tocar nada para ejecutar.
- Si se usa otra instancia de SQL Server (por ejemplo, en Docker), se define la cadena
  de conexión en `appsettings.Development.json`. Ese archivo **no se sube al repositorio**
  (está en `.gitignore`, ya que puede contener credenciales) y cada quien mantiene su
  configuración local.

## Funcionalidades

- Registrar las notas de un estudiante (formulario).
- Calcular automáticamente la nota definitiva.
- Listar los estudiantes con su definitiva y estado (Aprobado / Reprobado).
- Ver el detalle de un estudiante.
- Eliminar estudiantes (con mensaje de confirmación).

## Estructura del proyecto

```
NotasEstudiantes/
├── Controllers/   → EstudiantesController.cs   (coordina vista, lógica y datos)
├── Models/        → Estudiante.cs, NotaService.cs, ApplicationDbContext.cs
├── Views/         → vistas Razor (Index, Create, Resultado, Details, Delete)
├── Migrations/    → migración de Entity Framework Core
└── wwwroot/css/   → estilos (notas.css)
```
