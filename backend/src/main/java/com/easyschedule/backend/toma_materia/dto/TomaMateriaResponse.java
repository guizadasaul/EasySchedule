package com.easyschedule.backend.toma_materia.dto;

import java.time.OffsetDateTime;

public record TomaMateriaResponse(
    Long id,
    Long estudianteId,
    Long materiaId,
    String estado,
    OffsetDateTime fechaActualizacion
) {
}
