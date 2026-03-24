package com.easyschedule.backend.horario.service;

import com.easyschedule.backend.auth.models.User;
import com.easyschedule.backend.auth.repositories.UserRepository;
import com.easyschedule.backend.horario.dto.HorarioRecomendadoRequest;
import com.easyschedule.backend.horario.dto.HorarioRecomendadoResponse;
import com.easyschedule.backend.horario.model.HorarioRecomendado;
import com.easyschedule.backend.horario.repository.HorarioRecomendadoRepository;
import com.easyschedule.backend.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class HorarioRecomendadoService {

    private final HorarioRecomendadoRepository horarioRecomendadoRepository;
    private final UserRepository userRepository;

    public HorarioRecomendadoService(
        HorarioRecomendadoRepository horarioRecomendadoRepository,
        UserRepository userRepository
    ) {
        this.horarioRecomendadoRepository = horarioRecomendadoRepository;
        this.userRepository = userRepository;
    }

    public List<HorarioRecomendadoResponse> findAll() {
        return horarioRecomendadoRepository.findAll().stream().map(this::toResponse).toList();
    }

    public HorarioRecomendadoResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    public HorarioRecomendadoResponse create(HorarioRecomendadoRequest request) {
        User user = getUserOrThrow(request.userId());

        HorarioRecomendado horario = new HorarioRecomendado();
        horario.setUser(user);
        horario.setSemestre(request.semestre());
        horario.setJsonResultado(request.jsonResultado());
        horario.setFechaGeneracion(OffsetDateTime.now());

        return toResponse(horarioRecomendadoRepository.save(horario));
    }

    public HorarioRecomendadoResponse update(Long id, HorarioRecomendadoRequest request) {
        HorarioRecomendado horario = getOrThrow(id);
        User user = getUserOrThrow(request.userId());

        horario.setUser(user);
        horario.setSemestre(request.semestre());
        horario.setJsonResultado(request.jsonResultado());

        return toResponse(horarioRecomendadoRepository.save(horario));
    }

    public void delete(Long id) {
        HorarioRecomendado horario = getOrThrow(id);
        horarioRecomendadoRepository.delete(horario);
    }

    private HorarioRecomendado getOrThrow(Long id) {
        return horarioRecomendadoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("HorarioRecomendado no encontrado con id: " + id));
    }

    private User getUserOrThrow(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
    }

    private HorarioRecomendadoResponse toResponse(HorarioRecomendado horario) {
        return new HorarioRecomendadoResponse(
            horario.getId(),
            horario.getUser().getId(),
            horario.getSemestre(),
            horario.getJsonResultado(),
            horario.getFechaGeneracion()
        );
    }
}
