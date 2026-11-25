// Autores: Soleny Guarin Quintero, Omar Poveda, Jeff Alan
using System;
using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace NotasEstudiantes.Migrations
{
    /// <inheritdoc />
    public partial class InitialCreate : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "Estudiantes",
                columns: table => new
                {
                    Id = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    Nombre = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Corte1 = table.Column<decimal>(type: "decimal(3,1)", nullable: false),
                    Corte2 = table.Column<decimal>(type: "decimal(3,1)", nullable: false),
                    Corte3 = table.Column<decimal>(type: "decimal(3,1)", nullable: false),
                    NotaDefinitiva = table.Column<decimal>(type: "decimal(3,1)", nullable: false),
                    FechaRegistro = table.Column<DateTime>(type: "datetime2", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Estudiantes", x => x.Id);
                });
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "Estudiantes");
        }
    }
}
