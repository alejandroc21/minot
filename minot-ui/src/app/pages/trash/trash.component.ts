import {
  Component,
  ElementRef,
  inject,
  OnInit,
  ViewChild,
} from '@angular/core';
import { ItemService } from '../../item/services/item.service';
import { ItemCardComponent } from '../../item/components/item-card/item-card.component';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { TrashService } from '../../trash/services/trash.service';

@Component({
  selector: 'app-trash',
  standalone: true,
  imports: [ItemCardComponent, MatProgressSpinnerModule],
  templateUrl: './trash.component.html',
  styleUrl: './trash.component.css',
})
export default class TrashComponent implements OnInit {
  private _itemService = inject(ItemService);
  items = this._itemService.items;
  loading = this._itemService.loading;
  viewGrid = true;

  ngOnInit() {
    this.viewGrid = this._itemService.getViewPreference('trash');
    this._itemService.updateFilter({
      trashed: true
    });
  }

  showGrid() {
    this._itemService.setViewPreference('trash', true);
    this.viewGrid = true;
  }

  showList() {
    this._itemService.setViewPreference('trash', false);
    this.viewGrid = false;
  }

  onScroll(event: any) {
    if (!this.onBottom(event)) {
      return;
    }

    this._itemService.loadNextPage();
  }

  onBottom(event: any) {
    const tracker = event.target;
    const limit = tracker.scrollHeight - tracker.clientHeight;
    return tracker.scrollTop === limit;
  }
}
