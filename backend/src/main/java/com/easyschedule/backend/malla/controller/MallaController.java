package com.easyschedule.backend.malla.controller;

import com.easyschedule.backend.malla.dto.MallaMateriaRequest;
import com.easyschedule.backend.malla.dto.MallaRequest;
import com.easyschedule.backend.malla.dto.MallaResponse;
import com.easyschedule.backend.malla.model.MallaMateria;
import com.easyschedule.backend.malla.service.MallaService;
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
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MallaController {

    private final MallaService mallaService;

    public MallaController(MallaService mallaService) {
        this.mallaService = mallaService;
    }

    @GetMapping("/mallas")
    public List<MallaResponse> findAllMallas() {
        return mallaService.findAllMallas();
    }

    @GetMapping("/mallas/{id}")
    public MallaResponse findMallaById(@PathVariable Long id) {
        return mallaService.findMallaById(id);
    }

    @PostMapping("/mallas")
    @ResponseStatus(HttpStatus.CREATED)
    public MallaResponse createMalla(@RequestBody MallaRequest request) {
        return mallaService.createMalla(request);
    }

    @PutMapping("/mallas/{id}")
    public MallaResponse updateMalla(@PathVariable Long id, @RequestBody MallaRequest request) {
        return mallaService.updateMalla(id, request);
    }

    @DeleteMapping("/mallas/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMalla(@PathVariable Long id) {
        mallaService.deleteMalla(id);
    }

    @GetMapping("/malla-materias")
    public List<Map<String, Long>> findAllMallaMaterias() {
        return mallaService.findAllMallaMaterias().stream().map(this::toMap).toList();
    }

    @PostMapping("/malla-materias")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Long> createMallaMateria(@RequestBody MallaMateriaRequest request) {
        return toMap(mallaService.createMallaMateria(request));
    }

    @DeleteMapping("/malla-materias/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMallaMateria(@PathVariable Long id) {
        mallaService.deleteMallaMateria(id);
    }

    private Map<String, Long> toMap(MallaMateria mallaMateria) {
        return Map.ofEntries(
            Map.entry("id", mallaMateria.getId()),
            Map.entry("mallaId", mallaMateria.getMalla().getId()),
            Map.entry("materiaId", mallaMateria.getMateria().getId()),
            Map.entry("semestreSugerido", Long.valueOf(mallaMateria.getSemestreSugerido()))
        );
    }
}
