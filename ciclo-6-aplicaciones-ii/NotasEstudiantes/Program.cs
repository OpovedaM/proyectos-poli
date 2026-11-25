// Autores: Soleny Guarin Quintero, Omar Poveda, Jeff Alan
using Microsoft.EntityFrameworkCore;
using NotasEstudiantes.Models;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddControllersWithViews();

// Clase de logica de negocio (calculo de la nota definitiva) disponible por DI.
builder.Services.AddScoped<NotaService>();

// Conexion a la base de datos SQL Server (LocalDB en Windows / SQL Server en Docker).
builder.Services.AddDbContext<ApplicationDbContext>(options =>
    options.UseSqlServer(builder.Configuration.GetConnectionString("DefaultConnection")));

var app = builder.Build();

if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Home/Error");
}

app.UseStaticFiles();
app.UseRouting();
app.UseAuthorization();

app.MapControllerRoute(
    name: "default",
    pattern: "{controller=Estudiantes}/{action=Index}/{id?}");

app.Run();
