package com.easyschedule.backend.academico.toma_materia.dto;

import jakarta.validation.constraints.NotNull;

public record TomaMateriaRequest(
    @NotNull Long ofertaId,
    String estado
) {
}
