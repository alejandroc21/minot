package com.alejandroct.minot_api.note.mapper;

import com.alejandroct.minot_api.note.dto.NoteDTO;
import com.alejandroct.minot_api.note.model.Note;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NoteMapper {

    Note toEntity(NoteDTO noteDTO);

    NoteDTO toDto(Note note);

}
