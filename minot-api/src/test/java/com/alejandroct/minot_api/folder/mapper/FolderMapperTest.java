package com.alejandroct.minot_api.folder.mapper;

import com.alejandroct.minot_api.folder.dto.FolderDTO;
import com.alejandroct.minot_api.folder.model.Folder;
import com.alejandroct.minot_api.item.dto.ItemType;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class FolderMapperTest {

    private final FolderMapper folderMapper = Mappers.getMapper(FolderMapper.class);

    @Test
    void folderDTOToFolder() {
        Folder folder = new Folder(1L, "test",false, null, null, null);
        FolderDTO folderDTO = this.folderMapper.toDto(folder);
        assertInstanceOf(FolderDTO.class, folderDTO);
    }

    @Test
    void folderToFolderDTO() {
        FolderDTO folderDTO = new FolderDTO(1L, "test", false,FolderDTO.class.getSimpleName(),null);
        Folder folder = this.folderMapper.toEntity(folderDTO);
        assertInstanceOf(Folder.class, folder);
    }


}