import { Routes } from '@angular/router';
import { TomaDeMaterias } from './toma-de-materias';

export const TOMA_DE_MATERIAS_ROUTES: Routes = [
  { path: '', component: TomaDeMaterias },
  {
    path: 'materia',
    loadComponent: () =>
      import('./materia/materia').then((m) => m.Materia),
  },
];
