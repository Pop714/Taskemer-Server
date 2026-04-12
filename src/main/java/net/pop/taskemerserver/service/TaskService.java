package net.pop.taskemerserver.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import net.pop.taskemerserver.data.model.entities.Task;
import net.pop.taskemerserver.data.model.responses.TaskResponse;
import net.pop.taskemerserver.data.repos.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepo taskRepo;

    @Autowired
    TaskService(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }

    public TaskResponse addTask(Task task){
        if (task.getId() != null && taskRepo.existsById(task.getId())) {
            throw new EntityExistsException("Task found with ID: " + task.getId());
        }
        Task addedTask = taskRepo.save(task);
        return TaskResponse.builder()
                .id(addedTask.getId())
                .userId(addedTask.getUser().getId())
                .title(addedTask.getTitle())
                .description(addedTask.getDescription())
                .priority(addedTask.getPriority())
                .status(addedTask.getStatus())
                .createdAt(addedTask.getCreatedAt())
                .build();
    }

    public void updateTask(Task task){
        if (!taskRepo.existsById(task.getId())) {
            throw new EntityNotFoundException("Task not found with ID: " + task.getId());
        }
        taskRepo.save(task);
    }

    public void deleteTask(Long id){
        if (!taskRepo.existsById(id)) {
            throw new EntityNotFoundException("Task not found with ID: " + id);
        }
        taskRepo.deleteById(id);
    }

    public TaskResponse findTaskById(Long id){
        Task addedTask = taskRepo.findById(id).orElse(null);
        if (addedTask == null) {
            throw new EntityNotFoundException("Task not found with ID: " + id);
        }
        return TaskResponse.builder()
                .id(addedTask.getId())
                .userId(addedTask.getUser().getId())
                .title(addedTask.getTitle())
                .description(addedTask.getDescription())
                .priority(addedTask.getPriority())
                .status(addedTask.getStatus())
                .createdAt(addedTask.getCreatedAt())
                .build();
    }

    public List<TaskResponse> findUserTasks(Long userId){
        List<Task> rawTasks = taskRepo.findByUserId(userId);
        return rawTasks.stream()
                .map(task -> TaskResponse.builder()
                        .id(task.getId())
                        .userId(task.getUser().getId())
                        .title(task.getTitle())
                        .description(task.getDescription())
                        .priority(task.getPriority())
                        .status(task.getStatus())
                        .createdAt(task.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

}
