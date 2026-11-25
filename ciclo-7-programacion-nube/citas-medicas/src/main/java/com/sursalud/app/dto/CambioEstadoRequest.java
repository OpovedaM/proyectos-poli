package com.sursalud.app.dto;

import com.sursalud.app.model.EstadoCita;
import jakarta.validation.constraints.NotNull;

/**
 * Cuerpo para cambiar el estado de una cita (confirmar, atender, cancelar).
 */
public class CambioEstadoRequest {

    @NotNull(message = "el nuevo estado es obligatorio")
    private EstadoCita estado;

    public EstadoCita getEstado() {
        return estado;
    }

    public void setEstado(EstadoCita estado) {
        this.estado = estado;
    }
}
