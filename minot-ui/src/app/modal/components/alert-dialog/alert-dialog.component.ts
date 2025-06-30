import { Component, inject } from '@angular/core';
import { MatDialogModule, MatDialogTitle, MatDialogContent, MatDialogActions, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import {MatButtonModule} from '@angular/material/button'

@Component({
  selector: 'app-alert-dialog',
  standalone: true,
  imports: [MatDialogModule, MatDialogTitle, MatButtonModule],
  templateUrl: './alert-dialog.component.html',
  styleUrl: './alert-dialog.component.css'
})
export class AlertDialogComponent {
readonly dialogRef = inject(MatDialogRef<AlertDialogComponent>);
  readonly data = inject(MAT_DIALOG_DATA);

  onOk() {
    this.dialogRef.close();
  }
}
