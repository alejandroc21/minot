package com.alejandroct.minot_api.folder.mapper;

import com.alejandroct.minot_api.folder.dto.FolderDTO;
import com.alejandroct.minot_api.folder.model.Folder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-23T23:48:54-0500",
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

        parentId = toMinimalDTO( folder.getParent() );
        id = folder.getId();
        name = folder.getName();
        trashed = folder.isTrashed();

        String type = "FOLDER";

        FolderDTO folderDTO = new FolderDTO( id, name, trashed, type, parentId );

        return folderDTO;
    }
}
