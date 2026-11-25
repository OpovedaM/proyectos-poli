package com.sursalud.app.controller;

import com.sursalud.app.dto.CambioEstadoRequest;
import com.sursalud.app.dto.CitaRequest;
import com.sursalud.app.dto.CitaResponse;
import com.sursalud.app.dto.ResumenReporteResponse;
import com.sursalud.app.model.Cita;
import com.sursalud.app.model.EstadoCita;
import com.sursalud.app.service.CitaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Solicitar, consultar, reprogramar, cambiar de estado y cancelar citas.
// Incluye la agenda por medico y los reportes que pide la administracion.
@RestController
@RequestMapping("/citas")
@Tag(name = "Citas")
public class CitaController {

    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    @Operation(summary = "Consultar todas las citas")
    @GetMapping
    public List<CitaResponse> listar() {
        return citaService.listarTodas().stream().map(CitaResponse::new).toList();
    }

    @Operation(summary = "Consultar una cita por id")
    @GetMapping("/{id}")
    public CitaResponse obtener(@PathVariable Long id) {
        return new CitaResponse(citaService.obtenerPorId(id));
    }

    @Operation(summary = "Agenda de un medico")
    @GetMapping("/medico/{medicoId}")
    public List<CitaResponse> agendaDelMedico(@PathVariable Long medicoId) {
        return citaService.listarPorMedico(medicoId).stream().map(CitaResponse::new).toList();
    }

    @Operation(summary = "Historial de citas de un paciente")
    @GetMapping("/paciente/{pacienteId}")
    public List<CitaResponse> historialDelPaciente(@PathVariable Long pacienteId) {
        return citaService.listarPorPaciente(pacienteId).stream().map(CitaResponse::new).toList();
    }

    @Operation(summary = "Reporte de citas por estado (PENDIENTE, CONFIRMADA, CANCELADA, ATENDIDA)")
    @GetMapping("/estado/{estado}")
    public List<CitaResponse> porEstado(@PathVariable EstadoCita estado) {
        return citaService.listarPorEstado(estado).stream().map(CitaResponse::new).toList();
    }

    @Operation(summary = "Resumen de citas por estado (para el panel de administracion)")
    @GetMapping("/reportes/resumen")
    public ResumenReporteResponse resumen() {
        return citaService.generarResumen();
    }

    @Operation(summary = "Solicitar cita")
    @PostMapping
    public ResponseEntity<CitaResponse> crear(@Valid @RequestBody CitaRequest request) {
        Cita creada = citaService.solicitar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CitaResponse(creada));
    }

    @Operation(summary = "Reprogramar una cita (fecha, hora, medico o paciente)")
    @PutMapping("/{id}")
    public CitaResponse reprogramar(@PathVariable Long id, @Valid @RequestBody CitaRequest request) {
        return new CitaResponse(citaService.reprogramar(id, request));
    }

    @Operation(summary = "Cambiar el estado de una cita (confirmar, atender, cancelar)")
    @PatchMapping("/{id}/estado")
    public CitaResponse cambiarEstado(@PathVariable Long id, @Valid @RequestBody CambioEstadoRequest request) {
        return new CitaResponse(citaService.cambiarEstado(id, request.getEstado()));
    }

    @Operation(summary = "Cancelar cita")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        citaService.cancelar(id);
        return ResponseEntity.noContent().build();
    }
}
