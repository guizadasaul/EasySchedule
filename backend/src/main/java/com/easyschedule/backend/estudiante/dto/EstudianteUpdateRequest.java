
package com.easyschedule.backend.estudiante.dto;

import java.time.LocalDate;

public record EstudianteUpdateRequest(
    String nombre,
    String apellido,
    String carnetIdentidad,
    LocalDate fechaNacimiento,
    Short semestreActual,
    Long universidadId,
    Long carreraId,
    Long mallaId
) {
}
