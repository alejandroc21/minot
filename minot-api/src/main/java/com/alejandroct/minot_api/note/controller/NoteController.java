package com.alejandroct.minot_api.note.controller;

import com.alejandroct.minot_api.note.dto.NoteDTO;
import com.alejandroct.minot_api.note.service.INoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {
    private final INoteService noteService;

    @PostMapping
    public ResponseEntity<NoteDTO> save(@Valid @RequestBody NoteDTO noteDTO, Principal principal){
        return new ResponseEntity<>(this.noteService.save(noteDTO, principal.getName()), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteDTO> update(@Valid @RequestBody NoteDTO noteDTO, @PathVariable Long id, Principal principal){
        return ResponseEntity.ok(this.noteService.update(noteDTO, id, principal.getName()));
    }
}
