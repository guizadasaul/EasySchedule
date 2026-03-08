package com.easyschedule.backend.toma_materia.controller;

import com.easyschedule.backend.toma_materia.dto.TomaMateriaRequest;
import com.easyschedule.backend.toma_materia.dto.TomaMateriaResponse;
import com.easyschedule.backend.toma_materia.service.TomaMateriaService;
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
@RequestMapping("/api/toma-materias")
public class TomaMateriaController {

    private final TomaMateriaService tomaMateriaService;

    public TomaMateriaController(TomaMateriaService tomaMateriaService) {
        this.tomaMateriaService = tomaMateriaService;
    }

    @GetMapping
    public List<TomaMateriaResponse> findAll() {
        return tomaMateriaService.findAll();
    }

    @GetMapping("/{id}")
    public TomaMateriaResponse findById(@PathVariable Long id) {
        return tomaMateriaService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TomaMateriaResponse create(@RequestBody TomaMateriaRequest request) {
        return tomaMateriaService.create(request);
    }

    @PutMapping("/{id}")
    public TomaMateriaResponse update(@PathVariable Long id, @RequestBody TomaMateriaRequest request) {
        return tomaMateriaService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        tomaMateriaService.delete(id);
    }
}
