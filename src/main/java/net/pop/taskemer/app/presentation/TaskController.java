package net.pop.taskemer.app.presentation;

import lombok.RequiredArgsConstructor;
import net.pop.taskemer.app.data.dto.TaskRequest;
import net.pop.taskemer.app.data.model.enums.TaskStatus;
import net.pop.taskemer.app.service.TaskService;
import net.pop.taskemer.utils.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/project/{projectId}")
    public ResponseEntity<ApiResponse<?>> createTask(@PathVariable Long projectId, @RequestBody TaskRequest task) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>("Success", HttpStatus.CREATED.value(), taskService.addTask(task, projectId))
        );
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<ApiResponse<?>> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.ok(
                new ApiResponse<>("Success", HttpStatus.OK.value(), "Task deleted successfully")
        );
    }

    @PatchMapping("/{taskId}/status")
    public ResponseEntity<ApiResponse<?>> changeStatus(@PathVariable Long taskId, @RequestParam TaskStatus status) {
        taskService.changeStatus(taskId, status);
        return ResponseEntity.ok(
                new ApiResponse<>("Success", HttpStatus.OK.value(), "Task updated successfully")
        );
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<ApiResponse<?>> getProjectTasks(@PathVariable Long projectId) {
        return ResponseEntity.ok(
                new ApiResponse<>("Success", HttpStatus.OK.value(), taskService.getProjectTasks(projectId))
        );
    }

    // --- Voice File Endpoints ---

    @PostMapping(value = "/{taskId}/voice", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<?>> uploadVoice(@PathVariable Long taskId, @RequestParam("file") MultipartFile file) throws IOException {
        String path = taskService.addVoice(taskId, file);
        return ResponseEntity.ok(
                new ApiResponse<>("Success", HttpStatus.OK.value(), path)
        );
    }

    @GetMapping("/voice")
    public ResponseEntity<byte[]> downloadVoice(@RequestParam("path") String path) throws IOException {
        byte[] audioBytes = taskService.getVoice(path);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"voice_message.mp3\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(audioBytes);
    }
}
