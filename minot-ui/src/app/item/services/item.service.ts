import { inject, Injectable, signal } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Item, ItemType } from '../model/item';
import { IPage } from '../../core/model/ipage';
import { catchError, finalize, Observable, retry, tap, throwError } from 'rxjs';
import { ToastrService } from 'ngx-toastr';
import { ItemFilter } from '../model/item-filter';

@Injectable({ providedIn: 'root' })
export class ItemService {
  private readonly API_URL = `${environment.env.API_URL}/items`;
  private _http = inject(HttpClient);
  private _toastService = inject(ToastrService);

  items = signal<Item[]>([]);
  loading = signal(false);
  totalElements = signal(0);
  currentPage = signal(0);
  pageSize = 20;

  filter = signal<ItemFilter>({
    type: [],
    text: '',
    trashed: undefined
  });


  loadItems() {
    const filterValue = this.filter();
    const params = new HttpParams()
      .set('sort', 'id,desc')
      .set('page', this.currentPage())
      .set('size', this.pageSize)
      .set('type', filterValue.type?.join(',') || '')
      .set('text', filterValue.text || '')
      .set('trashed', filterValue.trashed !== undefined ? String(filterValue.trashed) : '');

    this.loading.set(true);
    return this._http.get<IPage<Item>>(this.API_URL, { params }).pipe(
      retry({ count: 3, delay: 1000 }),
      tap((res) => {
        this.items.update(items => 
          this.currentPage() === 0 ? res.content : [...items, ...res.content]
        );
        this.totalElements.set(res.totalElements);
      }),
      catchError(err => {
        this._toastService.error('Failed to load items', 'Error');
        return throwError(() => err);
      }),
      finalize(() => this.loading.set(false))
    );
  }

  updateFilter(update: Partial<ItemFilter>) {
    this.filter.update(current => ({ ...current, ...update }));
    this.currentPage.set(0);
    this.loadItems().subscribe();
  }

  loadNextPage() {
    if (this.totalElements() <= this.items().length) return;
    this.currentPage.update(p => p + 1);
    this.loadItems().subscribe();
  }

  sendToTrash(id: number): Observable<Item> {
    return this._http.post<Item>(`${this.API_URL}/trash/${id}`, {}).pipe(
      tap(updatedItem => this._updateItemState(updatedItem))
    );
  }

  restore(id: number): Observable<Item> {
    return this._http.post<Item>(`${this.API_URL}/restore/${id}`, {}).pipe(
      tap(updatedItem => this._updateItemState(updatedItem))
    );
  }

  delete(id: number): Observable<boolean> {
    return this._http.delete<boolean>(`${this.API_URL}/${id}`).pipe(
      tap(() => this._removeItem(id))
    );
  }

  private _updateItemState(updatedItem: Item) {
    this.items.update(items => 
      items.map(item => item.id === updatedItem.id ? updatedItem : item)
    );
  }

  private _removeItem(id: number) {
    this.items.update(items => items.filter(item => item.id !== id));
    this.totalElements.update(total => total - 1);
  }

  setViewPreference(context: 'note' | 'task' | 'trash', viewGrid: boolean) {
    localStorage.setItem(`${context}View`, String(viewGrid));
  }

  getViewPreference(context: string): boolean {
    const value = localStorage.getItem(`${context}View`);
    return value !== null ? value === 'true' : true;
  }
}