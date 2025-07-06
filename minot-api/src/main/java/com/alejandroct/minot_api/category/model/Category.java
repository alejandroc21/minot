package com.alejandroct.minot_api.category.model;

import com.alejandroct.minot_api.note.model.Note;
import com.alejandroct.minot_api.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String color;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Note> notes;

    @ManyToOne(optional = false)
    private User user;

}
