package com.alejandroct.minot_api.note.dto;

import com.alejandroct.minot_api.category.dto.CategoryDTO;
import com.alejandroct.minot_api.category.model.Category;

import java.time.LocalDateTime;

public record NoteDTO(
        Long id,
        String name,
        String content,
        boolean trashed,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        CategoryDTO category
){}
