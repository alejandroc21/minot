package com.alejandroct.minot_api.folder.service;

import com.alejandroct.minot_api.folder.dto.FolderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface IFolderService {
    Page<FolderDTO> listFolders(Long parentId, Pageable pageable, String userEmail);

    FolderDTO save(FolderDTO folderDTO, String email);
}
