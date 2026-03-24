package com.easyschedule.backend.malla.service;

import com.easyschedule.backend.malla.dto.MallaMateriaRequest;
import com.easyschedule.backend.malla.dto.MallaRequest;
import com.easyschedule.backend.malla.dto.MallaResponse;
import com.easyschedule.backend.malla.model.Malla;
import com.easyschedule.backend.malla.model.MallaMateria;
import com.easyschedule.backend.malla.repository.MallaMateriaRepository;
import com.easyschedule.backend.malla.repository.MallaRepository;
import com.easyschedule.backend.materia.model.Materia;
import com.easyschedule.backend.materia.repository.MateriaRepository;
import com.easyschedule.backend.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MallaService {

    private final MallaRepository mallaRepository;
    private final MallaMateriaRepository mallaMateriaRepository;
    private final MateriaRepository materiaRepository;

    public MallaService(MallaRepository mallaRepository, MallaMateriaRepository mallaMateriaRepository, MateriaRepository materiaRepository) {
        this.mallaRepository = mallaRepository;
        this.mallaMateriaRepository = mallaMateriaRepository;
        this.materiaRepository = materiaRepository;
    }

    public List<MallaResponse> findAllMallas() {
        return mallaRepository.findAll().stream().map(this::toResponse).toList();
    }

    public MallaResponse findMallaById(Long id) {
        return toResponse(getMallaOrThrow(id));
    }

    public MallaResponse createMalla(MallaRequest request) {
        Malla malla = new Malla();
        malla.setCarreraId(request.carreraId());
        malla.setNombre(request.nombre());
        malla.setVersion(request.version());
        malla.setActive(request.active() == null || request.active());
        return toResponse(mallaRepository.save(malla));
    }

    public MallaResponse updateMalla(Long id, MallaRequest request) {
        Malla malla = getMallaOrThrow(id);
        malla.setCarreraId(request.carreraId());
        malla.setNombre(request.nombre());
        malla.setVersion(request.version());
        malla.setActive(request.active() == null || request.active());
        return toResponse(mallaRepository.save(malla));
    }

    public void deleteMalla(Long id) {
        Malla malla = getMallaOrThrow(id);
        mallaRepository.delete(malla);
    }

    public List<MallaMateria> findAllMallaMaterias() {
        return mallaMateriaRepository.findAll();
    }

    public MallaMateria createMallaMateria(MallaMateriaRequest request) {
        Malla malla = getMallaOrThrow(request.mallaId());
        Materia materia = materiaRepository.findById(request.materiaId())
            .orElseThrow(() -> new ResourceNotFoundException("Materia no encontrada con id: " + request.materiaId()));

        MallaMateria mallaMateria = new MallaMateria();
        mallaMateria.setMalla(malla);
        mallaMateria.setMateria(materia);
        mallaMateria.setSemestreSugerido(request.semestreSugerido());
        return mallaMateriaRepository.save(mallaMateria);
    }

    public void deleteMallaMateria(Long id) {
        MallaMateria mallaMateria = mallaMateriaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("MallaMateria no encontrada con id: " + id));
        mallaMateriaRepository.delete(mallaMateria);
    }

    private Malla getMallaOrThrow(Long id) {
        return mallaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Malla no encontrada con id: " + id));
    }

    private MallaResponse toResponse(Malla malla) {
        return new MallaResponse(
            malla.getId(),
            malla.getCarreraId(),
            malla.getNombre(),
            malla.getVersion(),
            malla.isActive()
        );
    }
}
