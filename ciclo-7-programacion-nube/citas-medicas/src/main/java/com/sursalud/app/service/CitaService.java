package com.sursalud.app.service;

import com.sursalud.app.dto.CitaRequest;
import com.sursalud.app.dto.ResumenReporteResponse;
import com.sursalud.app.model.Cita;
import com.sursalud.app.model.EstadoCita;
import com.sursalud.app.model.Medico;
import com.sursalud.app.model.Paciente;
import com.sursalud.app.repository.CitaRepository;
import com.sursalud.app.repository.MedicoRepository;
import com.sursalud.app.repository.PacienteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CitaService {

    private final CitaRepository citaRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;

    public CitaService(CitaRepository citaRepository, PacienteRepository pacienteRepository,
            MedicoRepository medicoRepository) {
        this.citaRepository = citaRepository;
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
    }

    public List<Cita> listarTodas() {
        return citaRepository.findAll();
    }

    public Cita obtenerPorId(Long id) {
        return citaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No existe una cita con id " + id));
    }

    public List<Cita> listarPorMedico(Long medicoId) {
        if (!medicoRepository.existsById(medicoId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe un medico con id " + medicoId);
        }
        return citaRepository.findByMedicoId(medicoId);
    }

    public List<Cita> listarPorPaciente(Long pacienteId) {
        if (!pacienteRepository.existsById(pacienteId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe un paciente con id " + pacienteId);
        }
        return citaRepository.findByPacienteId(pacienteId);
    }

    public List<Cita> listarPorEstado(EstadoCita estado) {
        return citaRepository.findByEstado(estado);
    }

    // Para el panel de reportes de administracion: cuantas citas hay en cada estado
    public ResumenReporteResponse generarResumen() {
        return new ResumenReporteResponse(
                citaRepository.findByEstado(EstadoCita.PENDIENTE).size(),
                citaRepository.findByEstado(EstadoCita.CONFIRMADA).size(),
                citaRepository.findByEstado(EstadoCita.CANCELADA).size(),
                citaRepository.findByEstado(EstadoCita.ATENDIDA).size());
    }

    public Cita solicitar(CitaRequest request) {
        Paciente paciente = pacienteRepository.findById(request.getPacienteId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No existe un paciente con id " + request.getPacienteId()));
        Medico medico = medicoRepository.findById(request.getMedicoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No existe un medico con id " + request.getMedicoId()));

        // que un medico no tenga dos citas activas a la misma hora
        boolean cruceHorario = citaRepository.existsByMedicoIdAndFechaAndHoraAndEstadoNot(
                medico.getId(), request.getFecha(), request.getHora(), EstadoCita.CANCELADA);
        if (cruceHorario) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El medico ya tiene una cita agendada en esa fecha y hora");
        }

        Cita cita = new Cita(request.getFecha(), request.getHora(), EstadoCita.PENDIENTE, paciente, medico);
        return citaRepository.save(cita);
    }

    public Cita reprogramar(Long id, CitaRequest request) {
        Cita cita = obtenerPorId(id);
        Medico medico = medicoRepository.findById(request.getMedicoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No existe un medico con id " + request.getMedicoId()));
        Paciente paciente = pacienteRepository.findById(request.getPacienteId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No existe un paciente con id " + request.getPacienteId()));

        cita.setFecha(request.getFecha());
        cita.setHora(request.getHora());
        cita.setMedico(medico);
        cita.setPaciente(paciente);
        return citaRepository.save(cita);
    }

    public Cita cambiarEstado(Long id, EstadoCita nuevoEstado) {
        Cita cita = obtenerPorId(id);
        cita.setEstado(nuevoEstado);
        return citaRepository.save(cita);
    }

    // DELETE /citas/{id} del enunciado: cancelar = eliminar la cita
    public void cancelar(Long id) {
        citaRepository.delete(obtenerPorId(id));
    }
}
