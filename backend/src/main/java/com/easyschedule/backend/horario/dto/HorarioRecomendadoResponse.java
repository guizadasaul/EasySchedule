package com.easyschedule.backend.horario.dto;

import java.time.OffsetDateTime;

public record HorarioRecomendadoResponse(
    Long id,
    Long estudianteId,
    String semestre,
    String jsonResultado,
    OffsetDateTime fechaGeneracion
) {
}
