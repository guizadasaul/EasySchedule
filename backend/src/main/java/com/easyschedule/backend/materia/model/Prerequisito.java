package com.easyschedule.backend.materia.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "prerequisitos", uniqueConstraints = {
    @UniqueConstraint(name = "uq_prereq", columnNames = {"id_materia", "id_prerequisito"})
})
public class Prerequisito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_materia", nullable = false)
    private Materia materia;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_prerequisito", nullable = false)
    private Materia prerequisito;

    public Prerequisito() {
    }

    public Prerequisito(Long id, Materia materia, Materia prerequisito) {
        this.id = id;
        this.materia = materia;
        this.prerequisito = prerequisito;
    }

    @PrePersist
    @PreUpdate
    public void validarDiferentes() {
        if (materia != null && prerequisito != null && materia.getId() != null && materia.getId().equals(prerequisito.getId())) {
            throw new IllegalArgumentException("Una materia no puede ser prerequisito de si misma.");
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public Materia getPrerequisito() {
        return prerequisito;
    }

    public void setPrerequisito(Materia prerequisito) {
        this.prerequisito = prerequisito;
    }
}
