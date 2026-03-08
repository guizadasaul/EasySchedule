package com.easyschedule.backend.oferta.service;

import com.easyschedule.backend.estudiante.model.Estudiante;
import com.easyschedule.backend.estudiante.repository.EstudianteRepository;
import com.easyschedule.backend.materia.model.Materia;
import com.easyschedule.backend.materia.repository.MateriaRepository;
import com.easyschedule.backend.oferta.dto.OfertaRequest;
import com.easyschedule.backend.oferta.dto.OfertaResponse;
import com.easyschedule.backend.oferta.model.Oferta;
import com.easyschedule.backend.oferta.repository.OfertaRepository;
import com.easyschedule.backend.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class OfertaService {

    private final OfertaRepository ofertaRepository;
    private final EstudianteRepository estudianteRepository;
    private final MateriaRepository materiaRepository;

    public OfertaService(OfertaRepository ofertaRepository, EstudianteRepository estudianteRepository, MateriaRepository materiaRepository) {
        this.ofertaRepository = ofertaRepository;
        this.estudianteRepository = estudianteRepository;
        this.materiaRepository = materiaRepository;
    }

    public List<OfertaResponse> findAll() {
        return ofertaRepository.findAll().stream().map(this::toResponse).toList();
    }

    public OfertaResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    public OfertaResponse create(OfertaRequest request) {
        Estudiante estudiante = getEstudianteOrThrow(request.estudianteId());
        Materia materia = getMateriaOrThrow(request.materiaId());

        Oferta oferta = new Oferta();
        oferta.setEstudiante(estudiante);
        oferta.setMateria(materia);
        oferta.setSemestre(request.semestre());
        oferta.setHorarioJson(request.horarioJson());
        oferta.setDocente(request.docente());
        oferta.setAula(request.aula());
        oferta.setFechaCreacion(OffsetDateTime.now());
        oferta.setFechaActualizacion(OffsetDateTime.now());

        return toResponse(ofertaRepository.save(oferta));
    }

    public OfertaResponse update(Long id, OfertaRequest request) {
        Oferta oferta = getOrThrow(id);
        Estudiante estudiante = getEstudianteOrThrow(request.estudianteId());
        Materia materia = getMateriaOrThrow(request.materiaId());

        oferta.setEstudiante(estudiante);
        oferta.setMateria(materia);
        oferta.setSemestre(request.semestre());
        oferta.setHorarioJson(request.horarioJson());
        oferta.setDocente(request.docente());
        oferta.setAula(request.aula());
        oferta.setFechaActualizacion(OffsetDateTime.now());

        return toResponse(ofertaRepository.save(oferta));
    }

    public void delete(Long id) {
        Oferta oferta = getOrThrow(id);
        ofertaRepository.delete(oferta);
    }

    private Oferta getOrThrow(Long id) {
        return ofertaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Oferta no encontrada con id: " + id));
    }

    private Estudiante getEstudianteOrThrow(Long id) {
        return estudianteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado con id: " + id));
    }

    private Materia getMateriaOrThrow(Long id) {
        return materiaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Materia no encontrada con id: " + id));
    }

    private OfertaResponse toResponse(Oferta oferta) {
        return new OfertaResponse(
            oferta.getId(),
            oferta.getEstudiante().getId(),
            oferta.getMateria().getId(),
            oferta.getSemestre(),
            oferta.getHorarioJson(),
            oferta.getDocente(),
            oferta.getAula(),
            oferta.getFechaCreacion(),
            oferta.getFechaActualizacion()
        );
    }
}
