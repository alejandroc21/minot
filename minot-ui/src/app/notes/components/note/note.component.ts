import { Component } from '@angular/core';
import { NoteGridComponent } from "../note-grid/note-grid.component";
import { NoteListComponent } from "../note-list/note-list.component";

@Component({
  selector: 'app-note',
  standalone: true,
  imports: [NoteGridComponent, NoteListComponent],
  templateUrl: './note.component.html',
  styleUrl: './note.component.css'
})
export default class NoteComponent {
  viewGrid = true;

  showGrid(){
    this.viewGrid = true;
  }
  showList(){
    this.viewGrid = false;
  }
}
