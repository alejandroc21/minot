import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from '../../auth/services/auth.service';
import { catchError, switchMap, throwError } from 'rxjs';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const token = authService.authToken;

  if (req.url.includes('/refresh') || req.url.includes('/logout')) {
    return next(req);
  }

  const createAuthRequest = (token: string | null) =>
    token
      ? req.clone({ setHeaders: { Authorization: `Bearer ${token}` } })
      : req;

  const handleTokenRefresh = () => {
    return authService.refreshToken().pipe(
      switchMap(() => next(createAuthRequest(authService.authToken))),
      catchError((refreshError) => {
        if (refreshError.status === 401) {
          authService.logout();
        }
        return throwError(() => refreshError);
      })
    );
  };

  if (authService.authToken && !authService.isAuthenticated()) {
    return handleTokenRefresh();
  }
  const initialReq = createAuthRequest(authService.authToken);

  return next(initialReq).pipe(
    catchError((error) => {
      if (error.status !== 401) {
        return throwError(() => error);
      }

      return handleTokenRefresh();
    })
  );
};
