package com.alejandroct.minot_api.task.service.impl;

import com.alejandroct.minot_api.note.mapper.NoteMapper;
import com.alejandroct.minot_api.note.model.Note;
import com.alejandroct.minot_api.task.dto.TaskDTO;
import com.alejandroct.minot_api.task.mapper.TaskMapper;
import com.alejandroct.minot_api.task.model.Task;
import com.alejandroct.minot_api.task.repository.TaskRepository;
import com.alejandroct.minot_api.task.service.ITaskService;
import com.alejandroct.minot_api.user.model.User;
import com.alejandroct.minot_api.user.service.IUserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements ITaskService {
    private final TaskRepository taskRepository;
    private final IUserService userService;
    private final TaskMapper taskMapper;

    public Task findByIdAndUserEmail(Long id, String email){
        return this.taskRepository.findByIdAndUserEmail(id, email)
                .orElseThrow(()-> new EntityNotFoundException("Task not found"));
    }

    @Override
    public TaskDTO save(TaskDTO taskDTO, String email) {
        User user = this.userService.findUserByEmail(email);
        Task task = this.taskMapper.toEntity(taskDTO);
        task.setUser(user);
        return this.taskMapper.toDto(this.taskRepository.save(task));
    }

    @Override
    public TaskDTO update(TaskDTO taskDTO, Long id, String email) {
        return null;
    }
}
