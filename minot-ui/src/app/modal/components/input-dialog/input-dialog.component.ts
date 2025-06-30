import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule, MatDialogTitle, MatDialogContent, MatDialogActions, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';

@Component({
  selector: 'app-input-dialog',
  standalone: true,
  imports: [MatDialogModule,
    MatDialogTitle,
    MatDialogContent,
    MatDialogActions,
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    MatButtonModule],
  templateUrl: './input-dialog.component.html',
  styleUrl: './input-dialog.component.css'
})
export class InputDialogComponent {
  readonly dialogRef = inject(MatDialogRef<InputDialogComponent>);
  readonly data = inject(MAT_DIALOG_DATA);
  inputValue = this.data.initialValue || '';

  onSave() {
    this.dialogRef.close(this.inputValue.trim());
  }

  onCancel() {
    this.dialogRef.close();
  }
}
