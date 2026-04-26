package com.easyschedule.backend.academico.horario.controller;

import com.easyschedule.backend.academico.horario.dto.HorarioActualResponse;
import com.easyschedule.backend.academico.horario.service.HorarioRecomendadoService;
import java.security.Principal;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/academico/horario")
public class HorarioRecomendadoController {

	private final HorarioRecomendadoService horarioRecomendadoService;

	public HorarioRecomendadoController(HorarioRecomendadoService horarioRecomendadoService) {
		this.horarioRecomendadoService = horarioRecomendadoService;
	}

	@GetMapping("/actual")
	public HorarioActualResponse getHorarioActual(Principal principal) {
		return horarioRecomendadoService.getHorarioActualByUserId(getAuthenticatedUserId(principal));
	}

	private Long getAuthenticatedUserId(Principal principal) {
		if (principal == null || principal.getName() == null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Sesion invalida");
		}

		try {
			return Long.valueOf(principal.getName());
		} catch (NumberFormatException ex) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Sesion invalida");
		}
	}
}
