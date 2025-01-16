import { Component, inject, OnInit } from '@angular/core';
import { NoteService } from '@services/note.service';
import { AsyncPipe } from '@angular/common';
import { Note } from '@models/note';
import { NoteItemComponent } from '../note-item/note-item.component';

@Component({
  selector: 'app-note-list',
  standalone: true,
  imports: [AsyncPipe, NoteItemComponent],
  templateUrl: './note-list.component.html',
  styleUrl: './note-list.component.css',
})
export class NoteListComponent implements OnInit {
  private readonly noteService = inject(NoteService);
  notes$ = this.noteService.notes;
  selectedNote: Note = {};

  ngOnInit(): void {
    this.noteService.selectedNote.subscribe({
      next: (res) => (this.selectedNote = res),
    });
    this.noteService.listNotes().subscribe();
  }

  selectNote(note: Note) {
    this.noteService.selectNote(note);
  }

  createNote() {
    this.noteService.createNote().subscribe({
      error: (err) => alert('error creating note')
    });
  }
}
