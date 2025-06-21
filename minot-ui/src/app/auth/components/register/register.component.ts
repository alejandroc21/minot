import { Component, inject, OnInit } from '@angular/core';
import { GoogleLoginComponent } from '../google-login/google-login.component';
import { AuthService } from '../../services/auth.service';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { passwordMatch } from '../../validators/password-match.validator';
import { RegisterRequest } from '../../model/register-request';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [GoogleLoginComponent, ReactiveFormsModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css',
})
export default class RegisterComponent implements OnInit {
  private _authService = inject(AuthService);
  private _formBuilder = inject(FormBuilder);
  private _router = inject(Router);
  hidePassword = true;
  hideconfirmPassword = true;
  messageError = '';

  form = this._formBuilder.group({
    name: ['', Validators.required],
    email: ['', [Validators.email, Validators.required]],
    password: ['', Validators.required],
    confirmPassword: ['', [Validators.required], [passwordMatch]],
  });

  ngOnInit(): void {
    this.form.get('password')?.valueChanges.subscribe(() => {
      this.form.get('confirmPassword')?.updateValueAndValidity();
      console.log('something');
    });
  }

  passwordVisibility() {
    this.hidePassword = !this.hidePassword;
  }
  confirmPasswordVisibility() {
    this.hideconfirmPassword = !this.hideconfirmPassword;
  }

  register() {
    if(this.form.valid){
      this.messageError = '';
      this._authService.register(this.form.value as RegisterRequest).subscribe({
        next:res=>{
          this._router.navigate(['/home']);
        },
        error:(err:HttpErrorResponse)=>{
          const error: { error: string } = err.error;
          this.messageError = error.error;
        }
      })
    }
  }
}
