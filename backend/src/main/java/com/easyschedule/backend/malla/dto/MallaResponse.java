package com.easyschedule.backend.malla.dto;

public record MallaResponse(
    Long id,
    Long carreraId,
    String nombre,
    String version,
    boolean active
) {
}
