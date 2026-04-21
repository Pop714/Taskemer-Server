package net.pop.taskemer.app.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import net.pop.taskemer.app.data.dto.TaskRequest;
import net.pop.taskemer.app.data.dto.TaskResponse;
import net.pop.taskemer.app.data.model.entities.Project;
import net.pop.taskemer.app.data.model.entities.Task;
import net.pop.taskemer.app.data.model.enums.TaskStatus;
import net.pop.taskemer.app.data.repo.ProjectRepository;
import net.pop.taskemer.app.data.repo.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    @Transactional
    public TaskResponse addTask(TaskRequest request, Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));

        Task task = Task.builder()
                .project(project)
                .title(request.getTitle())
                .status(TaskStatus.ONGOING)
                .build();

        Task addedTask = taskRepository.save(task);
        return mapToTaskResponse(addedTask);
    }

    @Transactional
    public void deleteTask(Long taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new EntityNotFoundException("Task not found");
        }
        taskRepository.deleteById(taskId);
    }

    @Transactional
    public void changeStatus(Long taskId, TaskStatus status) {
        if (!taskRepository.existsById(taskId)) {
            throw new EntityNotFoundException("Task not found");
        }
        taskRepository.updateTaskStatus(taskId, status);
    }

    @Transactional(readOnly = true)
    public List<TaskResponse> getProjectTasks(Long projectId) {
        return taskRepository.findByProjectId(projectId).stream()
                .map(this::mapToTaskResponse).toList();
    }

    @Transactional
    public String addVoice(Long taskId, MultipartFile voiceFile) throws IOException {
        String filename = UUID.randomUUID() + "_" + voiceFile.getOriginalFilename();
        String VOICE_DIR = "uploads/voices/";
        Path filePath = Paths.get(VOICE_DIR + filename);
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, voiceFile.getBytes());

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));
        task.setVoicePath(filePath.toString());
        taskRepository.save(task);

        return filePath.toString();
    }

    public byte[] getVoice(String path) throws IOException {
        return Files.readAllBytes(Paths.get(path));
    }

    private TaskResponse mapToTaskResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .status(task.getStatus())
                .voicePath(task.getVoicePath())
                .createdAt(task.getCreatedAt())
                .build();
    }
}