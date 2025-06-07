package com.codewithsidd.taskmanager.repository;

import com.codewithsidd.taskmanager.entity.Task;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskRepository {

    private DynamoDbTable<Task> taskTable;

    public TaskRepository(DynamoDbEnhancedClient enhancedClient) {
        this.taskTable = enhancedClient.table("Tasks", TableSchema.fromBean(Task.class));
    }

    public void save(Task task) {
        taskTable.putItem(task);
    }

    public Task findById(String id) {
        return taskTable.getItem(Key.builder().partitionValue(id).build());
    }

    public void deleteById(String id) {
        taskTable.deleteItem(Key.builder().partitionValue(id).build());
    }

    public List<Task> findAll() {
        List<Task> tasks = new ArrayList<>();
        taskTable.scan().items().forEach(tasks::add);
        return tasks;
    }

}
