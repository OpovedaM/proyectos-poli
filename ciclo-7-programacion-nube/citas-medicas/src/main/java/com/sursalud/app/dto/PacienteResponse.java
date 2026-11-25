package com.sursalud.app.dto;

import com.sursalud.app.model.Paciente;

public class PacienteResponse {

    private Long id;
    private String nombre;
    private String documento;
    private String telefono;
    private String correo;

    public PacienteResponse(Paciente p) {
        this.id = p.getId();
        this.nombre = p.getNombre();
        this.documento = p.getDocumento();
        this.telefono = p.getTelefono();
        this.correo = p.getCorreo();
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDocumento() {
        return documento;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }
}
