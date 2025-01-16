import { Component, Input, OnInit } from '@angular/core';
import { Note } from '../../core/models/note';
import { SlicePipe } from '@angular/common';

@Component({
  selector: 'app-note-item',
  standalone: true,
  imports: [SlicePipe],
  templateUrl: './note-item.component.html',
  styleUrl: './note-item.component.css',
})
export class NoteItemComponent {
  @Input() note!: Note;

  getBreakpoint(content: string): number {
    const maxCharacters = 50;
    if (content.length <= maxCharacters) {
      return content.length;
    }
    const breakpoint = content.lastIndexOf(' ', maxCharacters);
    return breakpoint !== -1 ? breakpoint : maxCharacters;
  }
}
