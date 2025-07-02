package com.alejandroct.minot_api.note.mapper;

import com.alejandroct.minot_api.note.dto.NoteDTO;
import com.alejandroct.minot_api.note.model.Note;
import com.alejandroct.minot_api.task.model.Status;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class NoteMapperTest {

    private  final NoteMapper noteMapper = Mappers.getMapper(NoteMapper.class);

    @Test
    void toEntity() {
        Note note = this.noteMapper.toEntity(new NoteDTO(1L, "test", "test", false, null, null,null));
        assertInstanceOf(Note.class, note);
    }

    @Test
    void toDto() {
        Note note = new Note(1L , "test", "test", false, null);
        NoteDTO noteDTO = this.noteMapper.toDto(note);
        assertInstanceOf(NoteDTO.class, noteDTO);
    }
}