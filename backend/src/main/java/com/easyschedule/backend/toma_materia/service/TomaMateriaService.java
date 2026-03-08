package com.easyschedule.backend.toma_materia.service;

import com.easyschedule.backend.estudiante.model.Estudiante;
import com.easyschedule.backend.estudiante.repository.EstudianteRepository;
import com.easyschedule.backend.materia.model.Materia;
import com.easyschedule.backend.materia.repository.MateriaRepository;
import com.easyschedule.backend.shared.exception.ResourceNotFoundException;
import com.easyschedule.backend.toma_materia.dto.TomaMateriaRequest;
import com.easyschedule.backend.toma_materia.dto.TomaMateriaResponse;
import com.easyschedule.backend.toma_materia.model.TomaMateria;
import com.easyschedule.backend.toma_materia.repository.TomaMateriaRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

@Service
public class TomaMateriaService {

    private static final Set<String> ESTADOS_VALIDOS = Set.of("aprobada", "pendiente", "cursando");

    private final TomaMateriaRepository tomaMateriaRepository;
    private final EstudianteRepository estudianteRepository;
    private final MateriaRepository materiaRepository;

    public TomaMateriaService(TomaMateriaRepository tomaMateriaRepository, EstudianteRepository estudianteRepository, MateriaRepository materiaRepository) {
        this.tomaMateriaRepository = tomaMateriaRepository;
        this.estudianteRepository = estudianteRepository;
        this.materiaRepository = materiaRepository;
    }

    public List<TomaMateriaResponse> findAll() {
        return tomaMateriaRepository.findAll().stream().map(this::toResponse).toList();
    }

    public TomaMateriaResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    public TomaMateriaResponse create(TomaMateriaRequest request) {
        validarEstado(request.estado());
        Estudiante estudiante = getEstudianteOrThrow(request.estudianteId());
        Materia materia = getMateriaOrThrow(request.materiaId());

        TomaMateria tomaMateria = new TomaMateria();
        tomaMateria.setEstudiante(estudiante);
        tomaMateria.setMateria(materia);
        tomaMateria.setEstado(request.estado());
        tomaMateria.setFechaActualizacion(OffsetDateTime.now());

        return toResponse(tomaMateriaRepository.save(tomaMateria));
    }

    public TomaMateriaResponse update(Long id, TomaMateriaRequest request) {
        validarEstado(request.estado());
        TomaMateria tomaMateria = getOrThrow(id);
        Estudiante estudiante = getEstudianteOrThrow(request.estudianteId());
        Materia materia = getMateriaOrThrow(request.materiaId());

        tomaMateria.setEstudiante(estudiante);
        tomaMateria.setMateria(materia);
        tomaMateria.setEstado(request.estado());
        tomaMateria.setFechaActualizacion(OffsetDateTime.now());

        return toResponse(tomaMateriaRepository.save(tomaMateria));
    }

    public void delete(Long id) {
        TomaMateria tomaMateria = getOrThrow(id);
        tomaMateriaRepository.delete(tomaMateria);
    }

    private void validarEstado(String estado) {
        if (!ESTADOS_VALIDOS.contains(estado)) {
            throw new IllegalArgumentException("Estado invalido. Valores permitidos: aprobada, pendiente, cursando.");
        }
    }

    private TomaMateria getOrThrow(Long id) {
        return tomaMateriaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("TomaMateria no encontrada con id: " + id));
    }

    private Estudiante getEstudianteOrThrow(Long id) {
        return estudianteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado con id: " + id));
    }

    private Materia getMateriaOrThrow(Long id) {
        return materiaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Materia no encontrada con id: " + id));
    }

    private TomaMateriaResponse toResponse(TomaMateria tomaMateria) {
        return new TomaMateriaResponse(
            tomaMateria.getId(),
            tomaMateria.getEstudiante().getId(),
            tomaMateria.getMateria().getId(),
            tomaMateria.getEstado(),
            tomaMateria.getFechaActualizacion()
        );
    }
}
