package com.codewithsidd.taskmanager.repository;

import com.codewithsidd.taskmanager.entity.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskRepositoryTest {

    @Mock
    private DynamoDbEnhancedClient mockEnhancedClient;

    @Mock
    private DynamoDbTable<Task> mockTaskTable;

    @InjectMocks
    private TaskRepository taskRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(mockEnhancedClient.table(eq("tasks"), any(TableSchema.class)))
                .thenReturn(mockTaskTable);
        taskRepository = new TaskRepository(mockEnhancedClient);
    }

    @Test
    void testSaveTask() {
        Task task = new Task();
        task.setId("123");
        task.setTitle("Write Tests");
        task.setDescription("Add unit tests for repository");
        task.setStatus("TODO");
        task.setAssignedTo("sid@work.com");
        task.setCreatedAt(Instant.now());

        taskRepository.save(task);

        verify(mockTaskTable).putItem(task);
    }

    @Test
    void testFindById() {
        Task mockTask = new Task();
        mockTask.setId("abc123");
        mockTask.setTitle("Fix Bug");
        mockTask.setDescription("Fix the DynamoDB bug");
        mockTask.setStatus("IN_PROGRESS");
        mockTask.setAssignedTo("dev@team.com");
        mockTask.setCreatedAt(Instant.now());

        when(mockTaskTable.getItem(any(Key.class))).thenReturn(mockTask);

        Task result = taskRepository.findById("abc123");

        assertNotNull(result);
        assertEquals("Fix Bug", result.getTitle());
        assertEquals("dev@team.com", result.getAssignedTo());
    }

    @Test
    void testDeleteById() {
        taskRepository.deleteById("task789");
        verify(mockTaskTable).deleteItem(any(Key.class));
    }
//
//    @Test
//    void testFindAll() {
//        Task task1 = new Task();
//        task1.setId("1");
//        task1.setTitle("Task 1");
//
//        Task task2 = new Task();
//        task2.setId("2");
//        task2.setTitle("Task 2");
//
//        Iterator<Task> iterator = Arrays.asList(task1, task2).iterator();
//        SdkIterable<Task> mockItems = () -> iterator;
//
//        ScanEnhancedIterable<Task> mockScanIterable = mock(ScanEnhancedIterable.class);
//        when(mockTaskTable.scan()).thenReturn(mockScanIterable);
//        when(mockScanIterable.items()).thenReturn(mockItems);
//
//        List<Task> tasks = taskRepository.findAll();
//
//        assertEquals(2, tasks.size());
//        assertEquals("Task 1", tasks.get(0).getTitle());
//    }
}
