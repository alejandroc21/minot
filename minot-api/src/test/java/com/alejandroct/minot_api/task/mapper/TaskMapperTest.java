package com.alejandroct.minot_api.task.mapper;

import com.alejandroct.minot_api.task.dto.TaskDTO;
import com.alejandroct.minot_api.task.model.Status;
import com.alejandroct.minot_api.task.model.Task;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class TaskMapperTest {

    private final TaskMapper taskMapper = Mappers.getMapper(TaskMapper.class);
    private final Task task = new Task(1l, "test", "test", false, null, Status.TODO);
    private final TaskDTO taskDTO = new TaskDTO(1l, "test", "test",Status.TODO, false, null, null,null);

    @Test
    void toEntity() {
        Task test = this.taskMapper.toEntity(this.taskDTO);
        assertInstanceOf(Task.class, test);
        System.out.println(test);
    }

    @Test
    void toDto() {
        TaskDTO taskDTO = this.taskMapper.toDto(this.task);
        assertInstanceOf(TaskDTO.class, taskDTO);
        System.out.println(taskDTO);
    }
}