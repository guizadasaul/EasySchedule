package com.easyschedule.backend.oferta.dto;

public record OfertaRequest(
    Long estudianteId,
    Long materiaId,
    String semestre,
    String horarioJson,
    String docente,
    String aula
) {
}
