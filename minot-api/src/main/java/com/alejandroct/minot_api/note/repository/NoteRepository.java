package com.alejandroct.minot_api.note.repository;

import com.alejandroct.minot_api.note.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    Optional<Note> findByIdAndUserEmail(Long id, String email);
}
