package com.easyschedule.backend.estudiante.repository;

import com.easyschedule.backend.estudiante.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
}
