import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { environment } from '../../../environments/environment';
import { Item, ItemType } from '../../item/model/item';
import {
  tap,
  finalize,
  catchError,
  throwError,
  retry,
  count,
  delay,
} from 'rxjs';
import { IPage } from '../../core/model/ipage';
import { Overlay, ToastrService } from 'ngx-toastr';

@Injectable({
  providedIn: 'root',
})
export class NoteService {
  private readonly API_URL = `${environment.env.API_URL}/items`;
  private _http = inject(HttpClient);
  private _toastService = inject(ToastrService);

  notes = signal<Item[]>([]);
  loading = signal(false);
  totalElemnts = signal(0);
  currentPage = signal(0);

  typeFilter = signal<ItemType[]>([]);
  textFilter = signal<string>('');
  trashedFilter = signal<boolean | undefined>(undefined);

  loadItems() {
    const params = new HttpParams()
      .set('sort', 'id,desc')
      .set('page', this.currentPage())
      .set('type', ItemType.NOTE)
      .set('text', this.textFilter() || '')
      .set('trashed', 'false');
    this.loading.set(true);
    return this._http.get<IPage<Item>>(this.API_URL, { params }).pipe(
      retry({
        count: 3,
        delay: 1000,
      }),
      tap((res) => {
        // this.items.set(res.content);
        this.notes.update((notes) => notes.concat(res.content));

        this.totalElemnts.set(res.totalElements);
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

  loadNextPage() {
    if (this.totalElemnts() <= this.notes().length) {
      return;
    }
    this.currentPage.update((c) => c + 1);
    this.loadItems().subscribe();
  }

  setDefaultView(viewGrid:boolean){
    localStorage.setItem("noteView",viewGrid.toString());
  }

  getDefaultView(){
    const value = localStorage.getItem("noteView");
    return value == null ? true : value === 'true';
  }
}
