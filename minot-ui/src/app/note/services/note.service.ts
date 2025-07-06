import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { environment } from '../../../environments/environment';
import {
  tap,
  finalize,
  catchError,
  throwError,
  retry,
  count,
  delay,
  firstValueFrom,
} from 'rxjs';
import { IPage } from '../../core/model/ipage';
import { ToastrService } from 'ngx-toastr';
import { Note } from '../model/note';
import { DialogService } from '../../dialog/services/dialog.service';

@Injectable({
  providedIn: 'root',
})
export class NoteService {
  private readonly API_URL = `${environment.env.API_URL}/notes`;
  private _http = inject(HttpClient);
  private _toastService = inject(ToastrService);

  notes = signal<Note[]>([]);
  trashNotes = signal<Note[]>([]);

  loading = signal(false);
  trashLoading = signal(false);

  totalElemnts = signal(0);
  trashTotal = signal(0);

  currentPage = signal(0);
  trashCurrentPage = signal(0);

  firstLoadNote = false;
  firstLoadTrash = false;

  textFilter = signal<string>('');
  trashedFilter = signal<boolean | undefined>(undefined);

  loadItems() {
    const params = new HttpParams()
      .set('sort', 'updatedAt,desc')
      .set('page', this.currentPage())
      .set('text', this.textFilter() || '')
      .set('trashed', 'false');
    this.loading.set(true);
    return this._http.get<IPage<Note>>(this.API_URL, { params }).pipe(
      retry({
        count: 3,
        delay: 1000,
      }),
      tap((res) => {
        if(this.firstLoadNote){
          // this.notes.update((notes) => notes.concat(res.content));
          this.notes.update((notes)=>{
            const idExists = new Set(notes.map(n => n.id));
            const newNotes = res.content.filter(n => !idExists.has(n.id));
            return notes.concat(newNotes);
          })
        }else{
          this.notes.set(res.content);
        }
        this.totalElemnts.set(res.totalElements);
        this.firstLoadNote = true;
      }),
      catchError((err) => {
        this._toastService.error('No pudimos obtener las notas', 'Error');
        return throwError(() => err);
      }),
      finalize(() => {
        this.loading.set(false);
      })
    );
  }

  findNote(id: number) {
    this.loading.set(true);
    return this._http.get<Note>(`${this.API_URL}/${id}`).pipe(
      retry({
        count: 3,
        delay: 1000,
      }),
      catchError((err) => {
        this._toastService.error('Ocurrió un error', 'Ups');
        return throwError(() => err);
      }),
      finalize(() => {
        this.loading.set(false);
      })
    );
  }

  saveNote(note: Note) {
    this.loading.set(true);
    return this._http.post<Note>(`${this.API_URL}`, note).pipe(
      tap((res)=>{
        this.addToNotesList(res);
      }),
      retry({
        count: 3,
        delay: 1000,
      }),
      catchError((err) => {
        this._toastService.error('No se pudo crear la nota', 'Error');
        return throwError(() => err);
      }),
      finalize(() => {
        this.loading.set(false);
      })
    );
  }

  updateNote(note: Note) {
    this.loading.set(true);
    return this._http.put<Note>(`${this.API_URL}/${note.id}`, note).pipe(
      tap((res) => {
        this.notes.update((notes) =>{
          // notes.map((n) => (n!.id === res.id ? { ...n, ...res } : n))
          return [res, ...notes.filter((n)=>n.id !== res.id)];
        }
        );
      }),
      retry({
        count: 3,
        delay: 1000,
      }),
      catchError((err) => {
        this._toastService.error('cambios no guardados', 'Error');
        return throwError(() => err);
      }),
      finalize(() => {
        this.loading.set(false);
      })
    );
  }

  sendToTrash(noteId: number) {
    return this._http.post<Note>(`${this.API_URL}/trash/${noteId}`, {}).pipe(
      tap((res) => {
        this._toastService.success('Nota enviada a la papelera');
        this.notesToTrash(res);
      }),
      retry({
        count: 3,
        delay: 1000,
      }),
      catchError((err) => {
        this._toastService.error('Algo salió mal', 'Ups');
        return throwError(() => err);
      }),
      finalize(() => {
        this.loading.set(false);
      })
    );
  }


  restoreNote(noteId: number) {
    this._http
      .post<Note>(`${this.API_URL}/restore/${noteId}`, {})
      .pipe(
        tap((res) => {
          this._toastService.success('Nota restaurada');
          this.TrashToNotes(res);
        }),
        retry({
          count: 3,
          delay: 1000,
        }),
        catchError((err) => {
          this._toastService.error('Algo salió mal', 'Ups');
          return throwError(() => err);
        }),
        finalize(() => {
          this.loading.set(false);
        })
      )
      .subscribe();
  }

  deleteNote(noteId: number){
    this._http.delete<boolean>(`${this.API_URL}/${noteId}`)
    .pipe(
        tap(() => {
          this._toastService.success('Nota eliminada');
          this.trashNotes.update(notes => notes.filter(note => note.id !== noteId));
        }),
        retry({
          count: 3,
          delay: 1000,
        }),
        catchError((err) => {
          this._toastService.error('Algo salió mal', 'Ups');
          return throwError(() => err);
        }),
        finalize(() => {
          this.loading.set(false);
        })
      )
      .subscribe();
  }

  loadNextPage() {
    if (this.totalElemnts() <= this.notes().length) {
      return;
    }
    this.currentPage.update((c) => c + 1);
    this.loadItems().subscribe();
  }

  setDefaultView(viewGrid: boolean) {
    localStorage.setItem('noteView', viewGrid.toString());
  }

  addToNotesList(note:Note){
    this.notes.update((notes) => [note, ...notes]);
    this.totalElemnts.update((total) => total + 1);
  }

  TrashToNotes(note: Note) {
    this.trashNotes.update((notes) =>
      notes.filter((n) => n.id !== note.id)
    );
    this.trashTotal.update((total) => total - 1);
    this.addToNotesList(note);
  }

  notesToTrash(note: Note) {
    this.notes.update((notes) => notes.filter((n) => n.id !== note.id));
    this.totalElemnts.update((total) => total - 1);
    this.trashNotes.update((notes) => [note, ...notes]);
    this.trashTotal.update((total) => total + 1);
  }

  getDefaultView() {
    const value = localStorage.getItem('noteView');
    return value == null ? true : value === 'true';
  }

}
