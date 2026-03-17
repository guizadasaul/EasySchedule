import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { TranslatePipe } from '@ngx-translate/core';

@Component({
  selector: 'app-perfil',
  imports: [RouterLink, TranslatePipe],
  templateUrl: './perfil.html',
  styleUrl: './perfil.scss',
})
export class Perfil {}
