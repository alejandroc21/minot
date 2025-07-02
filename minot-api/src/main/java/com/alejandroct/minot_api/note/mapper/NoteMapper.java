package com.alejandroct.minot_api.note.mapper;

import com.alejandroct.minot_api.note.dto.NoteDTO;
import com.alejandroct.minot_api.note.model.Note;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NoteMapper {

    Note toEntity(NoteDTO noteDTO);

    @Mapping(target = "type", constant = "NOTE")
    NoteDTO toDto(Note note);

}
