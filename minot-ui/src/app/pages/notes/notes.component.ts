import {
  Component,
  ElementRef,
  inject,
  OnInit,
  ViewChild,
} from '@angular/core';
import { NoteService } from '../../note/services/note.service';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { NoteCardComponent } from "../../note/components/note-card/note-card.component";
import { Router } from '@angular/router';

@Component({
  selector: 'app-notes',
  standalone: true,
  imports: [MatProgressSpinnerModule, NoteCardComponent],
  templateUrl: './notes.component.html',
  styleUrl: './notes.component.css',
})
export default class NotesComponent implements OnInit {
  private _noteService = inject(NoteService);
  private _router = inject(Router);

  notes = this._noteService.notes;
  viewGrid: boolean = true;
  loading = this._noteService.loading;
  @ViewChild('section') scroll!: ElementRef<HTMLTableSectionElement>;

  ngOnInit(): void {
    this.viewGrid = this._noteService.getDefaultView();
    this.loadNotes();
  }

  loadNotes() {
    if (!this._noteService.firstLoadNote) {
      this._noteService.loadItems().subscribe();
    }
  }

  createNote(){
    this._noteService.saveNote({}).subscribe({
      next:(res)=>{
            this._router.navigate(['home','editor', res.id ]);
      }
    })
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
}
