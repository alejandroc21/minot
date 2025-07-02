package com.alejandroct.minot_api.task.model;

import com.alejandroct.minot_api.item.model.Item;
import com.alejandroct.minot_api.user.model.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.jspecify.annotations.NonNull;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@DiscriminatorValue("TASK")
public class Task extends Item {

    @Enumerated(EnumType.STRING)
    Status status;

    public Task(Long id, String name, String content, boolean trashed, User user, Status status) {
        super(id, name, content, trashed, user);
        this.status = status;
    }
}
