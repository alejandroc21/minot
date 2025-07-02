package com.alejandroct.minot_api.task.mapper;

import com.alejandroct.minot_api.task.dto.TaskDTO;
import com.alejandroct.minot_api.task.model.Status;
import com.alejandroct.minot_api.task.model.Task;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-01T19:24:33-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.15 (Ubuntu)"
)
@Component
public class TaskMapperImpl implements TaskMapper {

    @Override
    public Task toEntity(TaskDTO taskDTO) {
        if ( taskDTO == null ) {
            return null;
        }

        Task.TaskBuilder<?, ?> task = Task.builder();

        task.id( taskDTO.id() );
        task.name( taskDTO.name() );
        task.trashed( taskDTO.trashed() );
        task.content( taskDTO.content() );
        task.type( taskDTO.type() );
        task.createdAt( taskDTO.createdAt() );
        task.updatedAt( taskDTO.updatedAt() );
        task.status( taskDTO.status() );

        return task.build();
    }

    @Override
    public TaskDTO toDto(Task task) {
        if ( task == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String content = null;
        Status status = null;
        boolean trashed = false;
        LocalDateTime createdAt = null;
        LocalDateTime updatedAt = null;

        id = task.getId();
        name = task.getName();
        content = task.getContent();
        status = task.getStatus();
        trashed = task.isTrashed();
        createdAt = task.getCreatedAt();
        updatedAt = task.getUpdatedAt();

        String type = "TASK";

        TaskDTO taskDTO = new TaskDTO( id, name, content, status, trashed, type, createdAt, updatedAt );

        return taskDTO;
    }
}
