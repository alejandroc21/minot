package com.alejandroct.minot_api.note.dto;

import com.alejandroct.minot_api.folder.dto.FolderDTO;
import com.alejandroct.minot_api.item.dto.ItemDTO;

public record NoteDTO(
        Long    id,
        String  name,
        String  content,
        boolean trashed,
        String  type,
        Long parentId

) implements ItemDTO {
    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean isTrashed() {
        return this.trashed;
    }

    @Override
    public String getType() {
        return this.type;
    }
}
