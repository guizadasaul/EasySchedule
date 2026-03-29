package com.easyschedule.backend.materia.dto;

public record PrerequisitoRequest(
    Long mallaMateriaId,
    Long prerequisitoMallaMateriaId
) {
}
