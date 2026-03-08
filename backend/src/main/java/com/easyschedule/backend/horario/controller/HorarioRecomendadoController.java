package com.easyschedule.backend.horario.controller;

import com.easyschedule.backend.horario.dto.HorarioRecomendadoRequest;
import com.easyschedule.backend.horario.dto.HorarioRecomendadoResponse;
import com.easyschedule.backend.horario.service.HorarioRecomendadoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/horarios-recomendados")
public class HorarioRecomendadoController {

    private final HorarioRecomendadoService horarioRecomendadoService;

    public HorarioRecomendadoController(HorarioRecomendadoService horarioRecomendadoService) {
        this.horarioRecomendadoService = horarioRecomendadoService;
    }

    @GetMapping
    public List<HorarioRecomendadoResponse> findAll() {
        return horarioRecomendadoService.findAll();
    }

    @GetMapping("/{id}")
    public HorarioRecomendadoResponse findById(@PathVariable Long id) {
        return horarioRecomendadoService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HorarioRecomendadoResponse create(@RequestBody HorarioRecomendadoRequest request) {
        return horarioRecomendadoService.create(request);
    }

    @PutMapping("/{id}")
    public HorarioRecomendadoResponse update(@PathVariable Long id, @RequestBody HorarioRecomendadoRequest request) {
        return horarioRecomendadoService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        horarioRecomendadoService.delete(id);
    }
}
