package com.sursalud.app.controller;

import com.sursalud.app.dto.PacienteRequest;
import com.sursalud.app.dto.PacienteResponse;
import com.sursalud.app.model.Paciente;
import com.sursalud.app.service.PacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Registro y consulta de pacientes (RF: los pacientes se registran y consultan sus citas)
@RestController
@RequestMapping("/pacientes")
@Tag(name = "Pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @Operation(summary = "Consultar pacientes")
    @GetMapping
    public List<PacienteResponse> listar() {
        return pacienteService.listarTodos().stream().map(PacienteResponse::new).toList();
    }

    @Operation(summary = "Consultar un paciente por id")
    @GetMapping("/{id}")
    public PacienteResponse obtener(@PathVariable Long id) {
        return new PacienteResponse(pacienteService.obtenerPorId(id));
    }

    @Operation(summary = "Registrar paciente")
    @PostMapping
    public ResponseEntity<PacienteResponse> crear(@Valid @RequestBody PacienteRequest request) {
        Paciente creado = pacienteService.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new PacienteResponse(creado));
    }

    @Operation(summary = "Actualizar datos de un paciente")
    @PutMapping("/{id}")
    public PacienteResponse actualizar(@PathVariable Long id, @Valid @RequestBody PacienteRequest request) {
        return new PacienteResponse(pacienteService.actualizar(id, request));
    }

    @Operation(summary = "Eliminar paciente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        pacienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
