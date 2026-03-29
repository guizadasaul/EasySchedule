package com.easyschedule.backend.toma_materia.dto;

import java.time.OffsetDateTime;

public record TomaMateriaResponse(
    Long id,
    Long userId,
    Long mallaMateriaId,
    String estado,
    OffsetDateTime fechaActualizacion
) {
}
