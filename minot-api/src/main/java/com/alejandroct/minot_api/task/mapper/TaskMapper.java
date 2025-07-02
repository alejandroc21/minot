package com.alejandroct.minot_api.task.mapper;

import com.alejandroct.minot_api.task.dto.TaskDTO;
import com.alejandroct.minot_api.task.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    Task toEntity(TaskDTO taskDTO);

    @Mapping(target = "type", constant = "TASK")
    TaskDTO toDto(Task task);
}
