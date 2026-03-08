package com.easyschedule.backend.oferta.dto;

import java.time.OffsetDateTime;

public record OfertaResponse(
    Long id,
    Long estudianteId,
    Long materiaId,
    String semestre,
    String horarioJson,
    String docente,
    String aula,
    OffsetDateTime fechaCreacion,
    OffsetDateTime fechaActualizacion
) {
}
