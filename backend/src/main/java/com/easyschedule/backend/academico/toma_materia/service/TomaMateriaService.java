package com.easyschedule.backend.academico.toma_materia.service;

import com.easyschedule.backend.academico.estado_materia.service.EstadoMateriaService;
import com.easyschedule.backend.academico.oferta_materia.model.OfertaMateria;
import com.easyschedule.backend.academico.oferta_materia.repository.OfertaMateriaRepository;
import com.easyschedule.backend.academico.toma_materia.dto.TomaMateriaRequest;
import com.easyschedule.backend.academico.toma_materia.dto.TomaMateriaResponse;
import com.easyschedule.backend.academico.toma_materia.model.TomaMateriaEstudiante;
import com.easyschedule.backend.academico.toma_materia.repository.TomaMateriaEstudianteRepository;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TomaMateriaService {

    private static final Set<String> ESTADOS_VALIDOS = Set.of("inscrita", "retirada", "aprobada", "reprobada");

    private final TomaMateriaEstudianteRepository tomaMateriaEstudianteRepository;
    private final OfertaMateriaRepository ofertaMateriaRepository;
    private final EstadoMateriaService estadoMateriaService;

    public TomaMateriaService(
        TomaMateriaEstudianteRepository tomaMateriaEstudianteRepository,
        OfertaMateriaRepository ofertaMateriaRepository,
        EstadoMateriaService estadoMateriaService
    ) {
        this.tomaMateriaEstudianteRepository = tomaMateriaEstudianteRepository;
        this.ofertaMateriaRepository = ofertaMateriaRepository;
        this.estadoMateriaService = estadoMateriaService;
    }

    public List<TomaMateriaResponse> listByUserId(Long userId) {
        return tomaMateriaEstudianteRepository.findByUserIdOrderByFechaInscripcionDesc(userId)
            .stream()
            .map(this::toResponse)
            .toList();
    }

    @Transactional
    public TomaMateriaResponse saveByUserId(Long userId, TomaMateriaRequest request) {
        OfertaMateria oferta = ofertaMateriaRepository.findById(request.ofertaId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Oferta no encontrada"));

        TomaMateriaEstudiante toma = tomaMateriaEstudianteRepository
            .findByUserIdAndOfertaId(userId, request.ofertaId())
            .orElseGet(TomaMateriaEstudiante::new);

        toma.setUserId(userId);
        toma.setOfertaId(request.ofertaId());

        if (toma.getFechaInscripcion() == null) {
            toma.setFechaInscripcion(OffsetDateTime.now());
        }

        String estado = normalizeEstado(request.estado());
        toma.setEstado(estado);
        toma.setFechaActualizacion(OffsetDateTime.now());

        TomaMateriaEstudiante saved = tomaMateriaEstudianteRepository.save(toma);
        estadoMateriaService.syncEstadoFromToma(userId, oferta.getMallaMateriaId(), saved.getEstado());
        return toResponse(saved);
    }

    @Transactional
    public void deleteByUserIdAndOfertaId(Long userId, Long ofertaId) {
        tomaMateriaEstudianteRepository.findByUserIdAndOfertaId(userId, ofertaId)
            .ifPresent(toma -> {
                tomaMateriaEstudianteRepository.delete(toma);

                String estadoToma = toma.getEstado() == null ? "" : toma.getEstado().trim().toLowerCase(Locale.ROOT);
                if ("inscrita".equals(estadoToma) || "retirada".equals(estadoToma)) {
                    ofertaMateriaRepository.findById(ofertaId)
                        .ifPresent(oferta -> estadoMateriaService.markPendiente(userId, oferta.getMallaMateriaId()));
                }
            });
    }

    private String normalizeEstado(String estado) {
        if (estado == null || estado.isBlank()) {
            return "inscrita";
        }

        String normalized = estado.trim().toLowerCase(Locale.ROOT);
        if (!ESTADOS_VALIDOS.contains(normalized)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estado de toma no valido");
        }
        return normalized;
    }

    private TomaMateriaResponse toResponse(TomaMateriaEstudiante toma) {
        return new TomaMateriaResponse(
            toma.getId(),
            toma.getUserId(),
            toma.getOfertaId(),
            toma.getEstado(),
            toma.getFechaInscripcion(),
            toma.getFechaActualizacion()
        );
    }
}
