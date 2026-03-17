import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { TranslatePipe } from '@ngx-translate/core';

@Component({
  selector: 'app-toma-de-materias',
  imports: [RouterLink, TranslatePipe],
  templateUrl: './toma-de-materias.html',
  styleUrl: './toma-de-materias.scss',
})
export class TomaDeMaterias {}
