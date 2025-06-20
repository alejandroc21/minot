import { Component, inject, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { environment } from '../../../../environments/environment.development';

declare var google: any;

@Component({
  selector: 'app-google-login',
  standalone: true,
  imports: [],
  templateUrl: './google-login.component.html',
  styleUrl: './google-login.component.css',
})
export class GoogleLoginComponent implements OnInit {
  private _authService = inject(AuthService);

  private clientId = environment.env.NG_APP_GOOGLE_CLIENT_ID;

  ngOnInit(): void {
    google.accounts.id.initialize({
      client_id: this.clientId,
      callback: (res: any) => {
        console.log(res);
        this.handleLogin();
      },
    });

    let buttonWidth = '370';

    if (window.innerWidth < 480) {
      buttonWidth = '290';
    } else if (window.innerWidth < 768) {
      buttonWidth = '320';
    }

    google.accounts.id.renderButton(document.getElementById('google-btn'), {
      size: 'large',
      shape: 'square',
      width: buttonWidth,
      locale: 'en',
    });
  }

  handleLogin() {
    this._authService.googleLogin();
  }
}
