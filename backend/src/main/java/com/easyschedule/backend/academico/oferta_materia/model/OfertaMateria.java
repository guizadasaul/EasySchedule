package com.easyschedule.backend.academico.oferta_materia.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ofertas")
public class OfertaMateria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "malla_materia_id", nullable = false)
	private Long mallaMateriaId;

	public OfertaMateria() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMallaMateriaId() {
		return mallaMateriaId;
	}

	public void setMallaMateriaId(Long mallaMateriaId) {
		this.mallaMateriaId = mallaMateriaId;
	}
}
