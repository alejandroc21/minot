package com.alejandroct.minot_api.folder.model;

import com.alejandroct.minot_api.item.model.Item;
import com.alejandroct.minot_api.note.model.Note;
import com.alejandroct.minot_api.user.model.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;



@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@Entity
@NoArgsConstructor
@DiscriminatorValue("FOLDER")
public class Folder extends Item {

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Folder parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Folder> children;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Note> notes;

    public Folder(Long id, String name, boolean deleted, User user, Folder parent, List<Folder> children) {
        super(id, name, deleted, user);
        this.parent = parent;
        this.children = children;
    }
}
