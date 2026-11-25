/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.poli.seriestv;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO para operaciones CRUD de Series
 * @author brito
 */
public class SerieDAO {
    
    // ================= MÉTODOS UPDATE =================
    
    /**
     * Actualiza una serie completa
     * @param serie - Objeto Serie con todos los datos actualizados
     * @return true si se actualizó correctamente, false si no
     */
    public boolean actualizarSerie(Serie serie) {
        String sql = "UPDATE series SET codigo = ?, titulo = ?, fecha = ?, temporadas = ?, genero = ?, actores = ?, sinopsis = ? WHERE id = ?";
        
        try (Connection conn = Conexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, serie.getCodigo());
            pstmt.setString(2, serie.getTitulo());
            pstmt.setString(3, serie.getFecha());
            pstmt.setString(4, serie.getTemporadas());
            pstmt.setString(5, serie.getGenero());
            pstmt.setString(6, serie.getActores());
            pstmt.setString(7, serie.getSinopsis());
            pstmt.setInt(8, serie.getId());
            
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("Serie actualizada exitosamente");
                return true;
            } else {
                System.out.println("No se encontró la serie con ID: " + serie.getId());
                return false;
            }
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar la serie: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Actualiza un campo específico de una serie
     * @param id - ID de la serie
     * @param campo - Nombre del campo a actualizar
     * @param nuevoValor - Nuevo valor para el campo
     * @return true si se actualizó correctamente, false si no
     */
    public boolean actualizarCampoSerie(int id, String campo, String nuevoValor) {
        // Validar que el campo sea válido para evitar SQL injection
        String[] camposPermitidos = {"codigo", "titulo", "fecha", "temporadas", "genero", "actores", "sinopsis"};
        boolean campoValido = false;
        
        for (String campoPermitido : camposPermitidos) {
            if (campoPermitido.equals(campo)) {
                campoValido = true;
                break;
            }
        }
        
        if (!campoValido) {
            System.err.println("Campo no válido: " + campo);
            return false;
        }
        
        String sql = "UPDATE series SET " + campo + " = ? WHERE id = ?";
        
        try (Connection conn = Conexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, nuevoValor);
            pstmt.setInt(2, id);
            
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("Campo '" + campo + "' actualizado exitosamente");
                return true;
            } else {
                System.out.println("No se encontró la serie con ID: " + id);
                return false;
            }
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar el campo: " + e.getMessage());
            return false;
        }
    }
    
    // ================= MÉTODOS DELETE =================
    
    /**
     * Elimina una serie por su ID
     * @param id - ID de la serie a eliminar
     * @return true si se eliminó correctamente, false si no
     */
    public boolean eliminarSerie(int id) {
        String sql = "DELETE FROM series WHERE id = ?";
        
        try (Connection conn = Conexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("Serie eliminada exitosamente");
                return true;
            } else {
                System.out.println("No se encontró la serie con ID: " + id);
                return false;
            }
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar la serie: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Elimina una serie por su código
     * @param codigo - Código de la serie a eliminar
     * @return true si se eliminó correctamente, false si no
     */
    public boolean eliminarSeriePorCodigo(String codigo) {
        String sql = "DELETE FROM series WHERE codigo = ?";
        
        try (Connection conn = Conexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, codigo);
            
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("Serie eliminada exitosamente");
                return true;
            } else {
                System.out.println("No se encontró ninguna serie con código: " + codigo);
                return false;
            }
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar la serie: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Elimina una serie por su título
     * @param titulo - Título de la serie a eliminar
     * @return true si se eliminó correctamente, false si no
     */
    public boolean eliminarSeriePorTitulo(String titulo) {
        String sql = "DELETE FROM series WHERE titulo = ?";
        
        try (Connection conn = Conexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, titulo);
            
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("Serie(s) eliminada(s): " + filasAfectadas + " registro(s)");
                return true;
            } else {
                System.out.println("No se encontró ninguna serie con el título: " + titulo);
                return false;
            }
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar la serie: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Elimina múltiples series por género
     * @param genero - Género de las series a eliminar
     * @return número de series eliminadas (-1 si hay error)
     */
    public int eliminarSeriesPorGenero(String genero) {
        String sql = "DELETE FROM series WHERE genero LIKE ?";
        
        try (Connection conn = Conexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + genero + "%");
            
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("Se eliminaron " + filasAfectadas + " serie(s) del género: " + genero);
            } else {
                System.out.println("No se encontraron series del género: " + genero);
            }
            
            return filasAfectadas;
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar las series: " + e.getMessage());
            return -1;
        }
    }
    
    // ================= MÉTODOS DE BÚSQUEDA =================
    
    /**
     * Busca una serie por su ID
     * @param id - ID de la serie
     * @return Objeto Serie o null si no se encuentra
     */
    public Serie buscarSeriePorId(int id) {
        String sql = "SELECT * FROM series WHERE id = ?";
        Serie serie = null;
        
        try (Connection conn = Conexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                serie = new Serie();
                serie.setId(rs.getInt("id"));
                serie.setCodigo(rs.getString("codigo"));
                serie.setTitulo(rs.getString("titulo"));
                serie.setFecha(rs.getString("fecha"));
                serie.setTemporadas(rs.getString("temporadas"));
                serie.setGenero(rs.getString("genero"));
                serie.setActores(rs.getString("actores"));
                serie.setSinopsis(rs.getString("sinopsis"));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al buscar la serie: " + e.getMessage());
        }
        
        return serie;
    }
    
    /**
     * Busca una serie por su código
     * @param codigo - Código de la serie
     * @return Objeto Serie o null si no se encuentra
     */
    public Serie buscarSeriePorCodigo(String codigo) {
        String sql = "SELECT * FROM series WHERE codigo = ?";
        Serie serie = null;
        
        try (Connection conn = Conexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, codigo);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                serie = new Serie();
                serie.setId(rs.getInt("id"));
                serie.setCodigo(rs.getString("codigo"));
                serie.setTitulo(rs.getString("titulo"));
                serie.setFecha(rs.getString("fecha"));
                serie.setTemporadas(rs.getString("temporadas"));
                serie.setGenero(rs.getString("genero"));
                serie.setActores(rs.getString("actores"));
                serie.setSinopsis(rs.getString("sinopsis"));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al buscar la serie: " + e.getMessage());
        }
        
        return serie;
    }
    
    // ================= MÉTODOS CREATE Y READ =================
    
    /**
     * Crea una nueva serie en la base de datos
     * @param serie - Objeto Serie con los datos
     * @return true si se creó correctamente, false si no
     */
    public boolean crearSerie(Serie serie) {
        String sql = "INSERT INTO series (codigo, titulo, fecha, temporadas, genero, actores, sinopsis) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = Conexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, serie.getCodigo());
            pstmt.setString(2, serie.getTitulo());
            pstmt.setString(3, serie.getFecha());
            pstmt.setString(4, serie.getTemporadas());
            pstmt.setString(5, serie.getGenero());
            pstmt.setString(6, serie.getActores());
            pstmt.setString(7, serie.getSinopsis());
            
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("Serie creada exitosamente");
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("Error al crear la serie: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Lista todas las series de la base de datos
     * @return Lista de objetos Serie
     */
    public List<Serie> listarSeries() {
        List<Serie> series = new ArrayList<>();
        String sql = "SELECT * FROM series ORDER BY titulo";
        
        try (Connection conn = Conexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Serie serie = new Serie();
                serie.setId(rs.getInt("id"));
                serie.setCodigo(rs.getString("codigo"));
                serie.setTitulo(rs.getString("titulo"));
                serie.setFecha(rs.getString("fecha"));
                serie.setTemporadas(rs.getString("temporadas"));
                serie.setGenero(rs.getString("genero"));
                serie.setActores(rs.getString("actores"));
                serie.setSinopsis(rs.getString("sinopsis"));
                series.add(serie);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al listar las series: " + e.getMessage());
        }
        
        return series;
    }
    
    /**
     * Lista series filtradas por género
     * @param genero - Género a buscar
     * @return Lista de series del género especificado
     */
    public List<Serie> listarSeriesPorGenero(String genero) {
        List<Serie> series = new ArrayList<>();
        String sql = "SELECT * FROM series WHERE genero LIKE ? ORDER BY titulo";
        
        try (Connection conn = Conexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + genero + "%");
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Serie serie = new Serie();
                serie.setId(rs.getInt("id"));
                serie.setCodigo(rs.getString("codigo"));
                serie.setTitulo(rs.getString("titulo"));
                serie.setFecha(rs.getString("fecha"));
                serie.setTemporadas(rs.getString("temporadas"));
                serie.setGenero(rs.getString("genero"));
                serie.setActores(rs.getString("actores"));
                serie.setSinopsis(rs.getString("sinopsis"));
                series.add(serie);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al buscar series por género: " + e.getMessage());
        }
        
        return series;
    }
    
    /**
     * Verifica si existe una serie con el código dado
     * @param codigo - Código a verificar
     * @return true si existe, false si no
     */
    public boolean existeCodigo(String codigo) {
        String sql = "SELECT COUNT(*) FROM series WHERE codigo = ?";
        
        try (Connection conn = Conexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, codigo);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("Error al verificar código: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Cuenta el total de series en la base de datos
     * @return Número total de series
     */
    public int contarSeries() {
        String sql = "SELECT COUNT(*) FROM series";
        
        try (Connection conn = Conexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al contar series: " + e.getMessage());
        }
        
        return 0;
    }
}
/**
 *
 * @author poved
 */
public class SerieDaO {
    
}
