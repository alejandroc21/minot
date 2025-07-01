import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { environment } from '../../../environments/environment';
import { User } from '../model/user';
import { TokenResponse } from '../model/token-response';
import { catchError, tap, throwError } from 'rxjs';
import { ToastrService } from 'ngx-toastr';
import { AuthRequest } from '../model/auth-request';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly API_URL = `${environment.env.API_URL}/auth`;
  private readonly _http = inject(HttpClient);
  private _accessTokenExpiration: number | null = null;
  private _accessToken: string | null = null;
  private _toastService = inject(ToastrService);
  public currentUser = signal<User | null>(null);

  public register(request: AuthRequest) {
    return this._http
      .post<TokenResponse>(`${this.API_URL}/register`, request, {
        withCredentials: true,
      })
      .pipe(
        tap((res) => {
          this._accessToken = res.token;
          this.setCurrentUser(res.token);
        }),
        catchError(this.handleError)
      );
  }

  public login(request: AuthRequest) {
    return this._http
      .post<TokenResponse>(`${this.API_URL}/login`, request, {
        withCredentials: true,
      })
      .pipe(
        tap((res) => {
          this._accessToken = res.token;
          this.setCurrentUser(res.token);
        }),
        catchError(this.handleError)
      );
  }

  public googleLogin(googleToken: string) {
    return this._http
      .post<TokenResponse>(
        `${this.API_URL}/google-login`,
        { token: googleToken },
        { withCredentials: true }
      )
      .pipe(
        tap((res) => {
          this._accessToken = res.token;
          this.setCurrentUser(res.token);
        }),
        catchError(this.handleError)
      );
  }

  public refreshToken() {
    return this._http
      .post<TokenResponse>(
        this.API_URL + '/refresh',
        {},
        { withCredentials: true }
      )
      .pipe(
        tap((res) => {
          this._accessToken = res.token;
          this.setCurrentUser(res.token);
        }),
        catchError((error) => {
          this.clearAuthData();
          return throwError(() => error);
        })
      );
  }

  public logout() {
    return this._http
      .post<boolean>(this.API_URL + '/logout', {}, { withCredentials: true })
      .pipe(
        tap(() => {
          this.clearAuthData();
          console.log('Logout successful (server-side)');
        }),
        catchError((err) => {
          console.error('Logout error on server:', err);
          this.clearAuthData();
          return throwError(() => err);
        })
      );
  }

  public isAuthenticated(): boolean {
    if (!this._accessToken) {
      return false;
    }
    const now = Date.now();
    return this._accessTokenExpiration
      ? this._accessTokenExpiration > now + 60 * 1000
      : false;
  }

  private setCurrentUser(token: string) {
    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      const user: User = {
        email: payload.sub,
      };
      this.currentUser.set(user);
      this._accessTokenExpiration = payload.exp * 1000;
    } catch (e) {
      console.error(e);
      this.clearAuthData();
    }
  }

  private clearAuthData() {
    this.currentUser.set(null);
    this._accessTokenExpiration = null;
    this._accessToken = null;
  }

  private handleError = (error: HttpErrorResponse) => {
    let message = '';

    if (error.status === 0) {
      message = 'Algo salió mal';
    } else {
      switch (error.error.message) {
        case 'EMAIL_IN_USE':
          message = 'Ingresa un email diferente';
          break;
        case 'USER_NOT_FOUND':
          message = 'El usuario no existe';
          break;
      }
    }
    this._toastService.error(message, 'Error');
    return throwError(() => error);
  };

  get authToken() {
    return this._accessToken;
  }
}
