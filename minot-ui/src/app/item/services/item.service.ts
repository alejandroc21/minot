import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { environment } from '../../../environments/environment';
import { Item } from '../model/item';
import { catchError, tap, throwError } from 'rxjs';
import { PaginatedResponse } from '../../core/model/paginated-response';

@Injectable({
  providedIn: 'root',
})
export class ItemService {
  private readonly API_URL = environment.env.API_URL + '/items';
  private _http = inject(HttpClient);
  items = signal<Item[]>([]);

  listItems() {
    return this._http.get<PaginatedResponse<Item>>(this.API_URL).pipe(
      tap((res) => {
        this.items.set(res.content);
      }),
      catchError((err) => {
        return throwError(() => err);
      })
    );
  }


}
