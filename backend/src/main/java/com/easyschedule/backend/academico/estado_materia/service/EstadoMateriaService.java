package com.easyschedule.backend.academico.estado_materia.service;

import com.easyschedule.backend.academico.estado_materia.model.EstadoMateria;
import com.easyschedule.backend.academico.estado_materia.repository.EstadoMateriaRepository;
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

    @Transactional
    public void syncEstadoFromToma(Long userId, Long mallaMateriaId, String tomaEstado) {
        String estadoDestino = mapEstadoTomaToEstadoMateria(tomaEstado);
        estadoMateriaRepository.upsertEstado(userId, mallaMateriaId, estadoDestino);
    }

    @Transactional
    public void markPendiente(Long userId, Long mallaMateriaId) {
        estadoMateriaRepository.upsertEstado(userId, mallaMateriaId, "pendiente");
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
