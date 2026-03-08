package com.easyschedule.backend.oferta.controller;

import com.easyschedule.backend.oferta.dto.OfertaRequest;
import com.easyschedule.backend.oferta.dto.OfertaResponse;
import com.easyschedule.backend.oferta.service.OfertaService;
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
@RequestMapping("/api/ofertas")
public class OfertaController {

    private final OfertaService ofertaService;

    public OfertaController(OfertaService ofertaService) {
        this.ofertaService = ofertaService;
    }

    @GetMapping
    public List<OfertaResponse> findAll() {
        return ofertaService.findAll();
    }

    @GetMapping("/{id}")
    public OfertaResponse findById(@PathVariable Long id) {
        return ofertaService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OfertaResponse create(@RequestBody OfertaRequest request) {
        return ofertaService.create(request);
    }

    @PutMapping("/{id}")
    public OfertaResponse update(@PathVariable Long id, @RequestBody OfertaRequest request) {
        return ofertaService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        ofertaService.delete(id);
    }
}
