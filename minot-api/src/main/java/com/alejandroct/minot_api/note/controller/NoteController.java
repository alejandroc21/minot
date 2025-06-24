package com.alejandroct.minot_api.note.controller;

import com.alejandroct.minot_api.note.dto.NoteDTO;
import com.alejandroct.minot_api.note.service.INoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {
    private final INoteService noteService;

    @PostMapping("/save")
    public ResponseEntity<NoteDTO> save(@RequestBody NoteDTO noteDTO, Principal principal){
        return ResponseEntity.ok(this.noteService.save(noteDTO, principal.getName()));
    }

}
