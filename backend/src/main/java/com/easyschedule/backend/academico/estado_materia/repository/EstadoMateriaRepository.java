package com.easyschedule.backend.academico.estado_materia.repository;

import com.easyschedule.backend.academico.estado_materia.model.EstadoMateria;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoMateriaRepository extends JpaRepository<EstadoMateria, Long> {
    Optional<EstadoMateria> findByUserIdAndMallaMateria_Id(Long userId, Long mallaMateriaId);

    @Modifying
    @Query(
        value = """
            INSERT INTO estado_materia_estudiante (user_id, malla_materia_id, estado, fecha_actualizacion)
            VALUES (:userId, :mallaMateriaId, :estado, NOW())
            ON CONFLICT (user_id, malla_materia_id)
            DO UPDATE SET estado = EXCLUDED.estado, fecha_actualizacion = NOW()
            """,
        nativeQuery = true
    )
    void upsertEstado(
        @Param("userId") Long userId,
        @Param("mallaMateriaId") Long mallaMateriaId,
        @Param("estado") String estado
    );
}
