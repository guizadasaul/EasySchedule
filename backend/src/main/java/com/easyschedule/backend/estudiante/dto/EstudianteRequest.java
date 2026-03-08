package com.easyschedule.backend.estudiante.dto;

import java.time.LocalDate;

public record EstudianteRequest(
    String username,
    String nombre,
    String apellido,
    String correo,
    String passwordHash,
    String carnetIdentidad,
    LocalDate fechaNacimiento,
    Short semestreActual,
    String carrera,
    Long mallaId
) {
}
