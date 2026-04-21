package net.pop.taskemer.app.presentation;

import lombok.RequiredArgsConstructor;
import net.pop.taskemer.app.data.dto.ProjectRequest;
import net.pop.taskemer.app.service.ProjectService;
import net.pop.taskemer.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<?>> createProject(@PathVariable Integer userId, @RequestBody ProjectRequest project) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>("Success", HttpStatus.CREATED.value(), projectService.addProject(project, userId))
        );
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<ApiResponse<?>> updateProject(@PathVariable Long projectId, @RequestBody ProjectRequest project) {
        return ResponseEntity.ok(
                new ApiResponse<>("Success", HttpStatus.OK.value(), projectService.editProject(projectId, project))
        );
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<ApiResponse<?>> deleteProject(@PathVariable Long projectId) {
        projectService.deleteProject(projectId);
        return ResponseEntity.ok(
                new ApiResponse<>("Success", HttpStatus.OK.value(), "Project deleted successfully")
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<?>> getUserProjects(@PathVariable Integer userId) {
        return ResponseEntity.ok(
                new ApiResponse<>("Success", HttpStatus.OK.value(), projectService.getUserProjects(userId))
        );
    }
}
