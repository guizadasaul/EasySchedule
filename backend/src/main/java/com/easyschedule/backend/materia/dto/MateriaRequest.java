package com.easyschedule.backend.materia.dto;

public record MateriaRequest(
    String codigo,
    String nombre,
    Short semestreSugerido,
    Short creditos
) {
}
