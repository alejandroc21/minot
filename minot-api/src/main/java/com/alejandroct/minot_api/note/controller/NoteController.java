package com.alejandroct.minot_api.note.controller;

import com.alejandroct.minot_api.note.dto.NoteDTO;
import com.alejandroct.minot_api.note.service.INoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {
    private final INoteService noteService;

    @GetMapping
    public ResponseEntity<Page<NoteDTO>> filter(
            @RequestParam(name = "text", required = false) String text,
            @RequestParam(name = "trashed", required = false) Boolean trashed,
            Pageable pageable,
            Principal principal) {

        return ResponseEntity.ok(this.noteService.list(text, trashed, pageable, principal.getName()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteDTO> findNote(@PathVariable Long id, Principal principal){
        return ResponseEntity.ok(this.noteService.findNote(id, principal.getName()));
    }

    @PostMapping
    public ResponseEntity<NoteDTO> save(@Valid @RequestBody NoteDTO noteDTO, Principal principal){
        return new ResponseEntity<>(this.noteService.save(noteDTO, principal.getName()), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteDTO> update(@Valid @RequestBody NoteDTO noteDTO, @PathVariable Long id, Principal principal){
        return ResponseEntity.ok(this.noteService.update(noteDTO, id, principal.getName()));
    }

    @PostMapping("/trash/{id}")
    public ResponseEntity<NoteDTO> sendToTrash(@PathVariable Long id, Principal principal){
        return ResponseEntity.ok(this.noteService.sendToTrash(id, principal.getName()));
    }

    @PostMapping("/restore/{id}")
    public ResponseEntity<NoteDTO> restore(@PathVariable Long id, Principal principal){
        return ResponseEntity.ok(this.noteService.restore(id, principal.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id, Principal principal){
        return ResponseEntity.ok(this.noteService.delete(id, principal.getName()));
    }


}
