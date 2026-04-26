package com.easyschedule.backend.academico.estado_materia.dto;

import java.time.OffsetDateTime;

public record EstadoMateriaResponse(
    Long id,
    Long userId,
    Long mallaMateriaId,
    String estado,
    OffsetDateTime fechaActualizacion
) {
}
