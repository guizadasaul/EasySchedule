package com.easyschedule.backend.auth.service;

import com.easyschedule.backend.auth.dto.RegistroRequest;
import com.easyschedule.backend.estudiante.dto.EstudianteResponse;
import com.easyschedule.backend.estudiante.model.Estudiante;
import com.easyschedule.backend.estudiante.repository.EstudianteRepository;
import com.easyschedule.backend.malla.model.Malla;
import com.easyschedule.backend.malla.repository.MallaRepository;
import com.easyschedule.backend.shared.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;

@Service
public class AuthService {

    private final EstudianteRepository estudianteRepository;
    private final MallaRepository mallaRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(EstudianteRepository estudianteRepository, MallaRepository mallaRepository, PasswordEncoder passwordEncoder) {
        this.estudianteRepository = estudianteRepository;
        this.mallaRepository = mallaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public EstudianteResponse register(RegistroRequest request) {
        if (estudianteRepository.existsByCorreo(request.correo())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El correo ya está registrado");
        }
        if (estudianteRepository.existsByUsername(request.username())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El nombre de usuario ya existe");
        }

        Malla malla = getMallaOrThrow(request.mallaId());

        Estudiante estudiante = new Estudiante();
        estudiante.setUsername(request.username());
        estudiante.setNombre(request.nombre());
        estudiante.setApellido(request.apellido());
        estudiante.setCorreo(request.correo());
        estudiante.setPasswordHash(passwordEncoder.encode(request.password())); // Encriptar con BCrypt
        estudiante.setCarnetIdentidad(request.carnetIdentidad());
        estudiante.setFechaNacimiento(request.fechaNacimiento());
        estudiante.setSemestreActual(request.semestreActual());
        estudiante.setCarrera(request.carrera());
        estudiante.setMalla(malla); 
        estudiante.setFechaRegistro(OffsetDateTime.now());

        Estudiante guardado = estudianteRepository.save(estudiante);
        return toResponse(guardado);
    }

    private Malla getMallaOrThrow(Long id) {
        return mallaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Malla no encontrada con id: " + id));
    }

    private EstudianteResponse toResponse(Estudiante estudiante) {
        return new EstudianteResponse(
                estudiante.getId(),
                estudiante.getUsername(),
                estudiante.getNombre(),
                estudiante.getApellido(),
                estudiante.getCorreo(),
                estudiante.getCarnetIdentidad(),
                estudiante.getFechaNacimiento(),
                estudiante.getFechaRegistro(),
                estudiante.getSemestreActual(),
                estudiante.getCarrera(),
                estudiante.getMalla().getId()
        );
    }
}
