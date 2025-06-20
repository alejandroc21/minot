import { Component, inject } from '@angular/core';
import { GoogleLoginComponent } from '../google-login/google-login.component';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [GoogleLoginComponent, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export default class LoginComponent {
  private _formBuilder = inject(FormBuilder);
  hidePassword: boolean = true;
  loginError: string = '';

  form = this._formBuilder.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', Validators.required],
  });

  passwordVisibility() {
    this.hidePassword = !this.hidePassword;
  }
}
