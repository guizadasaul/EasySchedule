package com.easyschedule.backend.estudiante.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.easyschedule.backend.auth.dto.RegistroRequest;
import com.easyschedule.backend.auth.models.User;
import com.easyschedule.backend.auth.repositories.UserRepository;
import com.easyschedule.backend.auth.service.AuthService;
import com.easyschedule.backend.estudiante.dto.EstudianteUpdateRequest;
import com.easyschedule.backend.estudiante.model.Estudiante;
import com.easyschedule.backend.estudiante.repository.EstudianteRepository;
import com.easyschedule.backend.malla.model.Malla;
import com.easyschedule.backend.malla.repository.MallaRepository;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EstudianteServiceTest {

    @Mock
    private EstudianteRepository estudianteRepository;

    @Mock
    private MallaRepository mallaRepository;

    @Mock
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private EstudianteService estudianteService;

    @Test
    void registerCreatesProfileWithIncompleteFlag() {
        RegistroRequest request = new RegistroRequest("nuevo_usuario", "clave123", "nuevo@mail.com");
        User createdUser = new User("nuevo_usuario", "nuevo@mail.com", "hashed");
        createdUser.setId(10L);

        when(userRepository.findByUsername("nuevo_usuario")).thenReturn(Optional.of(createdUser));
        when(estudianteRepository.save(any(Estudiante.class))).thenAnswer(invocation -> {
            Estudiante saved = invocation.getArgument(0);
            saved.setId(saved.getUser().getId());
            return saved;
        });

        var response = estudianteService.register(request);

        verify(authService).registerUser(any());
        assertEquals(10L, response.id());
        assertFalse(response.profileCompleted());
        assertEquals("nuevo_usuario", response.username());
    }

    @Test
    void updateMarksProfileAsCompletedWhenRequiredFieldsExist() {
        User user = new User("usuario", "usuario@mail.com", "hashed");
        user.setId(20L);

        Malla malla = new Malla();
        malla.setId(3L);

        Estudiante profile = new Estudiante();
        profile.setId(20L);
        profile.setUser(user);
        profile.setFechaRegistro(OffsetDateTime.now());

        EstudianteUpdateRequest request = new EstudianteUpdateRequest(
            "Juan",
            "Perez",
            "1234567",
            LocalDate.of(2000, 1, 1),
            (short) 3,
            1L,
            2L,
            3L
        );

        when(estudianteRepository.findById(20L)).thenReturn(Optional.of(profile));
        when(mallaRepository.findById(3L)).thenReturn(Optional.of(malla));
        when(estudianteRepository.save(any(Estudiante.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var response = estudianteService.update(20L, request);

        ArgumentCaptor<Estudiante> captor = ArgumentCaptor.forClass(Estudiante.class);
        verify(estudianteRepository).save(captor.capture());
        assertTrue(captor.getValue().isProfileCompleted());
        assertTrue(response.profileCompleted());
        assertEquals(1L, response.universidadId());
        assertEquals(2L, response.carreraId());
    }
}
