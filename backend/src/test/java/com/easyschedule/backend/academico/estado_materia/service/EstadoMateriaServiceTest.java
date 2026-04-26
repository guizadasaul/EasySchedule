package com.easyschedule.backend.academico.estado_materia.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.easyschedule.backend.academico.estado_materia.model.EstadoMateria;
import com.easyschedule.backend.academico.estado_materia.repository.EstadoMateriaRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EstadoMateriaServiceTest {

    @Mock
    private EstadoMateriaRepository estadoMateriaRepository;

    @InjectMocks
    private EstadoMateriaService estadoMateriaService;

    @Test
    void getEstadoMateriaReturnsNullWhenNoRowExists() {
        when(estadoMateriaRepository.findByUserIdAndMallaMateria_Id(7L, 10L)).thenReturn(Optional.empty());

        String result = estadoMateriaService.getEstadoMateria(7L, 10L);

        assertNull(result);
    }

    @Test
    void getEstadoMateriaReturnsValueWhenRowExists() {
        EstadoMateria estado = new EstadoMateria();
        estado.setEstado("aprobada");
        when(estadoMateriaRepository.findByUserIdAndMallaMateria_Id(7L, 10L)).thenReturn(Optional.of(estado));

        String result = estadoMateriaService.getEstadoMateria(7L, 10L);

        assertEquals("aprobada", result);
    }

    @Test
    void syncEstadoFromTomaMapsInscritaToCursando() {
        estadoMateriaService.syncEstadoFromToma(3L, 40L, "inscrita");

        verify(estadoMateriaRepository).upsertEstado(3L, 40L, "cursando");
    }

    @Test
    void syncEstadoFromTomaMapsAprobadaToAprobada() {
        estadoMateriaService.syncEstadoFromToma(3L, 40L, "aprobada");

        verify(estadoMateriaRepository).upsertEstado(3L, 40L, "aprobada");
    }

    @Test
    void syncEstadoFromTomaMapsRetiradaToPendiente() {
        estadoMateriaService.syncEstadoFromToma(3L, 40L, "retirada");

        verify(estadoMateriaRepository).upsertEstado(3L, 40L, "pendiente");
    }

    @Test
    void markPendienteAlwaysUpsertsPendiente() {
        estadoMateriaService.markPendiente(9L, 80L);

        verify(estadoMateriaRepository).upsertEstado(9L, 80L, "pendiente");
    }
}
