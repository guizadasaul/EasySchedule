package com.easyschedule.backend.materia.controller;

import com.easyschedule.backend.materia.dto.MateriaRequest;
import com.easyschedule.backend.materia.dto.MateriaResponse;
import com.easyschedule.backend.materia.dto.PrerequisitoRequest;
import com.easyschedule.backend.materia.model.Prerequisito;
import com.easyschedule.backend.materia.service.MateriaService;
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
public class MateriaController {

    private final MateriaService materiaService;

    public MateriaController(MateriaService materiaService) {
        this.materiaService = materiaService;
    }

    @GetMapping("/materias")
    public List<MateriaResponse> findAllMaterias() {
        return materiaService.findAllMaterias();
    }

    @GetMapping("/materias/{id}")
    public MateriaResponse findMateriaById(@PathVariable Long id) {
        return materiaService.findMateriaById(id);
    }

    @PostMapping("/materias")
    @ResponseStatus(HttpStatus.CREATED)
    public MateriaResponse createMateria(@RequestBody MateriaRequest request) {
        return materiaService.createMateria(request);
    }

    @PutMapping("/materias/{id}")
    public MateriaResponse updateMateria(@PathVariable Long id, @RequestBody MateriaRequest request) {
        return materiaService.updateMateria(id, request);
    }

    @DeleteMapping("/materias/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMateria(@PathVariable Long id) {
        materiaService.deleteMateria(id);
    }

    @GetMapping("/prerequisitos")
    public List<Map<String, Long>> findAllPrerequisitos() {
        return materiaService.findAllPrerequisitos().stream().map(this::toMap).toList();
    }

    @PostMapping("/prerequisitos")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Long> createPrerequisito(@RequestBody PrerequisitoRequest request) {
        return toMap(materiaService.createPrerequisito(request));
    }

    @DeleteMapping("/prerequisitos/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePrerequisito(@PathVariable Long id) {
        materiaService.deletePrerequisito(id);
    }

    private Map<String, Long> toMap(Prerequisito prerequisito) {
        return Map.of(
            "id", prerequisito.getId(),
            "mallaMateriaId", prerequisito.getMallaMateria().getId(),
            "prerequisitoMallaMateriaId", prerequisito.getPrerequisito().getId()
        );
    }
}
