import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

import { FeatureName, FeatureToggleService } from './services/feature-toggle.service';

export const featureGuard = (featureName: FeatureName): CanActivateFn => {
  return () => {
    const featureToggleService = inject(FeatureToggleService);
    const router = inject(Router);

    return featureToggleService.loadFlags().then(() => {
      if (featureToggleService.isEnabled(featureName)) {
        return true;
      }

      return router.createUrlTree(['/home']);
    });
  };
};
