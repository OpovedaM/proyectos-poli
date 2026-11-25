// Autores: Soleny Guarin Quintero, Omar Poveda, Jeff Alan
// Clase de logica de negocio: aqui reside el calculo de la nota definitiva.
// El caso de estudio EXIGE que esta logica resida en una clase de C#.

namespace NotasEstudiantes.Models
{
    public class NotaService
    {
        // Pesos de cada corte segun el politecnico internacional.
        private const decimal PesoCorte1 = 0.15m; // 15%
        private const decimal PesoCorte2 = 0.35m; // 35%
        private const decimal PesoCorte3 = 0.50m; // 50%

        // Calcula la nota definitiva:
        // notaDefinitiva = (Corte1 * 15%) + (Corte2 * 35%) + (Corte3 * 50%)
        public decimal CalcularDefinitiva(decimal corte1, decimal corte2, decimal corte3)
        {
            decimal definitiva = (corte1 * PesoCorte1)
                               + (corte2 * PesoCorte2)
                               + (corte3 * PesoCorte3);

            // Se redondea a una cifra decimal (las notas se manejan con un decimal).
            return Math.Round(definitiva, 1);
        }
    }
}
