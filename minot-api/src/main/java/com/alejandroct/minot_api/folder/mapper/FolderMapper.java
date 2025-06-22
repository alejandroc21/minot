package com.alejandroct.minot_api.folder.mapper;

import com.alejandroct.minot_api.folder.dto.FolderDTO;
import com.alejandroct.minot_api.folder.model.Folder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface FolderMapper {

    Folder folderDTOToFolder(FolderDTO folderDTO);

    @Mapping(target = "parent", qualifiedByName = "toMinimalDTO")
    FolderDTO folderToFolderDTO(Folder folder);

    @Named("toMinimalDTO")
    default FolderDTO toMinimalDTO(Folder parent){
        if(parent == null) return null;
        return new FolderDTO(parent.getId(), parent.getName(), folderToFolderDTO(parent.getParent()));
    }
}
