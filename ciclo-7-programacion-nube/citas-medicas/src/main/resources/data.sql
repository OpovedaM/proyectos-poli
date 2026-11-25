-- Los nombres de columna asumen la convencion por defecto de Hibernate
-- (camelCase -> snake_case).

INSERT INTO medicos (nombre, especialidad, consultorio) VALUES
('Dr. Andres Molina', 'Medicina General', 'Consultorio 101'),
('Dra. Camila Restrepo', 'Pediatria', 'Consultorio 102'),
('Dr. Felipe Salazar', 'Ortopedia', 'Consultorio 201');

INSERT INTO pacientes (nombre, documento, telefono, correo) VALUES
('Ana Torres', '1001', '3001112233', 'ana.torres@example.com'),
('Carlos Ruiz', '1002', '3002223344', 'carlos.ruiz@example.com'),
('Laura Gomez', '1003', '3003334455', 'laura.gomez@example.com');

INSERT INTO citas (fecha, hora, estado, paciente_id, medico_id) VALUES
('2026-09-15', '09:00:00', 'PENDIENTE', 1, 1),
('2026-09-15', '10:30:00', 'CONFIRMADA', 2, 2),
('2026-09-16', '15:00:00', 'CANCELADA', 3, 3);
