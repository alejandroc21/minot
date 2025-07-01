package com.alejandroct.minot_api.note.dto;

import com.alejandroct.minot_api.item.dto.ItemDTO;
import jakarta.validation.constraints.NotBlank;


import java.time.LocalDateTime;

import static com.alejandroct.minot_api.constants.ValidationMessage.NAME_REQUIRED;

public record NoteDTO(
        Long    id,
        @NotBlank(message = NAME_REQUIRED)
        String  name,
        String  content,
        boolean trashed,
        String  type,
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
