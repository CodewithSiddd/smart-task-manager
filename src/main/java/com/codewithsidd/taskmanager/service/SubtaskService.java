package com.codewithsidd.taskmanager.service;

import com.codewithsidd.taskmanager.entity.Subtask;
import com.codewithsidd.taskmanager.repository.SubtaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubtaskService {

    private final SubtaskRepository subtaskRepository;

    public Subtask createSubtask(String taskId, Subtask subtask) {
        subtask.setId(UUID.randomUUID().toString());
        subtask.setTaskId(taskId);
        subtask.setCreatedAt(Instant.now());
        subtaskRepository.save(subtask);
        return subtask;
    }

    public List<Subtask> getSubtasksForTask(String taskId) {
        return subtaskRepository.findByTaskId(taskId);
    }

    public Subtask getSubtask(String taskId, String subtaskId) {
        return subtaskRepository.findById(taskId, subtaskId);
    }

    public void deleteSubtask(String taskId, String subtaskId) {
        subtaskRepository.deleteById(taskId, subtaskId);
    }

    public void updateSubtask(String taskId, String subtaskId, Subtask updatedSubtask) {
        Subtask existing = subtaskRepository.findById(taskId, subtaskId);
        if (existing != null) {
            updatedSubtask.setTaskId(taskId);
            updatedSubtask.setId(subtaskId);
            updatedSubtask.setCreatedAt(existing.getCreatedAt());
            subtaskRepository.save(updatedSubtask);
        }
    }

}
