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
export class TaskService {
  private readonly API_URL = `${environment.env.API_URL}/items`;
  private _toastService = inject(ToastrService);
  private _http = inject(HttpClient);
  tasks = signal<Item[]>([]);
  
  loading = signal(false);

  totalElemnts = signal(0);
  pageSize = signal(20);
  currentPage = signal(0);

  textFilter = signal<string>('');
  trashedFilter = signal<boolean | undefined>(undefined);

  loadTasks() {
    const params = new HttpParams()
      .set('sort', 'id,desc')
      .set('page', this.currentPage())
      .set('type', ItemType.TASK)
      .set('text', this.textFilter() || '')
      .set('trashed', 'false');
    this.loading.set(true);
    return this._http.get<IPage<Item>>(this.API_URL, { params }).pipe(
      retry({
              count: 3,
              delay: 1000,
            }),
      tap((res) => {
        this.tasks.update((tasks) => tasks.concat(res.content));
        this.totalElemnts.set(res.totalElements);
      }),
      catchError((err) => {
              this._toastService.error('No pudimos obtener las tareas', 'Error');
              return throwError(() => err);
            }),
      finalize(() => {
        this.loading.set(false);
      })
    );
  }

  loadNextPage() {
    if (this.totalElemnts() <= this.tasks().length) {
      return;
    }
    this.currentPage.update((c) => c + 1);
    this.loadTasks().subscribe();
  }

  setDefaultView(viewGrid:boolean){
    localStorage.setItem("taskView",viewGrid.toString());
  }

  getDefaultView(){
    const value = localStorage.getItem("taskView");
    return value == null ? true : value === 'true';
  }
}
