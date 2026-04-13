package net.pop.taskemerserver.presentation;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import net.pop.taskemerserver.data.model.entities.Attachment;
import net.pop.taskemerserver.data.model.responses.AttachmentResponse;
import net.pop.taskemerserver.service.AttachmentService;
import net.pop.taskemerserver.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/attachments")
public class AttachmentController {

    private final AttachmentService attachmentService;

    @Autowired
    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createAttachment(@RequestBody Attachment attachment) {
        try {
            AttachmentResponse addedAttachment = attachmentService.addAttachment(attachment);
            return ResponseEntity.ok(new ApiResponse<>(
                    "Success",
                    200,
                    addedAttachment
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
    public ResponseEntity<ApiResponse<?>> deleteAttachment(@PathVariable Long id) {
        try {
            attachmentService.deleteAttachment(id);
            return ResponseEntity.ok(new ApiResponse<>(
                    "Success",
                    200,
                    "Attachment deleted"
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

    @GetMapping("/{workId}")
    public ResponseEntity<ApiResponse<?>> getWorkAttachments(@PathVariable Long workId) {
        try {
            Object result = attachmentService.getWorkAttachments(workId);
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
