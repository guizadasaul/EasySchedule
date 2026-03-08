import { Routes } from '@angular/router';
import { Perfil } from './perfil';

export const PERFIL_ROUTES: Routes = [
  { path: '', component: Perfil },
  {
    path: 'editar',
    loadComponent: () =>
      import('./editar-perfil/editar-perfil').then((m) => m.EditarPerfil),
  },
];
