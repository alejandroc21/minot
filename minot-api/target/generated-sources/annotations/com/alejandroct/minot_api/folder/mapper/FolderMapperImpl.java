package com.alejandroct.minot_api.folder.mapper;

import com.alejandroct.minot_api.folder.dto.FolderDTO;
import com.alejandroct.minot_api.folder.model.Folder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-21T23:58:46-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.15 (Ubuntu)"
)
@Component
public class FolderMapperImpl implements FolderMapper {

    @Override
    public Folder folderDTOToFolder(FolderDTO folderDTO) {
        if ( folderDTO == null ) {
            return null;
        }

        Folder folder = new Folder();

        folder.setId( folderDTO.id() );
        folder.setName( folderDTO.name() );
        folder.setParent( folderDTOToFolder( folderDTO.parent() ) );

        return folder;
    }

    @Override
    public FolderDTO folderToFolderDTO(Folder folder) {
        if ( folder == null ) {
            return null;
        }

        FolderDTO parent = null;
        Long id = null;
        String name = null;

        parent = toMinimalDTO( folder.getParent() );
        id = folder.getId();
        name = folder.getName();

        FolderDTO folderDTO = new FolderDTO( id, name, parent );

        return folderDTO;
    }
}
