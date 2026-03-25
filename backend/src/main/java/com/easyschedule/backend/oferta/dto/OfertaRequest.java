package com.easyschedule.backend.oferta.dto;

public record OfertaRequest(
    Long userId,
    Long mallaMateriaId,
    String semestre,
    String paralelo,
    String horarioJson,
    String docente,
    String aula
) {
}
