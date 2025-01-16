import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Tag } from '../models/tag';
import { NoteService } from './note.service';
import { tap } from 'rxjs';
import { Note } from '../models/note';

@Injectable({
  providedIn: 'root',
})
export class TagService {
  private readonly http = inject(HttpClient);
  private readonly noteService = inject(NoteService);

  apiUrl = 'http://127.0.0.1:8080/tag';

  constructor() {}

  listTags() {
    return this.http.get<Tag[]>(`${this.apiUrl}/list`);
  }

  listByNote(noteId: number) {
    return this.http.get<Tag[]>(`${this.apiUrl}/list/${noteId}`);
  }

  createTag(tag: Tag) {    
    return this.http.post<Tag>(`${this.apiUrl}/create`, tag);
  }

  deleteTag(tagId: number) {
    return this.http.delete(`${this.apiUrl}/${tagId}`,{responseType:'text'});
  }
}
