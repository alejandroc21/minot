package com.alejandroct.minot_api.task.repository;

import com.alejandroct.minot_api.task.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByIdAndUserEmail(Long id, String email);

}
