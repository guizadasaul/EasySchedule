package com.easyschedule.backend.horario.service;

import com.easyschedule.backend.estudiante.model.Estudiante;
import com.easyschedule.backend.estudiante.repository.EstudianteRepository;
import com.easyschedule.backend.horario.dto.HorarioRecomendadoRequest;
import com.easyschedule.backend.horario.dto.HorarioRecomendadoResponse;
import com.easyschedule.backend.horario.model.HorarioRecomendado;
import com.easyschedule.backend.horario.repository.HorarioRecomendadoRepository;
import com.easyschedule.backend.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class HorarioRecomendadoService {

    private final HorarioRecomendadoRepository horarioRecomendadoRepository;
    private final EstudianteRepository estudianteRepository;

    public HorarioRecomendadoService(HorarioRecomendadoRepository horarioRecomendadoRepository, EstudianteRepository estudianteRepository) {
        this.horarioRecomendadoRepository = horarioRecomendadoRepository;
        this.estudianteRepository = estudianteRepository;
    }

    public List<HorarioRecomendadoResponse> findAll() {
        return horarioRecomendadoRepository.findAll().stream().map(this::toResponse).toList();
    }

    public HorarioRecomendadoResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    public HorarioRecomendadoResponse create(HorarioRecomendadoRequest request) {
        Estudiante estudiante = getEstudianteOrThrow(request.estudianteId());

        HorarioRecomendado horario = new HorarioRecomendado();
        horario.setEstudiante(estudiante);
        horario.setSemestre(request.semestre());
        horario.setJsonResultado(request.jsonResultado());
        horario.setFechaGeneracion(OffsetDateTime.now());

        return toResponse(horarioRecomendadoRepository.save(horario));
    }

    public HorarioRecomendadoResponse update(Long id, HorarioRecomendadoRequest request) {
        HorarioRecomendado horario = getOrThrow(id);
        Estudiante estudiante = getEstudianteOrThrow(request.estudianteId());

        horario.setEstudiante(estudiante);
        horario.setSemestre(request.semestre());
        horario.setJsonResultado(request.jsonResultado());

        return toResponse(horarioRecomendadoRepository.save(horario));
    }

    public void delete(Long id) {
        HorarioRecomendado horario = getOrThrow(id);
        horarioRecomendadoRepository.delete(horario);
    }

    private HorarioRecomendado getOrThrow(Long id) {
        return horarioRecomendadoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("HorarioRecomendado no encontrado con id: " + id));
    }

    private Estudiante getEstudianteOrThrow(Long id) {
        return estudianteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado con id: " + id));
    }

    private HorarioRecomendadoResponse toResponse(HorarioRecomendado horario) {
        return new HorarioRecomendadoResponse(
            horario.getId(),
            horario.getEstudiante().getId(),
            horario.getSemestre(),
            horario.getJsonResultado(),
            horario.getFechaGeneracion()
        );
    }
}
