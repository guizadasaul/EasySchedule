import { NgIf } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { TranslatePipe } from '@ngx-translate/core';
import { Subscription } from 'rxjs';

import { FeatureToggleService } from '../../services/feature-toggle.service';

@Component({
  selector: 'app-malla',
  imports: [RouterLink, NgIf, TranslatePipe],
  templateUrl: './malla.html',
  styleUrl: './malla.scss',
})
export class Malla implements OnInit, OnDestroy {
  protected mallaEnabled = false;
  private flagsSubscription?: Subscription;

  constructor(private readonly featureService: FeatureToggleService) {}

  ngOnInit(): void {
    this.flagsSubscription = this.featureService.flags$.subscribe((flags) => {
      this.mallaEnabled = flags.malla;
    });

    void this.featureService.loadFlags();
  }

  ngOnDestroy(): void {
    this.flagsSubscription?.unsubscribe();
  }
}
