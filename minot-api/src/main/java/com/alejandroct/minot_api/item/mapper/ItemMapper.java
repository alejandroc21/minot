package com.alejandroct.minot_api.item.mapper;

import com.alejandroct.minot_api.item.dto.ItemDTO;
import com.alejandroct.minot_api.item.model.Item;
import com.alejandroct.minot_api.note.mapper.NoteMapper;
import com.alejandroct.minot_api.note.model.Note;
import com.alejandroct.minot_api.task.mapper.TaskMapper;
import com.alejandroct.minot_api.task.model.Task;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ItemMapper {

    @Autowired
    protected NoteMapper noteMapper;

    @Autowired
    protected TaskMapper taskMapper;

    public ItemDTO toDto(Item item) {
        if (item instanceof Note n) return this.noteMapper.toDto(n);
        if(item instanceof Task t) return this.taskMapper.toDto(t);
        throw new IllegalArgumentException("MAPPER_ERROR: " + item.getType());
    }
}
