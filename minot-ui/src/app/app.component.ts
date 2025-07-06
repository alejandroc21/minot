import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { QuillModule } from 'ngx-quill';
import { Toast } from 'ngx-toastr';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, QuillModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent {
  
}
