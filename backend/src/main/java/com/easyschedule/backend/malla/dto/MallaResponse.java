package com.easyschedule.backend.malla.dto;

public record MallaResponse(
    Long id,
    String carrera,
    String universidad,
    String version
) {
}
