import { Component, OnInit, signal } from '@angular/core';

import { ApiService } from '../../services/api.service';

@Component({
  selector: 'app-home',
  imports: [],
  templateUrl: './home.html',
  styleUrl: './home.scss',
})
export class Home implements OnInit {
  protected readonly healthStatus = signal<string>('Comprobando conexión...');

  constructor(private readonly apiService: ApiService) {}

  ngOnInit(): void {
    this.apiService.get<unknown[]>('/api/estudiantes').subscribe({
      next: () => {
        this.healthStatus.set('Conexión exitosa');
      },
      error: (error) => {
        this.healthStatus.set(
          `Error de conexión: ${error.message}`,
        );
      },
    });
  }
}
