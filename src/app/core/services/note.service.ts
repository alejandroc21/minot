import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Note } from '@models/note';
import { BehaviorSubject, filter, Observable, Subject, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class NoteService {
  private readonly http = inject(HttpClient);

  private _notes: Note[] = [];
  private _notes$: BehaviorSubject<Note[]> = new BehaviorSubject<Note[]>([]);
  private _selectedNote$: Subject<Note> = new Subject();

  apiUrl = 'http://127.0.0.1:8080/note';

  constructor() {}

  listNotes(): Observable<Note[]> {
    return this.http.get<Note[]>(`${this.apiUrl}/list`).pipe(
      tap((res) => {
        this._notes = res;
        this._notes$.next(this._notes);
        this.selectNote(res[0]);
      })
    );
  }

  findNote(noteId: number) {
    return this.http.get<Note>(`${this.apiUrl}/${noteId}`);
  }

  createNote(): Observable<Note> {
    return this.http.post<Note>(`${this.apiUrl}/create`, {}).pipe(
      tap((res: Note) => {
        this._notes.push(res);
        this._notes$.next(this._notes);
        this.selectNote(res);
      })
    );
  }

  updateNote(note: Note): Observable<Note> {
    return this.http.put<Note>(`${this.apiUrl}/${note.id}`, note).pipe(tap((res:Note)=>this.selectNote(res)));
  }

  deleteNote(note:Note) {
    return this.http.delete(`${this.apiUrl}/${note.id}`, {
      responseType: 'text',
    }).pipe(
      tap(()=>{
        this._notes = this._notes.filter((item) => item !== note);
        this._notes$.next(this._notes)
        this.selectNote(this._notes[0]);
      })
    );
  }

  selectNote(note: Note) {
    this._selectedNote$.next(note);
  }

  get notes() {
    return this._notes$.asObservable();
  }

  get selectedNote() {
    return this._selectedNote$.asObservable();
  }
}
