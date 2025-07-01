import { Component, inject } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { GoogleButtonComponent } from '../google-button/google-button.component';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { passwordMatch } from '../../validators/password-match.validator';
import { AuthRequest } from '../../model/auth-request';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [RouterLink, GoogleButtonComponent, ReactiveFormsModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css',
})
export default class RegisterComponent {
hidePassword: any;
passwordVisibility() {
throw new Error('Method not implemented.');
}
  private _formBuilder = inject(FormBuilder);
  private _authService = inject(AuthService);
  private _router = inject(Router);
  public form = this._formBuilder.group(
    {
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      confirmPassword: ['', Validators.required],
    },
    { validators: passwordMatch }
  );

  public register() {
    if(this.form.valid){
      this._authService.register(this.form.value as AuthRequest).subscribe({
        next:()=>{
          this._router.navigateByUrl("/home");
          this.form.reset();
        }
      })
      
    }else{
      this.form.markAllAsTouched();
    }
  }

  get email(){
    return this.form.controls.email;
  }

  get password(){
    return this.form.controls.password;
  }

  get confirmPassword(){
    return this.form.controls.confirmPassword;
  }
}
