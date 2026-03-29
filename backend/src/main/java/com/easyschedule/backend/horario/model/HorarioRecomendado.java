package com.easyschedule.backend.horario.model;

import com.easyschedule.backend.auth.models.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.OffsetDateTime;

@Entity
@Table(name = "horarios_recomendados")
public class HorarioRecomendado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 30)
    private String semestre;

    @Column(name = "json_resultado", nullable = false, columnDefinition = "jsonb")
    private String jsonResultado;

    @Column(name = "fecha_generacion", nullable = false)
    private OffsetDateTime fechaGeneracion;

    public HorarioRecomendado() {
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

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public String getJsonResultado() {
        return jsonResultado;
    }

    public void setJsonResultado(String jsonResultado) {
        this.jsonResultado = jsonResultado;
    }

    public OffsetDateTime getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(OffsetDateTime fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }
}
