package com.easyschedule.backend.academico.horario.dto;

import java.util.List;

public record HorarioActualResponse(
    String universidad,
    String carrera,
    String malla,
    String semestreOferta,
    Short semestreActual,
    List<HorarioClaseResponse> clases
) {
}
