import {
  Component,
  ElementRef,
  EventEmitter,
  HostListener,
  inject,
  Input,
  OnInit,
  Output,
  ViewChild,
} from '@angular/core';
import { NoteService } from '@services/note.service';
import { TagService } from '@services/tag.service';
import { Note } from '@models/note';
import { Tag } from '@models/tag';
import { CommonModule } from '@angular/common';
import { FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { debounceTime, distinctUntilChanged } from 'rxjs';
import { TagItemComponent } from '../tag-item/tag-item.component';

@Component({
  selector: 'app-note',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, TagItemComponent],
  templateUrl: './note.component.html',
  styleUrl: './note.component.css',
})
export class NoteComponent implements OnInit {
  @ViewChild('editor', { static: true })
  editor!: ElementRef<HTMLTextAreaElement>;

  @Output() toggleAside = new EventEmitter();
  @Input() fullSizedNote: boolean = false;
  private readonly noteService = inject(NoteService);
  private readonly tagService = inject(TagService);

  note: Note = {content:''};
  textControl = new FormControl();
  updatedNote = true;
  showDeleteModal = false;
  showInfoModal = false;

  ngOnInit(): void {
    this.textControl.disable();
    this.noteService.selectedNote.subscribe({
      next: (res) => {
        this.textControl.enable();
        
        this.note = res;
        this.textControl.setValue(res.content);
        this.editor.nativeElement.setSelectionRange(0, 0);
        this.editor.nativeElement.focus();
      }
    });

    this.textControl.valueChanges
      .pipe(debounceTime(500), distinctUntilChanged())
      .subscribe((value) => {
        this.note.content = value;
        this.noteService.updateNote(this.note).subscribe({
          error: () => (this.updatedNote = false),
        });
      });
  }

  onToggleAside() {
    this.toggleAside.emit();
  }

  createNote() {
    this.noteService.createNote().subscribe({
      error: (err) => {
        console.log(err);
        alert('error creating note');
      },
    });
    console.log(this.note);
  }

  addTag(event: Event) {
    const inputEvent = event.target as HTMLInputElement;
    const value = inputEvent.value;
    if (value.trim().length !== 0) {
      const tag: Tag = { name: value, note: { id: this.note.id } };
      this.tagService.createTag(tag).subscribe({
        next: (res) => {
          if (this.note.tags !== null) {
            this.note.tags?.push(res);
          } else {
            this.note.tags = [res];
          }
        },
        error: () => {
          alert('error creating tag');
        },
      });
    }
    inputEvent.value = '';
  }

  removeTag(tag: Tag) {
    this.tagService.deleteTag(tag.id!).subscribe({
      next: (res) => {
        console.log(res);

        this.note.tags = this.note.tags!.filter((item) => item !== tag);
      },
      error: () => {
        alert('error removing tag');
      },
    });
  }

  openDeleteModal(){
    this.showDeleteModal = true;
  }

  deleteNote(){
    this.noteService.deleteNote(this.note).subscribe({
      next:()=>{
        this.showDeleteModal = false;
      }
    });
  }

  openInfoModal(){
    this.showInfoModal = true;
  }

  clickOutside(event:Event){
    event.stopPropagation();    
  }

  closeModals(){
    this.showDeleteModal = false;
    this.showInfoModal = false;
  }

  get wordCount(){
    return this.note.content?.trim().split(/\s+/).length || 0;
  }

  get characterCount(){
    return this.note.content?.length || 0;
  }
}
