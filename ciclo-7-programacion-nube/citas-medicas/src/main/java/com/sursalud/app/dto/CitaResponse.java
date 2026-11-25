package com.sursalud.app.dto;

import com.sursalud.app.model.Cita;

import java.time.LocalDate;
import java.time.LocalTime;

public class CitaResponse {

    private Long id;
    private LocalDate fecha;
    private LocalTime hora;
    private String estado;
    private Long pacienteId;
    private String pacienteNombre;
    private Long medicoId;
    private String medicoNombre;
    private String especialidad;

    public CitaResponse(Cita c) {
        this.id = c.getId();
        this.fecha = c.getFecha();
        this.hora = c.getHora();
        this.estado = c.getEstado().name();
        this.pacienteId = c.getPaciente().getId();
        this.pacienteNombre = c.getPaciente().getNombre();
        this.medicoId = c.getMedico().getId();
        this.medicoNombre = c.getMedico().getNombre();
        this.especialidad = c.getMedico().getEspecialidad();
    }

    public Long getId() {
        return id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public String getEstado() {
        return estado;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public String getPacienteNombre() {
        return pacienteNombre;
    }

    public Long getMedicoId() {
        return medicoId;
    }

    public String getMedicoNombre() {
        return medicoNombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }
}
