import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { tap, finalize, catchError, throwError, retry } from 'rxjs';
import { environment } from '../../../environments/environment';
import { IPage } from '../../core/model/ipage';
import { Item, ItemType } from '../../item/model/item';
import { ToastrService } from 'ngx-toastr';

@Injectable({
  providedIn: 'root',
})
export class TrashService {
  private readonly API_URL = `${environment.env.API_URL}/items`;
  private _http = inject(HttpClient);
  private _toastService = inject(ToastrService);

  items = signal<Item[]>([]);
  tasks = signal<Item[]>([]);
  notes = signal<Item[]>([]);
  loading = signal(false);

  totalElemnts = signal(0);
  pageSize = signal(20);
  currentPage = signal(0);

  typeFilter = signal<ItemType[]>([]);
  textFilter = signal<string>('');
  trashedFilter = signal<boolean | undefined>(undefined);

  loadItems() {
    const params = new HttpParams()
      .set('sort', 'id,desc')
      .set('page', this.currentPage())
      .set('size', 20)
      .set('type', this.typeFilter().join(','))
      .set('text', this.textFilter() || '')
      .set('trashed', this.trashedFilter()?.toString() || '');
    this.loading.set(true);
    return this._http.get<IPage<Item>>(this.API_URL, { params }).pipe(
      retry({
              count: 3,
              delay: 1000,
            }),
      tap((res) => {
        this.items.update((items) => items.concat(res.content));

        this.totalElemnts.set(res.totalElements);
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
    if (this.totalElemnts() <= this.items().length) {
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
