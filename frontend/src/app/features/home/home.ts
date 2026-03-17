import { Component, OnInit, signal } from '@angular/core';
import { TranslatePipe } from '@ngx-translate/core';

import { ApiService } from '../../services/api.service';

@Component({
  selector: 'app-home',
  imports: [TranslatePipe],
  templateUrl: './home.html',
  styleUrl: './home.scss',
})
export class Home implements OnInit {
  protected readonly healthStatusKey = signal<string>('home.status.checkingConnection');
  protected readonly healthStatusParams = signal<Record<string, unknown>>({});

  constructor(
    private readonly apiService: ApiService,
  ) {}

  ngOnInit(): void {
    this.apiService.get<unknown[]>('/api/estudiantes').subscribe({
      next: () => {
        this.healthStatusKey.set('home.status.connectionSuccess');
        this.healthStatusParams.set({});
      },
      error: (error) => {
        this.healthStatusKey.set('home.status.connectionError');
        this.healthStatusParams.set({ message: error.message });
      },
    });
  }
}
