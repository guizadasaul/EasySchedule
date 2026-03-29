package com.easyschedule.backend.horario.dto;

import java.time.OffsetDateTime;

public record HorarioRecomendadoResponse(
    Long id,
    Long userId,
    String semestre,
    String jsonResultado,
    OffsetDateTime fechaGeneracion
) {
}
