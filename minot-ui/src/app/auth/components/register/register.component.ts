import { Component } from '@angular/core';
import { GoogleLoginComponent } from "../google-login/google-login.component";

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [GoogleLoginComponent],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export default class RegisterComponent {
  hidePassword=true;
  hideconfirmPassword=true;

  passwordVisibility(){}
  confirmPasswordVisibility(){}
}
