import {
  Component,
  ElementRef,
  inject,
  OnInit,
  ViewChild,
} from '@angular/core';
import { ItemCardComponent } from '../../item/components/item-card/item-card.component';
import { TaskService } from '../../task/services/task.service';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

@Component({
  selector: 'app-tasks',
  standalone: true,
  imports: [ItemCardComponent, MatProgressSpinnerModule],
  templateUrl: './tasks.component.html',
  styleUrl: './tasks.component.css',
})
export default class TasksComponent implements OnInit {
  private _taskService = inject(TaskService);
  tasks = this._taskService.tasks;
  loading = this._taskService.loading;
  viewGrid: boolean = true;
  @ViewChild('section') scroll!: ElementRef<HTMLTableSectionElement>;

  ngOnInit(): void {
    this.viewGrid = this._taskService.getDefaultView();
    this.loadTasks();
  }

  loadTasks() {
    if (this.tasks().length === 0) {
      this._taskService.loadTasks().subscribe();
    }
    this._taskService;
  }

  showGrid() {
    this.viewGrid = true;
    this._taskService.setDefaultView(true);
  }

  showList() {
    this.viewGrid = false;
    this._taskService.setDefaultView(false);
  }

  onScroll(event: any) {
    if (!this.onBottom(event)) {
      return;
    }

    this._taskService.loadNextPage();
  }

  onBottom(event: any) {
    const tracker = event.target;
    const limit = tracker.scrollHeight - tracker.clientHeight;
    return tracker.scrollTop === limit;
  }
}
