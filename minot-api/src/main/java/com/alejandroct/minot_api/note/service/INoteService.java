package com.alejandroct.minot_api.note.service;

import com.alejandroct.minot_api.note.dto.NoteDTO;
import com.alejandroct.minot_api.note.model.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface INoteService {

    Page<NoteDTO> list(String text, Boolean trashed, Pageable pageable, String email);

    Note findByIdAndUserEmail(Long id, String email);

    NoteDTO findNote(Long id, String email);

    NoteDTO save(NoteDTO noteDTO, String email);

    NoteDTO update(NoteDTO noteDTO, Long id, String email);

    NoteDTO sendToTrash(Long id, String email);

    NoteDTO restore(Long id, String email);

    Boolean delete(Long id, String email);
}
