package com.easyschedule.backend.oferta.dto;

import java.time.OffsetDateTime;

public record OfertaResponse(
    Long id,
    Long userId,
    Long mallaMateriaId,
    String semestre,
    String paralelo,
    String horarioJson,
    String docente,
    String aula,
    OffsetDateTime fechaCreacion,
    OffsetDateTime fechaActualizacion
) {
}
