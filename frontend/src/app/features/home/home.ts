import { Component, OnInit, signal } from '@angular/core';

import { ApiService } from '../../services/api.service';

interface HealthResponse {
  status: string;
  database: string;
  error?: string;
}

@Component({
  selector: 'app-home',
  imports: [],
  templateUrl: './home.html',
  styleUrl: './home.scss',
})
export class Home implements OnInit {
  protected readonly healthStatus = signal<string>('Checking backend...');

  constructor(private readonly apiService: ApiService) {}

  ngOnInit(): void {
    this.apiService.get<HealthResponse>('/health').subscribe({
      next: (response) => {
        this.healthStatus.set(
          `GET /health OK -> status: ${response.status}, database: ${response.database}`,
        );
      },
      error: (error) => {
        this.healthStatus.set(`GET /health ERROR -> ${error.message}`);
      },
    });
  }
}
