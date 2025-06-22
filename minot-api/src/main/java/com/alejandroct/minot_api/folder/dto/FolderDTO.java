package com.alejandroct.minot_api.folder.dto;


public record FolderDTO(
        Long id,
        String name,
        FolderDTO parent
) {
}
