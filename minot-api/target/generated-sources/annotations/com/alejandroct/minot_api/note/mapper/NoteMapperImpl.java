package com.alejandroct.minot_api.note.mapper;

import com.alejandroct.minot_api.note.dto.NoteDTO;
import com.alejandroct.minot_api.note.model.Note;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-25T17:27:18-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.15 (Ubuntu)"
)
@Component
public class NoteMapperImpl implements NoteMapper {

    @Override
    public Note toEntity(NoteDTO noteDTO) {
        if ( noteDTO == null ) {
            return null;
        }

        Note.NoteBuilder<?, ?> note = Note.builder();

        note.id( noteDTO.id() );
        note.name( noteDTO.name() );
        note.trashed( noteDTO.trashed() );
        note.type( noteDTO.type() );
        note.createdAt( noteDTO.createdAt() );
        note.updatedAt( noteDTO.updatedAt() );
        note.content( noteDTO.content() );
        note.preview( noteDTO.preview() );

        return note.build();
    }

    @Override
    public NoteDTO toDto(Note note) {
        if ( note == null ) {
            return null;
        }

        Long parentId = null;
        Long id = null;
        String name = null;
        String preview = null;
        String content = null;
        boolean trashed = false;
        LocalDateTime createdAt = null;
        LocalDateTime updatedAt = null;

        parentId = toMinimalDTO( note.getParent() );
        id = note.getId();
        name = note.getName();
        preview = note.getPreview();
        content = note.getContent();
        trashed = note.isTrashed();
        createdAt = note.getCreatedAt();
        updatedAt = note.getUpdatedAt();

        String type = "NOTE";

        NoteDTO noteDTO = new NoteDTO( id, name, preview, content, trashed, type, parentId, createdAt, updatedAt );

        return noteDTO;
    }
}
