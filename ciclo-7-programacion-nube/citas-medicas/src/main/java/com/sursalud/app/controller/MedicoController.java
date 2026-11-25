package com.sursalud.app.controller;

import com.sursalud.app.dto.MedicoRequest;
import com.sursalud.app.dto.MedicoResponse;
import com.sursalud.app.model.Medico;
import com.sursalud.app.service.MedicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// El enunciado usa "/médicos" con tilde, pero se deja "/medicos" sin tilde
// porque en una URL un caracter con tilde toca escribirlo codificado (%C3%A9)
@RestController
@RequestMapping("/medicos")
@Tag(name = "Medicos")
public class MedicoController {

    private final MedicoService medicoService;

    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @Operation(summary = "Consultar medicos")
    @GetMapping
    public List<MedicoResponse> listar() {
        return medicoService.listarTodos().stream().map(MedicoResponse::new).toList();
    }

    @Operation(summary = "Consultar un medico por id")
    @GetMapping("/{id}")
    public MedicoResponse obtener(@PathVariable Long id) {
        return new MedicoResponse(medicoService.obtenerPorId(id));
    }

    @Operation(summary = "Registrar medico")
    @PostMapping
    public ResponseEntity<MedicoResponse> crear(@Valid @RequestBody MedicoRequest request) {
        Medico creado = medicoService.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MedicoResponse(creado));
    }

    @Operation(summary = "Actualizar datos de un medico (especialidad, consultorio, etc)")
    @PutMapping("/{id}")
    public MedicoResponse actualizar(@PathVariable Long id, @Valid @RequestBody MedicoRequest request) {
        return new MedicoResponse(medicoService.actualizar(id, request));
    }

    @Operation(summary = "Eliminar medico")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        medicoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
