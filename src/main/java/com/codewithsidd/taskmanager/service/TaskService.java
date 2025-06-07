package com.codewithsidd.taskmanager.service;

import com.codewithsidd.taskmanager.entity.Task;
import com.codewithsidd.taskmanager.repository.TaskRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

//    public TaskService(TaskRepository taskRepository) {
//        this.taskRepository = taskRepository;
//    }

    public Task create(Task task) {
        task.setId(UUID.randomUUID().toString());
        task.setCreatedAt(Instant.now());
        taskRepository.save(task);
        return task;
    }

    public Task get(String id) {
        return taskRepository.findById(id);
    }

    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    public void delete(String id) {
        taskRepository.deleteById(id);
    }

    public void update(String id, Task updatedTask) {
        Task existing = taskRepository.findById(id);
        if (existing != null) {
            updatedTask.setId(id);
            updatedTask.setCreatedAt(existing.getCreatedAt());
            taskRepository.save(updatedTask);
        }
    }

}
