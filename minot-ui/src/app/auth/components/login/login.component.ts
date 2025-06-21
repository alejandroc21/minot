import { Component, inject } from '@angular/core';
import { GoogleLoginComponent } from '../google-login/google-login.component';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { LoginResquest } from '../../model/login-resquest';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [GoogleLoginComponent, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export default class LoginComponent {
  private _formBuilder = inject(FormBuilder);
  private _authService = inject(AuthService);
  private _router = inject(Router);
  hidePassword: boolean = true;
  loginError: string = '';

  form = this._formBuilder.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', Validators.required],
  });

  passwordVisibility() {
    this.hidePassword = !this.hidePassword;
  }

  login() {
    if (this.form.valid) {
      this.loginError = '';
      this._authService.login(this.form.value as LoginResquest).subscribe({
        next: (res) => {
          this._router.navigate(['/home']);
        },
        error: (err: HttpErrorResponse) => {
          const error: { error: string } = err.error;
          this.loginError = error.error;
        },
      });
    } else {
      this.form.markAllAsTouched();
    }
  }
}
