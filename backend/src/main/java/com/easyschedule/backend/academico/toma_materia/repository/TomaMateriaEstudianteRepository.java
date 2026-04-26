package com.easyschedule.backend.academico.toma_materia.repository;

import com.easyschedule.backend.academico.toma_materia.model.TomaMateriaEstudiante;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TomaMateriaEstudianteRepository extends JpaRepository<TomaMateriaEstudiante, Long> {
    List<TomaMateriaEstudiante> findByUserIdOrderByFechaInscripcionDesc(Long userId);
    Optional<TomaMateriaEstudiante> findByUserIdAndOfertaId(Long userId, Long ofertaId);
    boolean existsByUserIdAndOfertaId(Long userId, Long ofertaId);
}
