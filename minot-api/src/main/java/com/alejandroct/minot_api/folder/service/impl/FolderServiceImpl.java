package com.alejandroct.minot_api.folder.service.impl;

import com.alejandroct.minot_api.folder.dto.FolderDTO;
import com.alejandroct.minot_api.folder.mapper.FolderMapper;
import com.alejandroct.minot_api.folder.model.Folder;
import com.alejandroct.minot_api.folder.repository.FolderRepository;
import com.alejandroct.minot_api.folder.service.IFolderService;
import com.alejandroct.minot_api.user.model.User;
import com.alejandroct.minot_api.user.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FolderServiceImpl implements IFolderService {
    private final FolderRepository folderRepository;
    private final IUserService userService;
    private final FolderMapper folderMapper;

//    @Override
//    public Page<FolderDTO> listFolders(Long parentId, Pageable pageable, String userEmail) {
//        Page<Folder> folders = this.folderRepository.findUserByEmailAndParentId(userEmail, parentId, pageable);
//        return folders.map(this.folderMapper::folderToFolderDTO);
//    }

    @Override
    public Page<FolderDTO> listFolders(Long parentId, Pageable pageable, String userEmail) {
        Page<Folder> folders;
        if(parentId == null){
            folders = this.folderRepository.findByUserEmailAndParentIsNull(userEmail,pageable);
        }else {
            folders = this.folderRepository.findByUserEmailAndParentId(userEmail,parentId,pageable);
        }
        return folders.map(this.folderMapper::folderToFolderDTO);
    }

    @Override
    public FolderDTO save(FolderDTO folderDTO, String email) {
        User user = this.userService.findUserByEmail(email);
        Folder folder = this.folderMapper.folderDTOToFolder(folderDTO);
        folder.setUser(user);
        return this.folderMapper.folderToFolderDTO(this.folderRepository.save(folder));
    }
}
