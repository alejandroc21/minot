import { Component, HostListener, inject, OnInit } from '@angular/core';
import { NoteListComponent } from './components/note-list/note-list.component';
import { NoteComponent } from './components/note/note.component';
import { NoteService } from './core/services/note.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [NoteListComponent, NoteComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent implements OnInit {
  private readonly noteService = inject(NoteService);

  showAside = true;
  isSelectedNote = false;
  smallScreen = false;

  ngOnInit(): void {
    this.noteService.selectedNote.subscribe({
      next: () => {
        this.isSelectedNote = true;
        if (this.smallScreen) {
          this.showAside = false;
        }
      },
      error: (err) => console.log(err),
    });
  }

  @HostListener('window:resize', [])
  onResize() {
    this.smallScreen = window.innerWidth < 800;
  }

  toggleAside() {
    this.showAside = !this.showAside;
  }
}
