package com.easyschedule.backend.estudiante.service;

import com.easyschedule.backend.estudiante.dto.EstudianteRequest;
import com.easyschedule.backend.estudiante.dto.EstudianteResponse;
import com.easyschedule.backend.estudiante.model.Estudiante;
import com.easyschedule.backend.estudiante.repository.EstudianteRepository;
import com.easyschedule.backend.malla.model.Malla;
import com.easyschedule.backend.malla.repository.MallaRepository;
import com.easyschedule.backend.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class EstudianteService {

    private final EstudianteRepository estudianteRepository;
    private final MallaRepository mallaRepository;

    public EstudianteService(EstudianteRepository estudianteRepository, MallaRepository mallaRepository) {
        this.estudianteRepository = estudianteRepository;
        this.mallaRepository = mallaRepository;
    }

    public List<EstudianteResponse> findAll() {
        return estudianteRepository.findAll().stream().map(this::toResponse).toList();
    }

    public EstudianteResponse findById(Long id) {
        return toResponse(getEstudianteOrThrow(id));
    }

    public EstudianteResponse create(EstudianteRequest request) {
        Malla malla = getMallaOrThrow(request.mallaId());

        Estudiante estudiante = new Estudiante();
        fillFromRequest(estudiante, request, malla);
        estudiante.setFechaRegistro(OffsetDateTime.now());
        return toResponse(estudianteRepository.save(estudiante));
    }

    public EstudianteResponse update(Long id, EstudianteRequest request) {
        Estudiante estudiante = getEstudianteOrThrow(id);
        Malla malla = getMallaOrThrow(request.mallaId());

        fillFromRequest(estudiante, request, malla);
        return toResponse(estudianteRepository.save(estudiante));
    }

    public void delete(Long id) {
        Estudiante estudiante = getEstudianteOrThrow(id);
        estudianteRepository.delete(estudiante);
    }

    private void fillFromRequest(Estudiante estudiante, EstudianteRequest request, Malla malla) {
        estudiante.setUsername(request.username());
        estudiante.setNombre(request.nombre());
        estudiante.setApellido(request.apellido());
        estudiante.setCorreo(request.correo());
        estudiante.setPasswordHash(request.passwordHash());
        estudiante.setCarnetIdentidad(request.carnetIdentidad());
        estudiante.setFechaNacimiento(request.fechaNacimiento());
        estudiante.setSemestreActual(request.semestreActual());
        estudiante.setCarrera(request.carrera());
        estudiante.setMalla(malla);
    }

    private Estudiante getEstudianteOrThrow(Long id) {
        return estudianteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado con id: " + id));
    }

    private Malla getMallaOrThrow(Long id) {
        return mallaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Malla no encontrada con id: " + id));
    }

    private EstudianteResponse toResponse(Estudiante estudiante) {
        return new EstudianteResponse(
            estudiante.getId(),
            estudiante.getUsername(),
            estudiante.getNombre(),
            estudiante.getApellido(),
            estudiante.getCorreo(),
            estudiante.getCarnetIdentidad(),
            estudiante.getFechaNacimiento(),
            estudiante.getFechaRegistro(),
            estudiante.getSemestreActual(),
            estudiante.getCarrera(),
            estudiante.getMalla().getId()
        );
    }
}
