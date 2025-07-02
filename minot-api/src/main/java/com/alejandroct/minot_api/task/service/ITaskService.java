package com.alejandroct.minot_api.task.service;

import com.alejandroct.minot_api.task.dto.TaskDTO;

public interface ITaskService {
    TaskDTO save(TaskDTO taskDTO, String email);

    TaskDTO update(TaskDTO taskDTO, Long id, String email);
}
