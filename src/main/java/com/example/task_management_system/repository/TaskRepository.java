package com.example.task_management_system.repository;

import com.example.task_management_system.tabels.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task,Long> {
}
