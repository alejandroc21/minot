package com.alejandroct.minot_api.note.service.impl;

import com.alejandroct.minot_api.folder.model.Folder;
import com.alejandroct.minot_api.folder.service.IFolderService;
import com.alejandroct.minot_api.note.dto.NoteDTO;
import com.alejandroct.minot_api.note.mapper.NoteMapper;
import com.alejandroct.minot_api.note.model.Note;
import com.alejandroct.minot_api.note.repository.NoteRepository;
import com.alejandroct.minot_api.note.service.INoteService;
import com.alejandroct.minot_api.user.model.User;
import com.alejandroct.minot_api.user.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements INoteService {
    private final IUserService userService;
    private final IFolderService folderService;
    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;

    @Override
    public NoteDTO save(NoteDTO noteDTO, String email){
        User user = this.userService.findUserByEmail(email);
        Note note = this.noteMapper.toEntity(noteDTO);
        if(noteDTO.parentId()!=null){
            Folder parent = this.folderService.findById(noteDTO.parentId());
            note.setParent(parent);
        }
        note.setUser(user);
        return this.noteMapper.toDto(this.noteRepository.save(note));
    }
}
