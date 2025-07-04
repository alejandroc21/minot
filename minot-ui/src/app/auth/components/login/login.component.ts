import { Component, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
import { GoogleButtonComponent } from "../google-button/google-button.component";
import {FormBuilder, ReactiveFormsModule, Validators} from '@angular/forms'
import { AuthService } from '../../services/auth.service';
import { AuthRequest } from '../../model/auth-request';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [RouterLink, GoogleButtonComponent, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export default class LoginComponent {
  private _formBuilder = inject(FormBuilder);
  private _authService = inject(AuthService);
  form =this._formBuilder.group({
    email:['', [Validators.required, Validators.email]],
    password:['', Validators.required]
  })
  hidePassword = true;

  login(){
    if(this.form.valid){
      this._authService.login(this.form.value as AuthRequest).subscribe({
        next:()=>{
          this.form.reset();
        }
      })
    }else{
      this.form.markAllAsTouched();
    }
  }

  togglePassword(){
    this.hidePassword = !this.hidePassword;
  }

  get email(){
    return this.form.controls.email;
  }

  get password(){
    return this.form.controls.password;
  }
}
