package net.pop.taskemerserver.presentation;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import net.pop.taskemerserver.data.model.entities.Task;
import net.pop.taskemerserver.data.model.responses.TaskResponse;
import net.pop.taskemerserver.service.TaskService;
import net.pop.taskemerserver.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createTask(@RequestBody Task task) {
        try {
            TaskResponse addedTask = taskService.addTask(task);
            return ResponseEntity.ok(new ApiResponse<>(
                    "Success",
                    200,
                    addedTask
            ));
        } catch (EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.FOUND).body(new ApiResponse<>(
                    "Error",
                    404,
                    e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(
                    "Error",
                    500,
                    e.getMessage()
            ));
        }
    }

    @PutMapping
    public ResponseEntity<ApiResponse<?>> updateTask(@RequestBody Task task) {
        try {
            taskService.updateTask(task);
            return ResponseEntity.ok(new ApiResponse<>(
                    "Success",
                    200,
                    "Task updated"
            ));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(
                    "Error",
                    404,
                    e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(
                    "Error",
                    500,
                    e.getMessage()
            ));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteTask(@PathVariable Long id) {
        try {
            taskService.deleteTask(id);
            return ResponseEntity.ok(new ApiResponse<>(
                    "Success",
                    200,
                    "Task deleted"
            ));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(
                    "Error",
                    404,
                    e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(
                    "Error",
                    500,
                    e.getMessage()
            ));
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<?>> getUserTasks(@PathVariable Long userId) {
        try {
            Object result = taskService.findUserTasks(userId);
            return ResponseEntity.ok(new ApiResponse<>(
                    "Success",
                    200,
                    result
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(
                    "Error",
                    500,
                    e.getMessage()
            ));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getTaskById(@RequestParam Long taskId) {
        try {
            TaskResponse task = taskService.findTaskById(taskId);
            return ResponseEntity.ok(new ApiResponse<>(
                    "Success",
                    200,
                    task
            ));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(
                    "Error",
                    404,
                    e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(
                    "Error",
                    500,
                    e.getMessage()
            ));
        }

    }

}
