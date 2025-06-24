package com.alejandroct.minot_api.note.service;

import com.alejandroct.minot_api.note.dto.NoteDTO;

public interface INoteService {
    NoteDTO save(NoteDTO noteDTO, String name);
}
