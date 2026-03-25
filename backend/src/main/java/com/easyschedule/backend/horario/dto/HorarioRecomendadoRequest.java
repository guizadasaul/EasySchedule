package com.easyschedule.backend.horario.dto;

public record HorarioRecomendadoRequest(
    Long userId,
    String semestre,
    String jsonResultado
) {
}
