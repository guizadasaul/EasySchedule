package com.easyschedule.backend.academico.toma_materia.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.OffsetDateTime;

@Entity
@Table(name = "toma_materia_estudiante", uniqueConstraints = {
    @UniqueConstraint(name = "uq_toma_user_oferta", columnNames = { "user_id", "oferta_id" })
})
public class TomaMateriaEstudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "oferta_id", nullable = false)
    private Long ofertaId;

    @Column(name = "estado", nullable = false, length = 20)
    private String estado = "inscrita";

    @Column(name = "fecha_inscripcion", nullable = false)
    private OffsetDateTime fechaInscripcion;

    @Column(name = "fecha_actualizacion", nullable = false)
    private OffsetDateTime fechaActualizacion;

    public TomaMateriaEstudiante() {
    }

    public TomaMateriaEstudiante(Long id, Long userId, Long ofertaId, String estado) {
        this.id = id;
        this.userId = userId;
        this.ofertaId = ofertaId;
        this.estado = estado;
        this.fechaInscripcion = OffsetDateTime.now();
        this.fechaActualizacion = OffsetDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOfertaId() {
        return ofertaId;
    }

    public void setOfertaId(Long ofertaId) {
        this.ofertaId = ofertaId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public OffsetDateTime getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(OffsetDateTime fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public OffsetDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(OffsetDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
}
