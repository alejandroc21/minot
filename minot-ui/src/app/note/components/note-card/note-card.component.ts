import { Component, inject, input, OnInit } from '@angular/core';
import { Note } from '../../model/note';
import { Router } from '@angular/router';
import { NoteService } from '../../services/note.service';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';
import { DialogService } from '../../../dialog/services/dialog.service';

@Component({
  selector: 'app-note-card',
  standalone: true,
  imports: [],
  templateUrl: './note-card.component.html',
  styleUrl: './note-card.component.css',
})
export class NoteCardComponent {
  private _router = inject(Router);
  private _noteService = inject(NoteService);
  private _sanitizer = inject(DomSanitizer);
  private _dialogService = inject(DialogService);

  grid = input.required<boolean>();
  note = input.required<Note>();
  content = '';

  getRelativeDate() {
    const date = this.note().updatedAt!;
    const diffSeconds = Math.floor(
      (new Date().getTime() - new Date(date).getTime()) / 1000
    );

    const MINUTE = 60;
    const HOUR = 3600;
    const DAY = 84600;

    if (diffSeconds < MINUTE) {
      return `${diffSeconds} segundos`;
    } else if (diffSeconds < HOUR) {
      const minutes = Math.floor(diffSeconds / MINUTE);
      return `${minutes} ${minutes === 1 ? 'minuto' : 'minutos'}`;
    } else if (diffSeconds < DAY) {
      return new Date(date).toLocaleTimeString('es-CO', {
        hour: '2-digit',
        minute: '2-digit',
      });
    }
    return new Date(date).toLocaleDateString('es-CO', {
      hour: '2-digit',
      minute: '2-digit',
      day: 'numeric',
      month: '2-digit',
      year: 'numeric',
    });
  }

  open() {
    this._router.navigate(['home', 'editor', this.note().id]);
  }

  sendToTrash() {
    this._dialogService
      .confirm('¿Estas seguro que quieres mover esta nota a la papelera?', 'Enviar a papelera')
      .subscribe((confirmed) => {
        if(confirmed){
          this._noteService.sendToTrash(this.note().id!).subscribe();
        }
      });
  }

  delete() {
    this._dialogService
      .confirm('¿Estas seguro que quieres eliminar permanentemente esta nota?', 'Elimiar nota')
      .subscribe((confirmed) => {
        if(confirmed){
          this._noteService.deleteNote(this.note().id!);
        }
      });
  }

  restore() {
    this._dialogService
      .confirm('¿Estas seguro que quieres recuperar esta nota?', 'Restaurar nota')
      .subscribe((confirmed) => {
        if(confirmed){
          this._noteService.restoreNote(this.note().id!);
        }
      });
  }

  getTextPreview(html: string, limit: number = 100): string {
    const div = document.createElement('div');
    div.innerHTML = html;
    const text = div.textContent || div.innerText || '';
    return text.length > limit ? text.substring(0, limit) + '...' : text;
  }

  getSanitizedPreview(): SafeHtml {
    if (this.note().content) {
      return this._sanitizer.bypassSecurityTrustHtml(this.note().content!);
    }
    return '';
  }

  get trashed() {
    return this.note().trashed!;
  }
}
