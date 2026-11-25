// Autores: Soleny Guarin Quintero, Omar Poveda, Jeff Alan
using Microsoft.EntityFrameworkCore;

namespace NotasEstudiantes.Models
{
    public class ApplicationDbContext : DbContext
    {
        public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options)
            : base(options)
        {
        }

        public DbSet<Estudiante> Estudiantes { get; set; }
    }
}
