import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { TranslatePipe } from '@ngx-translate/core';

@Component({
  selector: 'app-malla',
  imports: [RouterLink, TranslatePipe],
  templateUrl: './malla.html',
  styleUrl: './malla.scss',
})
export class Malla {}
