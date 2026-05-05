package com.easyschedule.backend.academico.toma_materia.dto;

import java.time.OffsetDateTime;

public record TomaMateriaResponse(
    Long id,
    Long userId,
    Long ofertaId,
    String estado,
    OffsetDateTime fechaInscripcion,
    OffsetDateTime fechaActualizacion
) {
}
