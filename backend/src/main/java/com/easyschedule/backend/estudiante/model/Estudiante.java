package com.easyschedule.backend.estudiante.model;

import com.easyschedule.backend.horario.model.HorarioRecomendado;
import com.easyschedule.backend.malla.model.Malla;
import com.easyschedule.backend.oferta.model.Oferta;
import com.easyschedule.backend.toma_materia.model.TomaMateria;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "estudiante")
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, columnDefinition = "citext")
    private String username;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellido;

    @Column(nullable = false, unique = true, columnDefinition = "citext")
    private String correo;

    @Column(name = "password_hash", nullable = false, columnDefinition = "text")
    private String passwordHash;

    @Column(name = "carnet_identidad", nullable = false, unique = true, length = 30)
    private String carnetIdentidad;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "fecha_registro", nullable = false)
    private OffsetDateTime fechaRegistro;

    @Column(name = "semestre_actual", nullable = false)
    private Short semestreActual;

    @Column(nullable = false, length = 120)
    private String carrera;

    @ManyToOne(optional = false)
    @JoinColumn(name = "malla_id", nullable = false)
    private Malla malla;

    @OneToMany(mappedBy = "estudiante")
    private List<TomaMateria> tomasMateria = new ArrayList<>();

    @OneToMany(mappedBy = "estudiante")
    private List<Oferta> ofertas = new ArrayList<>();

    @OneToMany(mappedBy = "estudiante")
    private List<HorarioRecomendado> horariosRecomendados = new ArrayList<>();

    public Estudiante() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getCarnetIdentidad() {
        return carnetIdentidad;
    }

    public void setCarnetIdentidad(String carnetIdentidad) {
        this.carnetIdentidad = carnetIdentidad;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public OffsetDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(OffsetDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Short getSemestreActual() {
        return semestreActual;
    }

    public void setSemestreActual(Short semestreActual) {
        this.semestreActual = semestreActual;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public Malla getMalla() {
        return malla;
    }

    public void setMalla(Malla malla) {
        this.malla = malla;
    }

    public List<TomaMateria> getTomasMateria() {
        return tomasMateria;
    }

    public List<Oferta> getOfertas() {
        return ofertas;
    }

    public List<HorarioRecomendado> getHorariosRecomendados() {
        return horariosRecomendados;
    }
}
