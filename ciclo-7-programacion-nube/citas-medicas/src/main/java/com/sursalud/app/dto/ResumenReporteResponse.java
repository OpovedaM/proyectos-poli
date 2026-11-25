package com.sursalud.app.dto;

/**
 * Conteo de citas agrupado por estado, usado por el modulo de reportes de
 * la administracion (control centralizado y disponibilidad).
 */
public class ResumenReporteResponse {

    private long pendientes;
    private long confirmadas;
    private long canceladas;
    private long atendidas;
    private long total;

    public ResumenReporteResponse(long pendientes, long confirmadas, long canceladas, long atendidas) {
        this.pendientes = pendientes;
        this.confirmadas = confirmadas;
        this.canceladas = canceladas;
        this.atendidas = atendidas;
        this.total = pendientes + confirmadas + canceladas + atendidas;
    }

    public long getPendientes() {
        return pendientes;
    }

    public long getConfirmadas() {
        return confirmadas;
    }

    public long getCanceladas() {
        return canceladas;
    }

    public long getAtendidas() {
        return atendidas;
    }

    public long getTotal() {
        return total;
    }
}
