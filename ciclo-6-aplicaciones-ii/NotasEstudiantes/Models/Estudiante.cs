// Autores: Soleny Guarin Quintero, Omar Poveda, Jeff Alan
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace NotasEstudiantes.Models
{
    public class Estudiante
    {
        public int Id { get; set; }

        [Required(ErrorMessage = "El nombre es obligatorio")]
        [Display(Name = "Nombre del Estudiante")]
        public string Nombre { get; set; } = string.Empty;

        [Required(ErrorMessage = "Ingrese la nota del primer corte")]
        [Range(0.0, 5.0, ErrorMessage = "La nota debe estar entre 0.0 y 5.0")]
        [Display(Name = "Primer Corte (15%)")]
        [Column(TypeName = "decimal(3,1)")]
        public decimal Corte1 { get; set; }

        [Required(ErrorMessage = "Ingrese la nota del segundo corte")]
        [Range(0.0, 5.0, ErrorMessage = "La nota debe estar entre 0.0 y 5.0")]
        [Display(Name = "Segundo Corte (35%)")]
        [Column(TypeName = "decimal(3,1)")]
        public decimal Corte2 { get; set; }

        [Required(ErrorMessage = "Ingrese la nota del tercer corte")]
        [Range(0.0, 5.0, ErrorMessage = "La nota debe estar entre 0.0 y 5.0")]
        [Display(Name = "Tercer Corte (50%)")]
        [Column(TypeName = "decimal(3,1)")]
        public decimal Corte3 { get; set; }

        [Display(Name = "Nota Definitiva")]
        [Column(TypeName = "decimal(3,1)")]
        public decimal NotaDefinitiva { get; set; }

        public DateTime FechaRegistro { get; set; }
    }
}
