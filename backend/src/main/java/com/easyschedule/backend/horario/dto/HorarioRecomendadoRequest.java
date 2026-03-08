package com.easyschedule.backend.horario.dto;

public record HorarioRecomendadoRequest(
    Long estudianteId,
    String semestre,
    String jsonResultado
) {
}
