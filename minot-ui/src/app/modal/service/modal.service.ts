import { inject, Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Observable } from 'rxjs';
import { ConfirmDialogComponent } from '../components/confirm-dialog/confirm-dialog.component';
import { AlertDialogComponent } from '../components/alert-dialog/alert-dialog.component';
import { InputDialogComponent } from '../components/input-dialog/input-dialog.component';

@Injectable({
  providedIn: 'root',
})
export class ModalService {
  private _dialog = inject(MatDialog);

  comfirm(message: string): Observable<boolean> {
    return this._dialog
      .open(ConfirmDialogComponent, {
        data: { message },
        minWidth: '350px',
      })
      .afterClosed();
  }

  alert(message: string): Observable<boolean> {
    return this._dialog
      .open(AlertDialogComponent, {
        data: { message },
        minWidth: '10rem',
        minHeight: '10rem'
      })
      .afterClosed();
  }

  input(
    placeholder: string,
    title?: string,
    initialValue?: string
  ): Observable<string | undefined> {
    return this._dialog
      .open(InputDialogComponent, {
        data: { placeholder, title, initialValue },
        minWidth: '350px',
      })
      .afterClosed();
  }

}
