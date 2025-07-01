package com.alejandroct.minot_api.folder.mapper;

import com.alejandroct.minot_api.folder.dto.FolderDTO;
import com.alejandroct.minot_api.folder.model.Folder;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-30T23:22:30-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.15 (Ubuntu)"
)
@Component
public class FolderMapperImpl implements FolderMapper {

    @Override
    public Folder toEntity(FolderDTO folderDTO) {
        if ( folderDTO == null ) {
            return null;
        }

        Folder.FolderBuilder<?, ?> folder = Folder.builder();

        folder.id( folderDTO.id() );
        folder.name( folderDTO.name() );
        folder.trashed( folderDTO.trashed() );
        folder.type( folderDTO.type() );
        folder.createdAt( folderDTO.createdAt() );
        folder.updatedAt( folderDTO.updatedAt() );

        return folder.build();
    }

    @Override
    public FolderDTO toDto(Folder folder) {
        if ( folder == null ) {
            return null;
        }

        Long parentId = null;
        Long id = null;
        String name = null;
        boolean trashed = false;
        LocalDateTime createdAt = null;
        LocalDateTime updatedAt = null;

        parentId = toMinimalDTO( folder.getParent() );
        id = folder.getId();
        name = folder.getName();
        trashed = folder.isTrashed();
        createdAt = folder.getCreatedAt();
        updatedAt = folder.getUpdatedAt();

        String type = "FOLDER";

        FolderDTO folderDTO = new FolderDTO( id, name, trashed, type, parentId, createdAt, updatedAt );

        return folderDTO;
    }
}
