package com.alejandroct.minot_api.item.mapper;

import com.alejandroct.minot_api.folder.mapper.FolderMapper;
import com.alejandroct.minot_api.folder.model.Folder;
import com.alejandroct.minot_api.item.dto.ItemDTO;
import com.alejandroct.minot_api.item.model.Item;
import com.alejandroct.minot_api.note.mapper.NoteMapper;
import com.alejandroct.minot_api.note.model.Note;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ItemMapper {

    @Autowired
    protected FolderMapper folderMapper;
    @Autowired
    protected NoteMapper noteMapper;

    public ItemDTO toDto(Item item) {
        if (item instanceof Note n) return this.noteMapper.toDto(n);
        if (item instanceof Folder f) return this.folderMapper.toDto(f);
        throw new IllegalArgumentException("MAPPER_ERROR: " + item.getType());
    }
}
