package com.alejandroct.minot_api.note.model;

import com.alejandroct.minot_api.folder.model.Folder;
import com.alejandroct.minot_api.item.model.Item;
import com.alejandroct.minot_api.user.model.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@Entity
@NoArgsConstructor
@DiscriminatorValue("NOTE")
public class Note extends Item {

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Folder parent;

    public Note(Long id, String name, boolean deleted, User user, String content, String preview, Folder parent) {
        super(id, name, deleted, user);
        this.content = content;
        this.parent = parent;
    }
}
