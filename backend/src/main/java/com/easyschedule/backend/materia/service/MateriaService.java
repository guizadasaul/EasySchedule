package com.easyschedule.backend.materia.service;

import com.easyschedule.backend.materia.dto.MateriaRequest;
import com.easyschedule.backend.materia.dto.MateriaResponse;
import com.easyschedule.backend.materia.dto.PrerequisitoRequest;
import com.easyschedule.backend.malla.model.MallaMateria;
import com.easyschedule.backend.malla.repository.MallaMateriaRepository;
import com.easyschedule.backend.materia.model.Materia;
import com.easyschedule.backend.materia.model.Prerequisito;
import com.easyschedule.backend.materia.repository.MateriaRepository;
import com.easyschedule.backend.materia.repository.PrerequisitoRepository;
import com.easyschedule.backend.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MateriaService {

    private final MateriaRepository materiaRepository;
    private final PrerequisitoRepository prerequisitoRepository;
    private final MallaMateriaRepository mallaMateriaRepository;

    public MateriaService(
        MateriaRepository materiaRepository,
        PrerequisitoRepository prerequisitoRepository,
        MallaMateriaRepository mallaMateriaRepository
    ) {
        this.materiaRepository = materiaRepository;
        this.prerequisitoRepository = prerequisitoRepository;
        this.mallaMateriaRepository = mallaMateriaRepository;
    }

    public List<MateriaResponse> findAllMaterias() {
        return materiaRepository.findAll().stream().map(this::toResponse).toList();
    }

    public MateriaResponse findMateriaById(Long id) {
        return toResponse(getMateriaOrThrow(id));
    }

    public MateriaResponse createMateria(MateriaRequest request) {
        Materia materia = new Materia();
        materia.setCodigo(request.codigo());
        materia.setNombre(request.nombre());
        materia.setCreditos(request.creditos());
        materia.setActive(request.active() == null || request.active());
        return toResponse(materiaRepository.save(materia));
    }

    public MateriaResponse updateMateria(Long id, MateriaRequest request) {
        Materia materia = getMateriaOrThrow(id);
        materia.setCodigo(request.codigo());
        materia.setNombre(request.nombre());
        materia.setCreditos(request.creditos());
        materia.setActive(request.active() == null || request.active());
        return toResponse(materiaRepository.save(materia));
    }

    public void deleteMateria(Long id) {
        Materia materia = getMateriaOrThrow(id);
        materiaRepository.delete(materia);
    }

    public List<Prerequisito> findAllPrerequisitos() {
        return prerequisitoRepository.findAll();
    }

    public Prerequisito createPrerequisito(PrerequisitoRequest request) {
        MallaMateria mallaMateria = getMallaMateriaOrThrow(request.mallaMateriaId());
        MallaMateria prerequisitoMallaMateria = getMallaMateriaOrThrow(request.prerequisitoMallaMateriaId());

        Prerequisito prerequisito = new Prerequisito();
        prerequisito.setMallaMateria(mallaMateria);
        prerequisito.setPrerequisito(prerequisitoMallaMateria);
        return prerequisitoRepository.save(prerequisito);
    }

    public void deletePrerequisito(Long id) {
        Prerequisito prerequisito = prerequisitoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Prerequisito no encontrado con id: " + id));
        prerequisitoRepository.delete(prerequisito);
    }

    private Materia getMateriaOrThrow(Long id) {
        return materiaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Materia no encontrada con id: " + id));
    }

    private MallaMateria getMallaMateriaOrThrow(Long id) {
        return mallaMateriaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("MallaMateria no encontrada con id: " + id));
    }

    private MateriaResponse toResponse(Materia materia) {
        return new MateriaResponse(
            materia.getId(),
            materia.getCodigo(),
            materia.getNombre(),
            materia.getCreditos(),
            materia.isActive()
        );
    }
}
