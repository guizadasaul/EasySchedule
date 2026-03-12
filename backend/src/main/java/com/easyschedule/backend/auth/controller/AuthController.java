package com.easyschedule.backend.auth.controller;

import com.easyschedule.backend.auth.dto.RegistroRequest;
import com.easyschedule.backend.auth.service.AuthService;
import com.easyschedule.backend.estudiante.dto.EstudianteResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<EstudianteResponse> register(@Valid @RequestBody RegistroRequest request) {
        EstudianteResponse response = authService.register(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}