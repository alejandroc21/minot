package com.alejandroct.minot_api.note.service;

import com.alejandroct.minot_api.note.dto.NoteDTO;
import com.alejandroct.minot_api.note.model.Note;

import java.security.Principal;

public interface INoteService {

    Note findByIdAndUserEmail(Long id, String email);

    NoteDTO save(NoteDTO noteDTO, String name);

    NoteDTO update(NoteDTO noteDTO, Long id, String email);

}
