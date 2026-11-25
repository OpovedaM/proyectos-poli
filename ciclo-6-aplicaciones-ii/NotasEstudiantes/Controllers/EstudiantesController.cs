// Autores: Soleny Guarin Quintero, Omar Poveda, Jeff Alan
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using NotasEstudiantes.Models;

namespace NotasEstudiantes.Controllers
{
    public class EstudiantesController : Controller
    {
        private readonly ApplicationDbContext _context;
        private readonly NotaService _notaService;

        // El DbContext y el servicio de calculo llegan por inyeccion de dependencias.
        public EstudiantesController(ApplicationDbContext context, NotaService notaService)
        {
            _context = context;
            _notaService = notaService;
        }

        // Muestra la lista de todos los estudiantes
        public async Task<IActionResult> Index()
        {
            var lista = await _context.Estudiantes.ToListAsync();
            return View(lista);
        }

        // Muestra el formulario para ingresar las notas
        [HttpGet]
        public IActionResult Create()
        {
            return View();
        }

        // Recibe el formulario, calcula la nota definitiva y guarda en la BD
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("Nombre,Corte1,Corte2,Corte3")] Estudiante estudiante)
        {
            if (ModelState.IsValid)
            {
                // La logica de negocio reside en la clase NotaService (criterio del caso).
                estudiante.NotaDefinitiva = _notaService.CalcularDefinitiva(
                    estudiante.Corte1, estudiante.Corte2, estudiante.Corte3);

                estudiante.FechaRegistro = DateTime.Now;

                // Guardar en la base de datos
                _context.Add(estudiante);
                await _context.SaveChangesAsync();

                // Mensaje de confirmacion que mostrara la vista de resultado.
                TempData["Mensaje"] = $"Estudiante {estudiante.Nombre} registrado con éxito. Nota definitiva: {estudiante.NotaDefinitiva:0.0}";

                // Redirigir a la vista de resultado
                return RedirectToAction("Resultado", new { id = estudiante.Id });
            }

            return View(estudiante);
        }

        // Muestra el resultado con la nota definitiva calculada
        public async Task<IActionResult> Resultado(int? id)
        {
            if (id == null)
                return NotFound();

            var estudiante = await _context.Estudiantes.FindAsync(id);

            if (estudiante == null)
                return NotFound();

            return View(estudiante);
        }

        // Muestra el detalle de un estudiante
        public async Task<IActionResult> Details(int? id)
        {
            if (id == null)
                return NotFound();

            var estudiante = await _context.Estudiantes.FindAsync(id);

            if (estudiante == null)
                return NotFound();

            return View(estudiante);
        }

        // Confirmacion para eliminar
        public async Task<IActionResult> Delete(int? id)
        {
            if (id == null)
                return NotFound();

            var estudiante = await _context.Estudiantes.FindAsync(id);

            if (estudiante == null)
                return NotFound();

            return View(estudiante);
        }

        // Elimina el estudiante de la base de datos
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(int id)
        {
            var estudiante = await _context.Estudiantes.FindAsync(id);
            if (estudiante != null)
            {
                _context.Estudiantes.Remove(estudiante);
                await _context.SaveChangesAsync();
                TempData["Mensaje"] = $"Estudiante {estudiante.Nombre} eliminado con éxito.";
            }
            else
            {
                TempData["Error"] = "No se pudo eliminar: el estudiante ya no existe.";
            }
            return RedirectToAction(nameof(Index));
        }
    }
}
