package com.easyschedule.backend.toma_materia.dto;

public record TomaMateriaRequest(
    Long userId,
    Long mallaMateriaId,
    String estado
) {
}
