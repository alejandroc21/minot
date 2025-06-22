import { AfterViewInit, Component, ElementRef, inject, NgZone, ViewChild } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { environment } from '../../../../environments/environment.development';
import { Router } from '@angular/router';

declare global {
  interface Window { google: any; }
}

@Component({
  selector: 'app-google-login',
  standalone: true,
  imports: [],
  templateUrl: './google-login.component.html',
  styleUrl: './google-login.component.css',
})
export class GoogleLoginComponent implements AfterViewInit{
  private readonly clientId = environment.env.NG_APP_GOOGLE_CLIENT_ID;
  private _authService = inject(AuthService);
  private _router = inject(Router);
  private _ngZone = inject(NgZone);
  @ViewChild('googleBtn', { static: true }) googleBtn!: ElementRef<HTMLDivElement>;

  ngAfterViewInit(): void {
      window.google.accounts.id.initialize({
      client_id: this.clientId,
      callback: (res: any) => {
        this._ngZone.run(() => this.handleLogin(res.credential));
      }
    });

    let w = window.innerWidth;
    const width = w < 480 ? '290' : w < 768 ? '320' : '370';

    window.google.accounts.id.renderButton(
      this.googleBtn.nativeElement,
      {
        size: 'large',
        shape: 'square',
        width,
      }
    );
  }

    handleLogin(googleToken: string) {
    this._authService.googleLogin(googleToken).subscribe({
      next: (res) => {
        this._router.navigate(['/home']);
      },
      error: (err) => {
        console.error(err);
      },
    });
  }
}
