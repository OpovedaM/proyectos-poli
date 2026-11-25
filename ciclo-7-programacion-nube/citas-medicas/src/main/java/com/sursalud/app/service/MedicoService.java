package com.sursalud.app.service;

import com.sursalud.app.dto.MedicoRequest;
import com.sursalud.app.model.Medico;
import com.sursalud.app.repository.MedicoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MedicoService {

    private final MedicoRepository medicoRepository;

    public MedicoService(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    public List<Medico> listarTodos() {
        return medicoRepository.findAll();
    }

    public Medico obtenerPorId(Long id) {
        return medicoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No existe un medico con id " + id));
    }

    public Medico registrar(MedicoRequest request) {
        Medico medico = new Medico(request.getNombre(), request.getEspecialidad(), request.getConsultorio());
        return medicoRepository.save(medico);
    }

    public Medico actualizar(Long id, MedicoRequest request) {
        Medico medico = obtenerPorId(id);
        medico.setNombre(request.getNombre());
        medico.setEspecialidad(request.getEspecialidad());
        medico.setConsultorio(request.getConsultorio());
        return medicoRepository.save(medico);
    }

    public void eliminar(Long id) {
        medicoRepository.delete(obtenerPorId(id));
    }
}
