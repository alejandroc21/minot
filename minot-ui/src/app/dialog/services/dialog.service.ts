import { inject, Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmComponent } from '../components/confirm/confirm.component';

@Injectable({
  providedIn: 'root'
})
export class DialogService {
  private _dialog = inject(MatDialog);

  confirm(message:string, title?:string){
    return this._dialog.open(ConfirmComponent,{
      data: {message, title},
      minWidth: '350px'
    })
    .afterClosed();
  }
}
