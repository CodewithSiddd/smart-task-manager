package com.codewithsidd.taskmanager.repository;

import com.codewithsidd.taskmanager.entity.User;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {

    private final DynamoDbTable<User> userTable;

    public UserRepository(DynamoDbEnhancedClient enhancedClient) {
        this.userTable = enhancedClient.table("users", TableSchema.fromBean(User.class));
    }

    public void save(User user) {
        userTable.putItem(user);
    }

    public User findById(String id) {
        return userTable.getItem(Key.builder().partitionValue(id).build());
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        for (User user : userTable.scan().items()) {
            users.add(user);
        }
        return users;
    }

}
