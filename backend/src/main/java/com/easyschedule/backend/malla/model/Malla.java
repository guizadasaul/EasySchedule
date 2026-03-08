package com.easyschedule.backend.malla.model;

import com.easyschedule.backend.estudiante.model.Estudiante;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "malla", uniqueConstraints = {
    @UniqueConstraint(name = "uq_malla", columnNames = {"carrera", "universidad", "version"})
})
public class Malla {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String carrera;

    @Column(nullable = false, length = 150)
    private String universidad;

    @Column(nullable = false, length = 30)
    private String version;

    @OneToMany(mappedBy = "malla")
    private List<Estudiante> estudiantes = new ArrayList<>();

    @OneToMany(mappedBy = "malla")
    private List<MallaMateria> mallaMaterias = new ArrayList<>();

    public Malla() {
    }

    public Malla(Long id, String carrera, String universidad, String version) {
        this.id = id;
        this.carrera = carrera;
        this.universidad = universidad;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getUniversidad() {
        return universidad;
    }

    public void setUniversidad(String universidad) {
        this.universidad = universidad;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(List<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }

    public List<MallaMateria> getMallaMaterias() {
        return mallaMaterias;
    }

    public void setMallaMaterias(List<MallaMateria> mallaMaterias) {
        this.mallaMaterias = mallaMaterias;
    }
}
