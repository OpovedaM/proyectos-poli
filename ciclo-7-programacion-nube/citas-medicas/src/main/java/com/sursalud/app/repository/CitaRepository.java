package com.sursalud.app.repository;

import com.sursalud.app.model.Cita;
import com.sursalud.app.model.EstadoCita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Long> {

    List<Cita> findByMedicoId(Long medicoId);

    List<Cita> findByMedicoIdAndFecha(Long medicoId, LocalDate fecha);

    List<Cita> findByPacienteId(Long pacienteId);

    List<Cita> findByEstado(EstadoCita estado);

    boolean existsByMedicoIdAndFechaAndHoraAndEstadoNot(Long medicoId, LocalDate fecha, java.time.LocalTime hora,
            EstadoCita estadoExcluido);
}
