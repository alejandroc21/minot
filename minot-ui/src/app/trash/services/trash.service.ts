import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { tap, finalize, catchError, throwError, retry } from 'rxjs';
import { environment } from '../../../environments/environment';
import { IPage } from '../../core/model/ipage';
import { ToastrService } from 'ngx-toastr';
import { Note } from '../../note/model/note';
import { NoteService } from '../../note/services/note.service';

@Injectable({
  providedIn: 'root',
})
export class TrashService {
  private readonly API_URL = `${environment.env.API_URL}/notes`;
  private _http = inject(HttpClient);
  private _toastService = inject(ToastrService);
  private _noteService = inject(NoteService);

  notes = this._noteService.trashNotes;
  loading = signal(false);

  totalElemnts = signal(0);
  pageSize = signal(20);
  currentPage = signal(0);

  textFilter = signal<string>('');
  trashedFilter = signal<boolean | undefined>(undefined);

  loadItems() {
    const params = new HttpParams()
      .set('sort', 'id,desc')
      .set('page', this.currentPage())
      .set('size', 20)
      .set('text', this.textFilter() || '')
      .set('trashed', 'true');
    this.loading.set(true);
    return this._http.get<IPage<Note>>(this.API_URL, { params }).pipe(
      retry({
              count: 3,
              delay: 1000,
            }),
      tap((res) => {
        if(this._noteService.firstLoadTrash){
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
        this._noteService.firstLoadTrash = true;
      }),
      catchError((err) => {
              this._toastService.error('No pudimos obtener las elementos eliminados', 'Error');
              return throwError(() => err);
            }),
      finalize(() => {
        this.loading.set(false);
      })
    );
  }

  loadNextPage() {
    if (this.totalElemnts() <= this.notes().length) {
      return;
    }
    this.currentPage.update((c) => c + 1);
    this.loadItems().subscribe();
  }

  setDefaultView(viewGrid: boolean) {
    localStorage.setItem('itemView', viewGrid.toString());
  }

  getDefaultView() {
    const value = localStorage.getItem('itemView');
    return value == null ? true : value === 'true';
  }
}
