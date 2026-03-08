import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  {
    path: 'home',
    loadChildren: () =>
      import('./features/home/home.routes').then((m) => m.HOME_ROUTES),
  },
  {
    path: 'malla',
    loadChildren: () =>
      import('./features/malla/malla.routes').then((m) => m.MALLA_ROUTES),
  },
  {
    path: 'toma-de-materias',
    loadChildren: () =>
      import('./features/toma-de-materias/toma-de-materias.routes').then(
        (m) => m.TOMA_DE_MATERIAS_ROUTES,
      ),
  },
  {
    path: 'perfil',
    loadChildren: () =>
      import('./features/perfil/perfil.routes').then((m) => m.PERFIL_ROUTES),
  },
  {
    path: 'login',
    loadChildren: () =>
      import('./features/login/login.routes').then((m) => m.LOGIN_ROUTES),
  },
  {
    path: 'registro',
    loadChildren: () =>
      import('./features/registro/registro.routes').then((m) => m.REGISTRO_ROUTES),
  },
  { path: '**', redirectTo: 'home' },
];
