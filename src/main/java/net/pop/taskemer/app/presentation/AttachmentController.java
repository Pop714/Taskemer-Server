package net.pop.taskemer.app.presentation;

import lombok.RequiredArgsConstructor;
import net.pop.taskemer.app.data.dto.AttachmentResponse;
import net.pop.taskemer.app.service.AttachmentService;
import net.pop.taskemer.utils.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/attachments")
@RequiredArgsConstructor
public class AttachmentController {

    private final AttachmentService attachmentService;

    @PostMapping("/task/{taskId}/url")
    public ResponseEntity<ApiResponse<?>> addUrl(
            @PathVariable Long taskId,
            @RequestParam String title,
            @RequestParam String url) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>("Success", HttpStatus.CREATED.value(), attachmentService.addUrlAttachment(taskId, title, url))
        );
    }

    @PostMapping(value = "/task/{taskId}/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<?>> addFile(
            @PathVariable Long taskId,
            @RequestParam String title,
            @RequestParam("file") MultipartFile file) throws IOException {

        AttachmentResponse attachment = attachmentService.addFileAttachment(taskId, title, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>("Success", HttpStatus.CREATED.value(), attachment)
        );
    }

    @DeleteMapping("/{attachmentId}")
    public ResponseEntity<ApiResponse<?>> deleteAttachment(@PathVariable Long attachmentId) {
        attachmentService.deleteAttachment(attachmentId);
        return ResponseEntity.ok(
                new ApiResponse<>("Success", HttpStatus.OK.value(), "Attachment deleted successfully")
        );
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadFile(@RequestParam("path") String path) {
        try {
            byte[] fileBytes = attachmentService.getFile(path);

            // Extract filename from the path to set it in the download header
            String filename = path.substring(path.lastIndexOf("/") + 1);
            if (filename.contains("\\")) filename = filename.substring(filename.lastIndexOf("\\") + 1);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(fileBytes);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}