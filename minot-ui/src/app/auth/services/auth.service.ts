import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { environment } from '../../../environments/environment';
import { TokenResponse } from '../model/token-response';
import { RegisterRequest } from '../model/register-request';
import { LoginResquest } from '../model/login-resquest';
import { User } from '../../user/model/user';
import { catchError, tap, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly API_URL = environment.env.API_URL + '/auth';
  private _http = inject(HttpClient);
  private _accessToken: string | null = null;
  private _accessTokenExpiration: number | null = null;

  currentUser = signal<User | null>(null);

  constructor() {}

  register(request: RegisterRequest) {
    return this._http
      .post<TokenResponse>(this.API_URL + '/register', request, {
        withCredentials: true,
      })
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

  login(request: LoginResquest) {
    return this._http
      .post<TokenResponse>(this.API_URL + '/login', request, {
        withCredentials: true,
      })
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

  googleLogin(googleToken: string) {
    return this._http
      .post<TokenResponse>(
        this.API_URL + '/google-login',
        {
          token: googleToken,
        },
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

  refreshToken() {
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

  logout() {
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

  isAuthenticated(): boolean {
    if (!this._accessToken) {
      return false;
    }
    const now = Date.now();
    return this._accessTokenExpiration ? this._accessTokenExpiration > now + (60 * 1000) : true;
  }

  private setCurrentUser(token: string) {
    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      const user: User = {
        name: payload.name,
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

  get authToken() {
    return this._accessToken;
  }
}
