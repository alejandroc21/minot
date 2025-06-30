import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from '../../auth/services/auth.service';
import { catchError, throwError, switchMap } from 'rxjs';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);

  const token = authService.authToken;

  const authReq = req.clone({
    setHeaders:{
      Authorization: `Bearer ${token}`
    }
  });



    return next(authReq).pipe(
    catchError((err) => {
      if(err.status !== 401){
        return throwError(()=> err);
      }

      if (req.url.includes('/refresh')) {
        authService.logout();
        return throwError(() => err);
      }

      return authService.refreshToken().pipe(
        switchMap((res) => {
          const refreshReq = req.clone({
            setHeaders: {
              Authorization: `Bearer ${res.token}`
            },
          });
          return next(refreshReq);
        }),
        catchError((refreshErr) => {
          return throwError(()=>refreshErr);
        })
      );
    })
  );
};
