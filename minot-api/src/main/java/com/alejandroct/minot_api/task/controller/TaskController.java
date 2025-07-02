package com.alejandroct.minot_api.task.controller;

import com.alejandroct.minot_api.task.dto.TaskDTO;
import com.alejandroct.minot_api.task.service.ITaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final ITaskService taskService;

    @PostMapping
    public ResponseEntity<TaskDTO> save(@Valid @RequestBody TaskDTO taskDTO, Principal principal){
        return new ResponseEntity<>(this.taskService.save(taskDTO, principal.getName()), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> update(@Valid @RequestBody TaskDTO taskDTO, @PathVariable Long id, Principal principal){
        return ResponseEntity.ok(this.taskService.update(taskDTO, id, principal.getName()));
    }
}
