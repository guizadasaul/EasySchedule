package com.easyschedule.backend.oferta.service;

import com.easyschedule.backend.auth.models.User;
import com.easyschedule.backend.auth.repositories.UserRepository;
import com.easyschedule.backend.malla.model.MallaMateria;
import com.easyschedule.backend.malla.repository.MallaMateriaRepository;
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
    private final UserRepository userRepository;
    private final MallaMateriaRepository mallaMateriaRepository;

    public OfertaService(
        OfertaRepository ofertaRepository,
        UserRepository userRepository,
        MallaMateriaRepository mallaMateriaRepository
    ) {
        this.ofertaRepository = ofertaRepository;
        this.userRepository = userRepository;
        this.mallaMateriaRepository = mallaMateriaRepository;
    }

    public List<OfertaResponse> findAll() {
        return ofertaRepository.findAll().stream().map(this::toResponse).toList();
    }

    public OfertaResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    public OfertaResponse create(OfertaRequest request) {
        User user = getUserOrThrow(request.userId());
        MallaMateria mallaMateria = getMallaMateriaOrThrow(request.mallaMateriaId());

        Oferta oferta = new Oferta();
        oferta.setUser(user);
        oferta.setMallaMateria(mallaMateria);
        oferta.setSemestre(request.semestre());
        oferta.setParalelo(request.paralelo());
        oferta.setHorarioJson(request.horarioJson());
        oferta.setDocente(request.docente());
        oferta.setAula(request.aula());
        oferta.setFechaCreacion(OffsetDateTime.now());
        oferta.setFechaActualizacion(OffsetDateTime.now());

        return toResponse(ofertaRepository.save(oferta));
    }

    public OfertaResponse update(Long id, OfertaRequest request) {
        Oferta oferta = getOrThrow(id);
        User user = getUserOrThrow(request.userId());
        MallaMateria mallaMateria = getMallaMateriaOrThrow(request.mallaMateriaId());

        oferta.setUser(user);
        oferta.setMallaMateria(mallaMateria);
        oferta.setSemestre(request.semestre());
        oferta.setParalelo(request.paralelo());
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

    private User getUserOrThrow(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
    }

    private MallaMateria getMallaMateriaOrThrow(Long id) {
        return mallaMateriaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("MallaMateria no encontrada con id: " + id));
    }

    private OfertaResponse toResponse(Oferta oferta) {
        return new OfertaResponse(
            oferta.getId(),
            oferta.getUser().getId(),
            oferta.getMallaMateria().getId(),
            oferta.getSemestre(),
            oferta.getParalelo(),
            oferta.getHorarioJson(),
            oferta.getDocente(),
            oferta.getAula(),
            oferta.getFechaCreacion(),
            oferta.getFechaActualizacion()
        );
    }
}
