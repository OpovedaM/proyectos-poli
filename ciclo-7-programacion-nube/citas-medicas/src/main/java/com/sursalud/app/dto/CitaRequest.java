package com.sursalud.app.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public class CitaRequest {

    @NotNull(message = "la fecha es obligatoria")
    private LocalDate fecha;

    @NotNull(message = "la hora es obligatoria")
    private LocalTime hora;

    @NotNull(message = "el id del paciente es obligatorio")
    private Long pacienteId;

    @NotNull(message = "el id del medico es obligatorio")
    private Long medicoId;

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public Long getMedicoId() {
        return medicoId;
    }

    public void setMedicoId(Long medicoId) {
        this.medicoId = medicoId;
    }
}
