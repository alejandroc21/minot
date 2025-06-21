import {
  AbstractControl,
  AsyncValidatorFn,
  ValidationErrors,
} from '@angular/forms';
import { Observable, of } from 'rxjs';

export const passwordMatch: AsyncValidatorFn = (
  control: AbstractControl
): Observable<ValidationErrors | null> => {
  if (!control.parent) return of(null);

  const password = control.parent.get('password')?.value;
  const confirmPassword = control.value;

  return of(password === confirmPassword ? null : { passwordNotMatch: true });
};
