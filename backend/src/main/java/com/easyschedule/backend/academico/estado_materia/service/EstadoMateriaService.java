package com.easyschedule.backend.academico.estado_materia.service;

import com.easyschedule.backend.academico.estado_materia.dto.EstadoMateriaRequest;
import com.easyschedule.backend.academico.estado_materia.dto.EstadoMateriaResponse;
import com.easyschedule.backend.academico.estado_materia.model.EstadoMateria;
import com.easyschedule.backend.academico.estado_materia.repository.EstadoMateriaRepository;
import java.util.List;
import java.util.Locale;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EstadoMateriaService {

    private final EstadoMateriaRepository estadoMateriaRepository;

    public EstadoMateriaService(EstadoMateriaRepository estadoMateriaRepository) {
        this.estadoMateriaRepository = estadoMateriaRepository;
    }

    public String getEstadoMateria(Long userId, Long mallaMateriaId) {
        return estadoMateriaRepository
                .findByUserIdAndMallaMateria_Id(userId, mallaMateriaId)
                .map(EstadoMateria::getEstado)
                .orElse(null);
    }

    public List<EstadoMateriaResponse> getEstadosByUserId(Long userId) {
        return estadoMateriaRepository.findByUser_Id(userId)
            .stream()
            .map(this::mapToResponse)
            .toList();
    }

    public List<EstadoMateriaResponse> getEstadosByMalla(Long userId, Long mallaId) {
        return estadoMateriaRepository.findByUserIdAndMallaId(userId, mallaId)
            .stream()
            .map(this::mapToResponse)
            .toList();
    }

    @Transactional
    public EstadoMateriaResponse saveEstado(Long userId, EstadoMateriaRequest request) {
        estadoMateriaRepository.upsertEstado(
            userId,
            request.mallaMateriaId(),
            request.estado()
        );
        EstadoMateria estado = estadoMateriaRepository
            .findByUserIdAndMallaMateria_Id(userId, request.mallaMateriaId())
            .orElseThrow(() -> new RuntimeException("Estado no guardado"));
        return mapToResponse(estado);
    }

    @Transactional
    public List<EstadoMateriaResponse> saveEstados(Long userId, List<EstadoMateriaRequest> requests) {
        return requests.stream()
            .map(request -> saveEstado(userId, request))
            .toList();
    }

    @Transactional
    public void syncEstadoFromToma(Long userId, Long mallaMateriaId, String tomaEstado) {
        String estadoDestino = mapEstadoTomaToEstadoMateria(tomaEstado);
        estadoMateriaRepository.upsertEstado(userId, mallaMateriaId, estadoDestino);
    }

    @Transactional
    public void markPendiente(Long userId, Long mallaMateriaId) {
        estadoMateriaRepository.upsertEstado(userId, mallaMateriaId, "pendiente");
    }

    private EstadoMateriaResponse mapToResponse(EstadoMateria entity) {
        return new EstadoMateriaResponse(
            entity.getId(),
            entity.getUser().getId(),
            entity.getMallaMateria().getId(),
            entity.getEstado(),
            entity.getFechaActualizacion()
        );
    }

    private String mapEstadoTomaToEstadoMateria(String tomaEstado) {
        if (tomaEstado == null) {
            return "cursando";
        }

        String normalized = tomaEstado.trim().toLowerCase(Locale.ROOT);
        return switch (normalized) {
            case "aprobada" -> "aprobada";
            case "inscrita" -> "cursando";
            case "retirada", "reprobada" -> "pendiente";
            default -> "cursando";
        };
    }
}
