import { Component } from '@angular/core';
import { TranslatePipe } from '@ngx-translate/core';

@Component({
  selector: 'app-login',
  imports: [TranslatePipe],
  templateUrl: './login.html',
  styleUrl: './login.scss',
})
export class Login {

}
