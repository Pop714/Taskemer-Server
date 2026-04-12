package net.pop.taskemerserver.presentation;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import net.pop.taskemerserver.data.model.entities.Work;
import net.pop.taskemerserver.service.WorkService;
import net.pop.taskemerserver.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/works")
public class WorkController {

    private final WorkService workService;

    @Autowired
    public WorkController(WorkService workService) {
        this.workService = workService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createWork(@RequestBody Work work) {
        try {
            Work addedWork = workService.addWork(work);
            return ResponseEntity.ok(new ApiResponse<>(
                    "Success",
                    200,
                    addedWork
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

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteWork(@PathVariable Long id) {
        try {
            workService.deleteWork(id);
            return ResponseEntity.ok(new ApiResponse<>(
                    "Success",
                    200,
                    "Work deleted"
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

    @GetMapping("/{taskId}")
    public ResponseEntity<ApiResponse<?>> getTaskWorks(@PathVariable Long taskId) {
        try {
            Object result = workService.findTaskWorks(taskId);
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

}
