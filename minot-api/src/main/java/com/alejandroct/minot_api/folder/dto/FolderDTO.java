package com.alejandroct.minot_api.folder.dto;


import com.alejandroct.minot_api.folder.model.Folder;
import com.alejandroct.minot_api.item.dto.ItemDTO;

import java.time.LocalDateTime;

public record FolderDTO(
        Long      id,
        String    name,
        boolean   trashed,
        String    type,
        Long parentId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
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

