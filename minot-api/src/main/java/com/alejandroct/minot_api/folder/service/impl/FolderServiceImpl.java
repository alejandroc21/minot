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
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FolderServiceImpl implements IFolderService {
    private final FolderRepository folderRepository;
    private final IUserService userService;
    private final FolderMapper folderMapper;

//    @Override
//    public Page<FolderDTO> listFolders(Long parentId, Pageable pageable, String userEmail) {
//        Page<Folder> folders;
//        if(parentId == null){
//            folders = this.folderRepository.findByUserEmailAndParentIsNull(userEmail,pageable);
//        }else {
//            folders = this.folderRepository.findByUserEmailAndParentId(userEmail,parentId,pageable);
//        }
//        return folders.map(this.folderMapper::toDto);
//    }

    @Override
    public Folder findByIdAndUserEmail(Long id, String email){
        return this.folderRepository.findByIdAndUserEmail(id, email)
                .orElseThrow(()->new EntityNotFoundException("folder not found"));
    }

    @Override
    public FolderDTO save(FolderDTO folderDTO, String email) {
        User user = this.userService.findUserByEmail(email);
        Folder folder = this.folderMapper.toEntity(folderDTO);
        if(folderDTO.parentId()!=null){
            Folder parent = this.findByIdAndUserEmail(folderDTO.parentId(), email);
            folder.setParent(parent);
        }
        folder.setUser(user);
        return this.folderMapper.toDto(this.folderRepository.save(folder));
    }

    @Override
    public FolderDTO update(FolderDTO folderDTO, Long id, String email) {
        Folder folder = this.findByIdAndUserEmail(id, email);
        folder.setName(folderDTO.getName());
        return folderMapper.toDto(this.folderRepository.save(folder));
    }
}
