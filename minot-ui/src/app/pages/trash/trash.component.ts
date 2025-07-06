import {
  Component,
  ElementRef,
  inject,
  OnInit,
  ViewChild,
} from '@angular/core';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { TrashService } from '../../trash/services/trash.service';
import { NoteCardComponent } from '../../note/components/note-card/note-card.component';
import { NoteService } from '../../note/services/note.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-trash',
  standalone: true,
  imports: [NoteCardComponent, MatProgressSpinnerModule, FormsModule, CommonModule],
  templateUrl: './trash.component.html',
  styleUrl: './trash.component.css',
})
export default class TrashComponent implements OnInit {
  private _noteService = inject(TrashService);
  private _note = inject(NoteService);
    notes = this._noteService.notes;
    viewGrid: boolean = true;
    loading = this._noteService.loading;
    @ViewChild('section') scroll!: ElementRef<HTMLTableSectionElement>;
  
    ngOnInit(): void {
      this.viewGrid = this._noteService.getDefaultView();
      this.loadNotes();
    }
  
    loadNotes() {
      if (!this._note.firstLoadTrash) {
        this._noteService.loadItems().subscribe();
      }
    }
  
    showGrid() {
      this.viewGrid = true;
      this._noteService.setDefaultView(true);
    }
  
    showList() {
      this.viewGrid = false;
      this._noteService.setDefaultView(false);
    }
  
    onScroll(event: any) {
      if (!this.onBottom(event)) {
        return;
      }
  
      this._noteService.loadNextPage();
    }
  
    onBottom(event: any) {
      const tracker = event.target;
      const limit = tracker.scrollHeight - tracker.clientHeight;
      return tracker.scrollTop === limit;
    }

    onTextChange(value:string){
        console.log(value);
    }
}
