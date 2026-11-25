package com.sursalud.app.dto;

import com.sursalud.app.model.Medico;

public class MedicoResponse {

    private Long id;
    private String nombre;
    private String especialidad;
    private String consultorio;

    public MedicoResponse(Medico m) {
        this.id = m.getId();
        this.nombre = m.getNombre();
        this.especialidad = m.getEspecialidad();
        this.consultorio = m.getConsultorio();
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public String getConsultorio() {
        return consultorio;
    }
}
