package com.easyschedule.backend.academico.toma_materia.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.easyschedule.backend.academico.estado_materia.service.EstadoMateriaService;
import com.easyschedule.backend.academico.oferta_materia.model.OfertaMateria;
import com.easyschedule.backend.academico.oferta_materia.repository.OfertaMateriaRepository;
import com.easyschedule.backend.academico.toma_materia.dto.TomaMateriaRequest;
import com.easyschedule.backend.academico.toma_materia.dto.TomaMateriaResponse;
import com.easyschedule.backend.academico.toma_materia.model.TomaMateriaEstudiante;
import com.easyschedule.backend.academico.toma_materia.repository.TomaMateriaEstudianteRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
class TomaMateriaServiceTest {

    @Mock
    private TomaMateriaEstudianteRepository tomaMateriaEstudianteRepository;

    @Mock
    private OfertaMateriaRepository ofertaMateriaRepository;

    @Mock
    private EstadoMateriaService estadoMateriaService;

    @InjectMocks
    private TomaMateriaService tomaMateriaService;

    @Test
    void saveByUserIdThrowsWhenOfertaNotFound() {
        when(ofertaMateriaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> tomaMateriaService.saveByUserId(5L, new TomaMateriaRequest(99L, "inscrita")));
    }

    @Test
    void saveByUserIdDefaultsEstadoToInscritaAndSyncsEstadoMateria() {
        OfertaMateria oferta = new OfertaMateria();
        oferta.setId(77L);
        oferta.setMallaMateriaId(900L);
        when(ofertaMateriaRepository.findById(77L)).thenReturn(Optional.of(oferta));
        when(tomaMateriaEstudianteRepository.findByUserIdAndOfertaId(5L, 77L)).thenReturn(Optional.empty());

        TomaMateriaEstudiante saved = new TomaMateriaEstudiante();
        saved.setId(1L);
        saved.setUserId(5L);
        saved.setOfertaId(77L);
        saved.setEstado("inscrita");
        when(tomaMateriaEstudianteRepository.save(org.mockito.ArgumentMatchers.any(TomaMateriaEstudiante.class))).thenReturn(saved);

        TomaMateriaResponse response = tomaMateriaService.saveByUserId(5L, new TomaMateriaRequest(77L, null));

        assertEquals("inscrita", response.estado());
        verify(estadoMateriaService).syncEstadoFromToma(5L, 900L, "inscrita");

        ArgumentCaptor<TomaMateriaEstudiante> captor = ArgumentCaptor.forClass(TomaMateriaEstudiante.class);
        verify(tomaMateriaEstudianteRepository).save(captor.capture());
        assertEquals("inscrita", captor.getValue().getEstado());
    }

    @Test
    void saveByUserIdRejectsInvalidEstado() {
        OfertaMateria oferta = new OfertaMateria();
        oferta.setId(77L);
        oferta.setMallaMateriaId(900L);
        when(ofertaMateriaRepository.findById(77L)).thenReturn(Optional.of(oferta));
        when(tomaMateriaEstudianteRepository.findByUserIdAndOfertaId(5L, 77L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> tomaMateriaService.saveByUserId(5L, new TomaMateriaRequest(77L, "estado-raro")));
        verify(tomaMateriaEstudianteRepository, never()).save(org.mockito.ArgumentMatchers.any());
    }

    @Test
    void deleteByUserIdAndOfertaIdMarksPendienteWhenWasInscrita() {
        TomaMateriaEstudiante toma = new TomaMateriaEstudiante();
        toma.setUserId(3L);
        toma.setOfertaId(77L);
        toma.setEstado("inscrita");

        OfertaMateria oferta = new OfertaMateria();
        oferta.setId(77L);
        oferta.setMallaMateriaId(501L);

        when(tomaMateriaEstudianteRepository.findByUserIdAndOfertaId(3L, 77L)).thenReturn(Optional.of(toma));
        when(ofertaMateriaRepository.findById(77L)).thenReturn(Optional.of(oferta));

        tomaMateriaService.deleteByUserIdAndOfertaId(3L, 77L);

        verify(tomaMateriaEstudianteRepository).delete(toma);
        verify(estadoMateriaService).markPendiente(3L, 501L);
    }

    @Test
    void deleteByUserIdAndOfertaIdDoesNotMarkPendienteWhenWasAprobada() {
        TomaMateriaEstudiante toma = new TomaMateriaEstudiante();
        toma.setUserId(3L);
        toma.setOfertaId(77L);
        toma.setEstado("aprobada");

        when(tomaMateriaEstudianteRepository.findByUserIdAndOfertaId(3L, 77L)).thenReturn(Optional.of(toma));

        tomaMateriaService.deleteByUserIdAndOfertaId(3L, 77L);

        verify(tomaMateriaEstudianteRepository).delete(toma);
        verify(estadoMateriaService, never()).markPendiente(org.mockito.ArgumentMatchers.anyLong(), org.mockito.ArgumentMatchers.anyLong());
    }
}
