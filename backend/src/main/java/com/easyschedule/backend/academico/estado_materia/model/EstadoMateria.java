package com.easyschedule.backend.academico.estado_materia.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.OffsetDateTime;

import com.easyschedule.backend.auth.models.User;
import com.easyschedule.backend.academico.malla.model.MallaMateria;

@Entity
@Table(name = "estado_materia_estudiante", uniqueConstraints = {
        @UniqueConstraint(name = "uq_estado_materia_estudiante", columnNames = { "user_id", "malla_materia_id" })
})
public class EstadoMateria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "malla_materia_id", nullable = false)
    private MallaMateria mallaMateria;

    @Column(name = "estado", nullable = false, length = 20)
    private String estado;

    @Column(name = "fecha_actualizacion", nullable = false)
    private OffsetDateTime fechaActualizacion;

    public EstadoMateria() {
    }

    public EstadoMateria(Long id, User user, MallaMateria mallaMateria, String estado) {
        this.id = id;
        this.user = user;
        this.mallaMateria = mallaMateria;
        this.estado = estado;
        this.fechaActualizacion = OffsetDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MallaMateria getMallaMateria() {
        return mallaMateria;
    }

    public void setMallaMateria(MallaMateria mallaMateria) {
        this.mallaMateria = mallaMateria;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public OffsetDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(OffsetDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
}
