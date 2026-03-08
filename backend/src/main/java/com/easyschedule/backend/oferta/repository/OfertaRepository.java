package com.easyschedule.backend.oferta.repository;

import com.easyschedule.backend.oferta.model.Oferta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfertaRepository extends JpaRepository<Oferta, Long> {
}
