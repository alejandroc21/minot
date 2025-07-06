package com.alejandroct.minot_api.note.service.impl;

import com.alejandroct.minot_api.category.model.Category;
import com.alejandroct.minot_api.category.service.CategoryService;
import com.alejandroct.minot_api.note.dto.NoteDTO;
import com.alejandroct.minot_api.note.filter.NoteSpecification;
import com.alejandroct.minot_api.note.mapper.NoteMapper;
import com.alejandroct.minot_api.note.model.Note;
import com.alejandroct.minot_api.note.repository.NoteRepository;
import com.alejandroct.minot_api.note.service.INoteService;
import com.alejandroct.minot_api.user.model.User;
import com.alejandroct.minot_api.user.service.IUserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements INoteService {
    private final IUserService userService;
    private final CategoryService categoryService;
    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;

    @Override
    public Page<NoteDTO> list(String text, Boolean trashed, Pageable pageable, String email) {
        Specification<Note> specification = NoteSpecification.withFilters(text, trashed, email);
        Page<Note> notes = this.noteRepository.findAll(specification, pageable);
        return notes.map(this.noteMapper::toDto);
    }

    @Override
    public Note findByIdAndUserEmail(Long id, String email) {
        return this.noteRepository.findByIdAndUserEmail(id, email)
                .orElseThrow(()-> new EntityNotFoundException("Note not found"));
    }

    @Override
    public NoteDTO findNote(Long id, String email) {
        return this.noteMapper.toDto(this.findByIdAndUserEmail(id, email));
    }

    @Override
    public NoteDTO save(NoteDTO noteDTO, String email){
        User user = this.userService.findUserByEmail(email);
        Note note = this.noteMapper.toEntity(noteDTO);
        note.setId(null);
        note.setUser(user);
        return this.noteMapper.toDto(this.noteRepository.save(note));
    }

    @Override
    public NoteDTO update(NoteDTO noteDTO, Long id, String email) {
        Note note = this.findByIdAndUserEmail(id, email);
        note.setName(noteDTO.name());
        note.setContent(noteDTO.content());
        if(noteDTO.category() != null){
            Category category = this.categoryService.findByIdAndUserEmail(noteDTO.category().id(), email);
            note.setCategory(category);
        }else{
            note.setCategory(null);
        }
        return this.noteMapper.toDto(this.noteRepository.save(note));
    }

    @Override
    public NoteDTO sendToTrash(Long id, String email) {
        Note note = this.findByIdAndUserEmail(id, email);
        note.setTrashed(true);
        return this.noteMapper.toDto(this.noteRepository.save(note));
    }

    @Override
    public NoteDTO restore(Long id, String email) {
        Note note = this.findByIdAndUserEmail(id, email);
        note.setTrashed(false);
        return this.noteMapper.toDto(this.noteRepository.save(note));
    }

    @Override
    public Boolean delete(Long id, String email) {
        this.findByIdAndUserEmail(id, email);
        this.noteRepository.deleteById(id);
        return true;
    }
}
