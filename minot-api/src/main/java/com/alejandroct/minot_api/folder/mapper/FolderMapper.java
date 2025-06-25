package com.alejandroct.minot_api.folder.mapper;

import com.alejandroct.minot_api.folder.dto.FolderDTO;
import com.alejandroct.minot_api.folder.model.Folder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface FolderMapper {

    Folder toEntity(FolderDTO folderDTO);

    @Mapping(target = "type", constant = "FOLDER")
    @Mapping(source = "parent",target = "parentId", qualifiedByName = "toMinimalDTO")
    FolderDTO toDto(Folder folder);

    @Named("toMinimalDTO")
    default Long toMinimalDTO(Folder parent){
        return parent == null ? null : parent.getId();
    }
}
