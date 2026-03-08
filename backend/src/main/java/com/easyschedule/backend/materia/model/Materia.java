package com.easyschedule.backend.materia.model;

import com.easyschedule.backend.malla.model.MallaMateria;
import com.easyschedule.backend.oferta.model.Oferta;
import com.easyschedule.backend.toma_materia.model.TomaMateria;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "materia")
public class Materia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30, unique = true)
    private String codigo;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(name = "semestre_sugerido", nullable = false)
    private Short semestreSugerido;

    @Column(nullable = false)
    private Short creditos;

    @OneToMany(mappedBy = "materia")
    private List<MallaMateria> mallaMaterias = new ArrayList<>();

    @OneToMany(mappedBy = "materia")
    private List<Prerequisito> prerequisitosDestino = new ArrayList<>();

    @OneToMany(mappedBy = "prerequisito")
    private List<Prerequisito> prerequisitosRequeridos = new ArrayList<>();

    @OneToMany(mappedBy = "materia")
    private List<TomaMateria> tomasMateria = new ArrayList<>();

    @OneToMany(mappedBy = "materia")
    private List<Oferta> ofertas = new ArrayList<>();

    public Materia() {
    }

    public Materia(Long id, String codigo, String nombre, Short semestreSugerido, Short creditos) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.semestreSugerido = semestreSugerido;
        this.creditos = creditos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Short getSemestreSugerido() {
        return semestreSugerido;
    }

    public void setSemestreSugerido(Short semestreSugerido) {
        this.semestreSugerido = semestreSugerido;
    }

    public Short getCreditos() {
        return creditos;
    }

    public void setCreditos(Short creditos) {
        this.creditos = creditos;
    }

    public List<MallaMateria> getMallaMaterias() {
        return mallaMaterias;
    }

    public List<Prerequisito> getPrerequisitosDestino() {
        return prerequisitosDestino;
    }

    public List<Prerequisito> getPrerequisitosRequeridos() {
        return prerequisitosRequeridos;
    }

    public List<TomaMateria> getTomasMateria() {
        return tomasMateria;
    }

    public List<Oferta> getOfertas() {
        return ofertas;
    }
}
