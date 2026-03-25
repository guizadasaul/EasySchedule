import { Injectable } from '@angular/core';
import { firstValueFrom } from 'rxjs';
import { TranslateService } from '@ngx-translate/core';

@Injectable({
  providedIn: 'root',
})
export class LanguageService {
  private readonly supportedLanguages = ['es', 'en', 'pt'];
  private currentLanguage = 'es';

  constructor(private readonly translate: TranslateService) {}

  initializeDefaultLanguage(): Promise<void> {
    const browserLanguage = this.translate.getBrowserLang();
    const initialLanguage = browserLanguage && this.supportedLanguages.includes(browserLanguage)
      ? browserLanguage
      : 'es';

    this.translate.addLangs(this.supportedLanguages);
    this.translate.setDefaultLang('es');
    this.currentLanguage = initialLanguage;
    return firstValueFrom(this.translate.use(initialLanguage)).then(() => undefined);
  }

  setLanguage(lang: string): void {
    if (!this.supportedLanguages.includes(lang)) {
      return;
    }

    this.currentLanguage = lang;
    this.translate.use(lang);
  }

  getCurrentLanguage(): string {
    return this.currentLanguage;
  }
}
