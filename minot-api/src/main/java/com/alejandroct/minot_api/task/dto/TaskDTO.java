package com.alejandroct.minot_api.task.dto;

import com.alejandroct.minot_api.item.dto.ItemDTO;
import com.alejandroct.minot_api.task.model.Status;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

public record TaskDTO(
        Long    id,
        String  name,
        String  content,
        @NotNull(message = "status can't be null")
        Status status,
        boolean trashed,
        String  type,
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
    public String getContent() {
        return this.content;
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