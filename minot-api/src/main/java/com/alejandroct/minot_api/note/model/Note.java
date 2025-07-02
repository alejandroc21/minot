package com.alejandroct.minot_api.note.model;

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

    public Note(Long id, String name, String content, boolean deleted, User user) {
        super(id, name,content, deleted, user);
    }
}
