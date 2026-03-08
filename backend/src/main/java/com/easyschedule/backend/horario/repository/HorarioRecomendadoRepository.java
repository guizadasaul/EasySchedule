package com.easyschedule.backend.horario.repository;

import com.easyschedule.backend.horario.model.HorarioRecomendado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HorarioRecomendadoRepository extends JpaRepository<HorarioRecomendado, Long> {
}
