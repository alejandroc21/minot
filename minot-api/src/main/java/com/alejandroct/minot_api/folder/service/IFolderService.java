package com.alejandroct.minot_api.folder.service;

import com.alejandroct.minot_api.folder.dto.FolderDTO;
import com.alejandroct.minot_api.folder.model.Folder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

public interface IFolderService {
//    Page<FolderDTO> listFolders(Long parentId, Pageable pageable, String userEmail);

    Folder findByIdAndUserEmail(Long id, String email);

    FolderDTO save(FolderDTO folderDTO, String email);

    FolderDTO update(FolderDTO folderDTO, Long id, String email);
}
