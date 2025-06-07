package com.codewithsidd.taskmanager.repository;

import com.codewithsidd.taskmanager.entity.Subtask;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
@RequiredArgsConstructor
public class SubtaskRepository {

    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;

    private static final String TABLE_NAME = "subtasks";

    private DynamoDbTable<Subtask> getTable() {
        return dynamoDbEnhancedClient.table(TABLE_NAME, TableSchema.fromBean(Subtask.class));
    }

    public void save(Subtask subtask) {
        getTable().putItem(subtask);
    }

    public Subtask findById(String taskId, String id) {
        return getTable().getItem(Key.builder().partitionValue(taskId).sortValue(id).build());
    }

    public List<Subtask> findByTaskId(String taskId) {
        return StreamSupport.stream(
                getTable().query(r -> r.queryConditional(
                        QueryConditional.keyEqualTo(Key.builder().partitionValue(taskId).build())
                )).items().spliterator(), false
        ).collect(Collectors.toList());
    }

    public void deleteById(String taskId, String id) {
        getTable().deleteItem(Key.builder().partitionValue(taskId).sortValue(id).build());
    }

}
