package com.codewithsidd.taskmanager.controller;

import com.codewithsidd.taskmanager.entity.Subtask;
import com.codewithsidd.taskmanager.service.SubtaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks/{taskId}/subtasks")
@RequiredArgsConstructor
public class SubtaskController {

    private final SubtaskService subtaskService;

    @PostMapping
    public ResponseEntity<Subtask> createSubtask(@PathVariable String taskId, @RequestBody Subtask subtask) {
        return ResponseEntity.ok(subtaskService.createSubtask(taskId, subtask));
    }

    @GetMapping
    public ResponseEntity<List<Subtask>> getAllSubtasks(@PathVariable String taskId) {
        return ResponseEntity.ok(subtaskService.getSubtasksForTask(taskId));
    }

    @GetMapping("/{subtaskId}")
    public ResponseEntity<Subtask> getSubtask(@PathVariable String taskId, @PathVariable String subtaskId) {
        Subtask subtask = subtaskService.getSubtask(taskId, subtaskId);
        return subtask != null ? ResponseEntity.ok(subtask) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{subtaskId}")
    public ResponseEntity<String> updateSubtask(@PathVariable String taskId, @PathVariable String subtaskId, @RequestBody Subtask subtask) {
        subtaskService.updateSubtask(taskId, subtaskId, subtask);
        return ResponseEntity.ok("Subtask updated successfully");
    }

    @DeleteMapping("/{subtaskId}")
    public ResponseEntity<String> deleteSubtask(@PathVariable String taskId, @PathVariable String subtaskId) {
        subtaskService.deleteSubtask(taskId, subtaskId);
        return ResponseEntity.ok("Subtask deleted successfully");
    }

}
