package com.easyschedule.backend.academico.malla.controller;

import com.easyschedule.backend.academico.malla.dto.MallaResponse;
import com.easyschedule.backend.academico.malla.service.MallaService;
import com.easyschedule.backend.academico.malla.dto.MallaMateriaResponse;
import java.security.Principal;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/academico/mallas")
public class MallaController {

	private final MallaService mallaService;

	public MallaController(MallaService mallaService) {
		this.mallaService = mallaService;
	}

	@GetMapping
	public List<MallaResponse> findByCarrera(@RequestParam("carreraId") Long carreraId) {
		return mallaService.findActiveByCarrera(carreraId);
	}

	@GetMapping("/{mallaId}/materias")
	public List<MallaMateriaResponse> findMateriasByMalla(
			@PathVariable("mallaId") Long mallaId,
			Principal principal) {
		Long userId = getAuthenticatedUserId(principal);
		return mallaService.findMateriasByMalla(mallaId, userId);
	}

	private Long getAuthenticatedUserId(Principal principal) {
		if (principal == null || principal.getName() == null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Sesión inválida");
		}

		try {
			return Long.valueOf(principal.getName());
		} catch (NumberFormatException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Sesión inválida");
		}
	}
}
