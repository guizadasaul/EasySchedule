package com.easyschedule.backend.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;

public record RegistroRequest(
    @NotBlank(message = "El nombre de usuario no puede estar vacio") String username,
    @NotBlank(message = "La contrasenia no puede estar vacia") String password,
    @NotBlank(message = "El correo no puede estar vacio") @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "El formato del correo electronico no es valido") String correo,
    @NotBlank(message = "El nombre no puede estar vacio") String nombre,
    @NotBlank(message = "El apellido no puede estar vacio") String apellido,
    @NotBlank(message = "El carnet de identidad no puede estar vacio") String carnetIdentidad,
    @NotNull(message = "La fecha de nacimiento no puede ser nula") LocalDate fechaNacimiento,
    @NotNull(message = "El semestre actual no puede ser nulo") Short semestreActual,
    @NotBlank(message = "La carrera no puede estar vacia") String carrera,
    @NotNull(message = "El ID de la malla no puede ser nulo") Long mallaId
) {}