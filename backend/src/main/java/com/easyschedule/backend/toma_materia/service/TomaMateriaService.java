package com.easyschedule.backend.toma_materia.service;

import com.easyschedule.backend.auth.models.User;
import com.easyschedule.backend.auth.repositories.UserRepository;
import com.easyschedule.backend.malla.model.MallaMateria;
import com.easyschedule.backend.malla.repository.MallaMateriaRepository;
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
    private final UserRepository userRepository;
    private final MallaMateriaRepository mallaMateriaRepository;

    public TomaMateriaService(
        TomaMateriaRepository tomaMateriaRepository,
        UserRepository userRepository,
        MallaMateriaRepository mallaMateriaRepository
    ) {
        this.tomaMateriaRepository = tomaMateriaRepository;
        this.userRepository = userRepository;
        this.mallaMateriaRepository = mallaMateriaRepository;
    }

    public List<TomaMateriaResponse> findAll() {
        return tomaMateriaRepository.findAll().stream().map(this::toResponse).toList();
    }

    public TomaMateriaResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    public TomaMateriaResponse create(TomaMateriaRequest request) {
        validarEstado(request.estado());
        User user = getUserOrThrow(request.userId());
        MallaMateria mallaMateria = getMallaMateriaOrThrow(request.mallaMateriaId());

        TomaMateria tomaMateria = new TomaMateria();
        tomaMateria.setUser(user);
        tomaMateria.setMallaMateria(mallaMateria);
        tomaMateria.setEstado(request.estado());
        tomaMateria.setFechaActualizacion(OffsetDateTime.now());

        return toResponse(tomaMateriaRepository.save(tomaMateria));
    }

    public TomaMateriaResponse update(Long id, TomaMateriaRequest request) {
        validarEstado(request.estado());
        TomaMateria tomaMateria = getOrThrow(id);
        User user = getUserOrThrow(request.userId());
        MallaMateria mallaMateria = getMallaMateriaOrThrow(request.mallaMateriaId());

        tomaMateria.setUser(user);
        tomaMateria.setMallaMateria(mallaMateria);
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

    private User getUserOrThrow(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
    }

    private MallaMateria getMallaMateriaOrThrow(Long id) {
        return mallaMateriaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("MallaMateria no encontrada con id: " + id));
    }

    private TomaMateriaResponse toResponse(TomaMateria tomaMateria) {
        return new TomaMateriaResponse(
            tomaMateria.getId(),
            tomaMateria.getUser().getId(),
            tomaMateria.getMallaMateria().getId(),
            tomaMateria.getEstado(),
            tomaMateria.getFechaActualizacion()
        );
    }
}
