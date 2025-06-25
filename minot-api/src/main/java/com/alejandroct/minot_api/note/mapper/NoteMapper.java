package com.alejandroct.minot_api.note.mapper;

import com.alejandroct.minot_api.folder.dto.FolderDTO;
import com.alejandroct.minot_api.folder.model.Folder;
import com.alejandroct.minot_api.note.dto.NoteDTO;
import com.alejandroct.minot_api.note.model.Note;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface NoteMapper {

    Note toEntity(NoteDTO noteDTO);

    @Mapping(target = "type", constant = "NOTE")
    @Mapping(source = "parent",target = "parentId", qualifiedByName = "toMinimalDTO")
    NoteDTO toDto(Note note);

    @Named("toMinimalDTO")
    default Long toMinimalDTO(Folder parent){
        return parent == null ? null : parent.getId();
    }
}
