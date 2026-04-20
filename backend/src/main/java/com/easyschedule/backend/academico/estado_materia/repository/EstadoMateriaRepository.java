package com.easyschedule.backend.academico.estado_materia.repository;

import com.easyschedule.backend.academico.estado_materia.model.EstadoMateria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstadoMateriaRepository extends JpaRepository<EstadoMateria, Long> {
    List<EstadoMateria> findByUserId(Long userId);
    Optional<EstadoMateria> findByUserIdAndMallaMateriaId(Long userId, Long mallaMateriaId);
}
