import { Component, input } from '@angular/core';
import { Item } from '../../../item/model/item';
import { Folder } from '../../model/folder';
import { Note } from '../../model/note';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-note-card',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './note-card.component.html',
  styleUrl: './note-card.component.css',
})
export class NoteCardComponent {
  grid = input.required<boolean>();
  item = input.required<Item>();

  isNote(item: Item): item is Note {
    return item.type === 'NOTE';
  }

  get noteItem(): Note | null {
    return this.isNote(this.item()) ? (this.item() as Note) : null;
  }
}
