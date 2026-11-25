# Proyectos Politécnico Internacional

Proyectos académicos de la Tecnología en Desarrollo de Software y Aplicaciones Móviles, 
| Ciclo | Materia | Proyecto | Tecnología |
|-------|---------|----------|------------|
| 3 | Programación I | [SeriesTv](ciclo-3-programacion-i/SeriesTv) | Java, Maven, Swing, MySQL |
| 4 | Programación II | [ServletCalculoNota](ciclo-4-programacion-ii/ServletCalculoNota) | Java, Jakarta EE, Servlets, JSP |
| 4 | Diseño de Algoritmos II | [Ejercicios de grafos](ciclo-4-diseno-algoritmos-ii) | Python |
| 5 | Aplicaciones I | [sumaalgebraica](ciclo-5-aplicaciones-i/sumaalgebraica) | C#, .NET, Windows Forms |
| 6 | Aplicaciones II | [NotasEstudiantes](ciclo-6-aplicaciones-ii/NotasEstudiantes) | C#, ASP.NET Core MVC, EF Core, SQL Server |
| 6 | Sistemas Integrados | [WebServiceNotas](ciclo-6-sistemas-integrados/WebServiceNotas) | PHP, REST, JSON, HTML |
| 7 | Aplicaciones Móviles | [Promedio](ciclo-7-aplicaciones-moviles/Promedio) / [promedioEstudiantes](ciclo-7-aplicaciones-moviles/promedioEstudiantes) | Kotlin |
| 7 | Aplicaciones Móviles | [Grupo03_DonaRosa_Inventario](ciclo-7-aplicaciones-moviles/Grupo03_DonaRosa_Inventario) | Android, Kotlin, SQLite |
| 7 | Pruebas y Mantenimiento de Software | [ProyectoBanco](ciclo-7-pruebas-mantenimiento/ProyectoBanco) | Java, Maven, JUnit |
| 7 | Lenguaje de Programación para Aplicaciones de Nube | [citas-medicas](ciclo-7-programacion-nube/citas-medicas) | Java, Spring Boot, JPA, H2, Swagger |
| 7 | Pruebas y Mantenimiento de Software | [tienda-dona-rosa](ciclo-7-pruebas-mantenimiento/tienda-dona-rosa) | Python, Flask, SQLite, pytest |

## Descripción de los proyectos

### Ciclo 3

**[SeriesTv](ciclo-3-programacion-i/SeriesTv)** · Programación I\
Aplicación de escritorio en Java (Swing, NetBeans) para administrar un catálogo de series de televisión. Permite crear, buscar, listar, actualizar y eliminar series (código, título, fecha, temporadas, género, actores y sinopsis), conectada a una base de datos mediante el patrón DAO. Caso de estudio integrador.

### Ciclo 4

**[ServletCalculoNota](ciclo-4-programacion-ii/ServletCalculoNota)** · Programación II\
Aplicación web en Java con Servlets y JSP que aplica el patrón MVC en un modelo cliente-servidor. Un formulario recibe los datos del estudiante y sus tres cortes, el servlet calcula la nota definitiva (35 % + 35 % + 30 %) y una página JSP muestra el resultado y si aprobó o reprobó. Caso de estudio integrador.

**[Ejercicios de grafos](ciclo-4-diseno-algoritmos-ii)** · Diseño de Algoritmos II\
Scripts en Python con NetworkX y Matplotlib que modelan las ciudades de España como un grafo ponderado por distancias. Calculan y dibujan rutas más cortas entre ciudades (por ejemplo, Murcia–Badajoz) y comparan varias rutas posibles en un mismo mapa.

### Ciclo 5

**[sumaalgebraica](ciclo-5-aplicaciones-i/sumaalgebraica)** · Aplicaciones I\
Aplicación de escritorio en C# con Windows Forms que recibe dos números y muestra su suma. Primer ejercicio con Visual Studio y el diseñador de formularios.

### Ciclo 6

**[NotasEstudiantes](ciclo-6-aplicaciones-ii/NotasEstudiantes)** · Aplicaciones II\
Aplicación web en ASP.NET Core MVC (.NET 8) que registra estudiantes y calcula su nota definitiva a partir de tres cortes ponderados (15 % + 35 % + 50 %). Guarda los datos en SQL Server con Entity Framework Core y migraciones. Caso de estudio integrador.

**[WebServiceNotas](ciclo-6-sistemas-integrados/WebServiceNotas)** · Sistemas Integrados\
Servicio web RESTful en PHP que recibe tres notas por la URL (GET), las valida en la escala de 0.0 a 5.0 y devuelve en JSON la nota definitiva (30 % + 30 % + 40 %). Incluye un cliente HTML que consume el servicio. Caso de estudio integrador.

### Ciclo 7

**[Promedio](ciclo-7-aplicaciones-moviles/Promedio) / [promedioEstudiantes](ciclo-7-aplicaciones-moviles/promedioEstudiantes)** · Aplicaciones Móviles\
Programa de consola en Kotlin que pide la cantidad de estudiantes, sus nombres y tres notas por estudiante. Valida los datos, calcula el promedio de cada uno y muestra el listado ordenado de mayor a menor nota.

**[Grupo03_DonaRosa_Inventario](ciclo-7-aplicaciones-moviles/Grupo03_DonaRosa_Inventario)** · Aplicaciones Móviles\
Aplicación Android nativa en Kotlin que reemplaza el cuaderno donde Doña Rosa llevaba el inventario de su tienda. Permite registrar, consultar, actualizar y eliminar productos, avisa cuáles están por agotarse y calcula el valor total del inventario. Los datos se guardan en SQLite y tiene pruebas unitarias. Caso de estudio integrador.

**[ProyectoBanco](ciclo-7-pruebas-mantenimiento/ProyectoBanco)** · Pruebas y Mantenimiento de Software\
Proyecto Maven en Java con las clases `Cliente` y `Banco` (crear clientes, consignar, retirar, consultar saldo y caja total). Está probado con JUnit 5 siguiendo el patrón Arrange–Act–Assert.

**[tienda-dona-rosa](ciclo-7-pruebas-mantenimiento/tienda-dona-rosa)** · Pruebas y Mantenimiento de Software\
Versión web del inventario de Doña Rosa, en Python con Flask y SQLite. Permite insertar, actualizar y borrar productos, muestra el costo total del inventario y alerta cuando a un producto le queda el 10 % o menos. Tiene pruebas automáticas con pytest. Caso de estudio integrador.

**[citas-medicas](ciclo-7-programacion-nube/citas-medicas)** · Lenguaje de Programación para Aplicaciones de Nube\
API REST en Spring Boot para la clínica Sur Salud (Soacha), que agendaba sus citas por teléfono y en hojas de cálculo. Gestiona médicos, pacientes y citas, con estados (pendiente, confirmada, cancelada, atendida), consultas por médico, paciente o estado, y un reporte resumen. Usa arquitectura en capas, JPA con base de datos H2 y documentación con Swagger, y está preparada para desplegarse en la nube. Caso de estudio integrador.
