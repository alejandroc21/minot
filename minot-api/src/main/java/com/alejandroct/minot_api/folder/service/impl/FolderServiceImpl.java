package com.alejandroct.minot_api.folder.service.impl;

import com.alejandroct.minot_api.folder.dto.FolderDTO;
import com.alejandroct.minot_api.folder.mapper.FolderMapper;
import com.alejandroct.minot_api.folder.model.Folder;
import com.alejandroct.minot_api.folder.repository.FolderRepository;
import com.alejandroct.minot_api.folder.service.IFolderService;
import com.alejandroct.minot_api.user.model.User;
import com.alejandroct.minot_api.user.service.IUserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FolderServiceImpl implements IFolderService {
    private final FolderRepository folderRepository;
    private final IUserService userService;
    private final FolderMapper folderMapper;

    @Override
    public Page<FolderDTO> listFolders(Long parentId, Pageable pageable, String userEmail) {
        Page<Folder> folders;
        if(parentId == null){
            folders = this.folderRepository.findByUserEmailAndParentIsNull(userEmail,pageable);
        }else {
            folders = this.folderRepository.findByUserEmailAndParentId(userEmail,parentId,pageable);
        }
        return folders.map(this.folderMapper::toDto);
    }

    @Override
    public FolderDTO save(FolderDTO folderDTO, String email) {
        User user = this.userService.findUserByEmail(email);
        Folder folder = this.folderMapper.toEntity(folderDTO);
        if(folderDTO.parentId()!=null){
            Folder parent = this.findById(folderDTO.parentId());
            folder.setParent(parent);
        }
        folder.setUser(user);
        return this.folderMapper.toDto(this.folderRepository.save(folder));
    }

    @Override
    public Folder findById(Long id) {
        return this.folderRepository.findById(id).orElseThrow(()->new EntityNotFoundException("folder not found"));
    }
}
