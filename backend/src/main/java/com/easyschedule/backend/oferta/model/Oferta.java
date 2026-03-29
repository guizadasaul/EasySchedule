package com.easyschedule.backend.oferta.model;

import com.easyschedule.backend.auth.models.User;
import com.easyschedule.backend.malla.model.MallaMateria;
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

@Entity
@Table(name = "ofertas", uniqueConstraints = {
    @UniqueConstraint(name = "uq_ofertas", columnNames = {"user_id", "malla_materia_id", "semestre", "paralelo"})
})
public class Oferta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "malla_materia_id", nullable = false)
    private MallaMateria mallaMateria;

    @Column(nullable = false, length = 30)
    private String semestre;

    @Column(length = 20)
    private String paralelo;

    @Column(name = "horario_json", nullable = false, columnDefinition = "jsonb")
    private String horarioJson;

    @Column(length = 150)
    private String docente;

    @Column(length = 100)
    private String aula;

    @Column(name = "fecha_creacion", nullable = false)
    private OffsetDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion", nullable = false)
    private OffsetDateTime fechaActualizacion;

    public Oferta() {
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

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public String getParalelo() {
        return paralelo;
    }

    public void setParalelo(String paralelo) {
        this.paralelo = paralelo;
    }

    public String getHorarioJson() {
        return horarioJson;
    }

    public void setHorarioJson(String horarioJson) {
        this.horarioJson = horarioJson;
    }

    public String getDocente() {
        return docente;
    }

    public void setDocente(String docente) {
        this.docente = docente;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public OffsetDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(OffsetDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public OffsetDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(OffsetDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
}
