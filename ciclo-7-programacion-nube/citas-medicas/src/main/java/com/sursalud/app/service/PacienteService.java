package com.sursalud.app.service;

import com.sursalud.app.dto.PacienteRequest;
import com.sursalud.app.model.Paciente;
import com.sursalud.app.repository.PacienteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public List<Paciente> listarTodos() {
        return pacienteRepository.findAll();
    }

    public Paciente obtenerPorId(Long id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No existe un paciente con id " + id));
    }

    public Paciente registrar(PacienteRequest request) {
        // no dejamos registrar dos pacientes con el mismo numero de documento
        if (pacienteRepository.existsByDocumento(request.getDocumento())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Ya existe un paciente con el documento " + request.getDocumento());
        }
        Paciente paciente = new Paciente(request.getNombre(), request.getDocumento(), request.getTelefono(),
                request.getCorreo());
        return pacienteRepository.save(paciente);
    }

    public Paciente actualizar(Long id, PacienteRequest request) {
        Paciente paciente = obtenerPorId(id);
        paciente.setNombre(request.getNombre());
        paciente.setTelefono(request.getTelefono());
        paciente.setCorreo(request.getCorreo());
        return pacienteRepository.save(paciente);
    }

    public void eliminar(Long id) {
        pacienteRepository.delete(obtenerPorId(id));
    }
}
