package com.easyschedule.backend.toma_materia.dto;

public record TomaMateriaRequest(
    Long estudianteId,
    Long materiaId,
    String estado
) {
}
