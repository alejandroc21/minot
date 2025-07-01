import { ValidatorFn, AbstractControl, ValidationErrors } from "@angular/forms";

export const passwordMatch:ValidatorFn = (formGroup: AbstractControl):ValidationErrors | null => {
  const passwordControl = formGroup.get('password');
  const confirmPasswordControl = formGroup.get('confirmPassword');
     
  return passwordControl?.value === confirmPasswordControl?.value
    ? null
    : { passwordNotMatch: true };
};