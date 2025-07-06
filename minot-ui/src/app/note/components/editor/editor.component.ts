import {
  Component,
  inject,
  input,
  OnInit,
  signal,
} from '@angular/core';
import { QuillModule } from 'ngx-quill';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NoteService } from '../../services/note.service';
import { Note } from '../../model/note';
import { Router } from '@angular/router';
import {
  MatProgressSpinnerModule,
} from '@angular/material/progress-spinner';
import { debounceTime, Subject } from 'rxjs';

@Component({
  selector: 'app-editor',
  standalone: true,
  imports: [QuillModule, FormsModule, CommonModule, MatProgressSpinnerModule],
  templateUrl: './editor.component.html',
  styleUrl: './editor.component.css',
})
export default class EditorComponent implements OnInit {
  private _noteService = inject(NoteService);
  private _router = inject(Router);
  private _notes = this._noteService.notes;
  loading = this._noteService.loading;
  id = input<number>();
  note = signal<Note | null>(null);
  saved = false;

  private titleChange$ = new Subject<string>();
  private contentChange$ = new Subject<string>();

  quillConfig = {
    toolbar: false,
    theme: 'snow',
    placeholder: '',
    modules: {
      toolbar: {
        container: '#custom-toolbar',
      },
    },
  };

  constructor() {}

  ngOnInit(): void {
    this.loadNote();

    this.titleChange$.pipe(debounceTime(500)).subscribe((newTitle) => {
      if (this.note()) {
        this.note()!.name = newTitle;
        this.updateNote();
      }
    });

    this.contentChange$
      .pipe(debounceTime(1000))
      .subscribe((newContent) => {
        if (this.note()) {
          this.note()!.content = newContent;
          this.updateNote();
        }
      });
  }

  loadNote() {
    if (this.id() && !isNaN(Number(this.id()))) {
      this.getNote(this.id()!);
    } else {
      this.close();
    }
  }

  getNote(id: number) {
    const note = this._noteService
      .notes()
      .find((note) => note.id === Number(id));
    if (note) {
      this.note.set(note);
      this.saved = true;
    } else {
      this._noteService.findNote(id).subscribe((res) => {
        this.note.set(res);
        this.saved = true;
      });
    }
  }

  updateNote() {
    if (this.note() !== null) {
      this._noteService.updateNote(this.note()!).subscribe({
        next: () => {
          this.saved = true;
        },
      });
    }
  }

  sendToTash(){
    if (this.id() && !isNaN(Number(this.id()))){
      this._noteService.sendToTrash(this.note()!.id!).subscribe({
        next:()=>{
          this.close();
        }
      })
    }
  }

  onTitleChange(value: string): void {
    this.saved = false;
    this.titleChange$.next(value);
  }

  onEditorContentChange(value: string): void {
    this.saved = false;
    this.contentChange$.next(value);
  }

  insertCustomElement(): void {
    alert('sure?');
  }

  retry() {
    this.loadNote();
  }

  close() {
    this._router.navigateByUrl('/home');
  }
}
