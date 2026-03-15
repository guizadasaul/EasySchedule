import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, AbstractControl, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-registro',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule
  ],
  templateUrl: './registro.html',
  styleUrls: ['./registro.scss']
})
export class Registro {

  successMessage = '';
  errorMessage = '';
  loading = false;

  form: FormGroup;

  constructor(private fb: FormBuilder, private http: HttpClient) {

    this.form = this.fb.group({
      nombre: ['', Validators.required],
      apellido: ['', Validators.required],
      correo: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8)]],
      confirmPassword: ['', Validators.required]
    }, { validators: this.passwordMatch });

  }

  passwordMatch(control: AbstractControl) {

    const pass = control.get('password')?.value;
    const confirm = control.get('confirmPassword')?.value;

    if (pass !== confirm) {
      control.get('confirmPassword')?.setErrors({ mismatch: true });
    } else {
      control.get('confirmPassword')?.setErrors(null);
    }

    return null;
  }

  registrar() {

    if (this.form.invalid) return;

    this.loading = true;
    this.successMessage = '';
    this.errorMessage = '';

    const payload = {
      username: this.form.value.nombre,
      email: this.form.value.correo,
      password: this.form.value.password
    };

    this.http.post('http://localhost:8080/api/estudiantes/registro', payload)
      .subscribe({

        next: () => {

          this.loading = false;
          this.successMessage = 'Registro exitoso. Redirigiendo...';

          setTimeout(() => {
            window.location.href = '/login';
          }, 2000);

        },

        error: (err) => {

          this.loading = false;

          if (err.status === 409) {
            this.errorMessage = 'El correo ya está registrado';
          }
          else if (err.status === 400) {
            this.errorMessage = 'Datos inválidos';
          }
          else {
            this.errorMessage = 'Error del servidor. Intenta nuevamente';
          }

        }

      });

  }

}