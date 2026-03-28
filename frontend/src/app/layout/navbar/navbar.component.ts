import { Component, OnDestroy, OnInit } from '@angular/core';
import { NgIf } from '@angular/common';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { TranslatePipe } from '@ngx-translate/core';
import { Subscription } from 'rxjs';

import { LanguageService } from '../../core/services/language.service';
import { FeatureToggleService } from '../../services/feature-toggle.service';

@Component({
  selector: 'app-navbar',
  imports: [RouterLink, RouterLinkActive, NgIf, TranslatePipe],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss',
})
export class NavbarComponent implements OnInit, OnDestroy {
  protected mallaEnabled = false;
  protected tomaMateriasEnabled = false;
  private flagsSubscription?: Subscription;

  constructor(
    private readonly languageService: LanguageService,
    private readonly featureToggleService: FeatureToggleService,
  ) {}

  ngOnInit(): void {
    this.flagsSubscription = this.featureToggleService.flags$.subscribe((flags) => {
      this.mallaEnabled = flags.malla;
      this.tomaMateriasEnabled = flags.tomaMaterias;
    });

    void this.featureToggleService.loadFlags();
  }

  ngOnDestroy(): void {
    this.flagsSubscription?.unsubscribe();
  }

  protected setLanguage(lang: string): void {
    this.languageService.setLanguage(lang);
  }
  protected isLoggedIn(): boolean {
    return !!localStorage.getItem('token');
  }
  
  protected logout(): void {
    localStorage.removeItem('token');
    window.location.href = '/home'; 
  }
}
