/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.poli.seriestv;

/**
 *
 * @author brito
 */
public class Serie {
    private int id;
    private String codigo;
    private String titulo;
    private String fecha;
    private String temporadas;
    private String genero;
    private String actores;
    private String sinopsis;
    
    // Constructor vacío
    public Serie() {}
    
    // Constructor con parámetros
    public Serie(int id, String codigo, String titulo, String fecha, String temporadas, 
                 String genero, String actores, String sinopsis) {
        this.id = id;
        this.codigo = codigo;
        this.titulo = titulo;
        this.fecha = fecha;
        this.temporadas = temporadas;
        this.genero = genero;
        this.actores = actores;
        this.sinopsis = sinopsis;
    }
    
    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    
    public String getTemporadas() { return temporadas; }
    public void setTemporadas(String temporadas) { this.temporadas = temporadas; }
    
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
    
    public String getActores() { return actores; }
    public void setActores(String actores) { this.actores = actores; }
    
    public String getSinopsis() { return sinopsis; }
    public void setSinopsis(String sinopsis) { this.sinopsis = sinopsis; }
    
}
