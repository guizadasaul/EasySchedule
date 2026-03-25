package com.easyschedule.backend.malla.dto;

public record MallaRequest(
    Long carreraId,
    String nombre,
    String version,
    Boolean active
) {
}
