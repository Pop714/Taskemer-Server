package net.pop.taskemer.app.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.pop.taskemer.app.data.dto.AttachmentResponse;
import net.pop.taskemer.app.data.model.entities.Attachment;
import net.pop.taskemer.app.data.model.entities.Task;
import net.pop.taskemer.app.data.model.enums.AttachmentType;
import net.pop.taskemer.app.data.repo.AttachmentRepository;
import net.pop.taskemer.app.data.repo.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final TaskRepository taskRepository;

    // Handle Url attachments
    @Transactional
    public AttachmentResponse addUrlAttachment(Long taskId, String title, String url) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));

        Attachment attachment = Attachment.builder()
                .title(title)
                .type(AttachmentType.URL)
                .url(url)
                .task(task)
                .build();

        Attachment addedAttachment = attachmentRepository.save(attachment);
        return mapToAttachmentResponse(addedAttachment);
    }

    // Handle File attachments
    @Transactional
    public AttachmentResponse addFileAttachment(Long taskId, String title, MultipartFile file) throws IOException {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));

        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        String ATTACHMENT_DIR = "uploads/attachments/";
        Path filePath = Paths.get(ATTACHMENT_DIR + filename);
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, file.getBytes());

        Attachment attachment = Attachment.builder()
                .title(title)
                .type(AttachmentType.FILE)
                .filePath(filePath.toString())
                .task(task)
                .build();

        Attachment addedAttachment = attachmentRepository.save(attachment);
        return mapToAttachmentResponse(addedAttachment);
    }

    @Transactional
    public void deleteAttachment(Long attachmentId) {
        if (!attachmentRepository.existsById(attachmentId)) {
            throw  new EntityNotFoundException("Attachment with id " + attachmentId + " not found");
        }
        attachmentRepository.deleteById(attachmentId);
    }

    public byte[] getFile(String filePath) throws IOException {
        return Files.readAllBytes(Paths.get(filePath));
    }

    private AttachmentResponse mapToAttachmentResponse(Attachment attachment) {
        return AttachmentResponse.builder()
                .id(attachment.getId())
                .title(attachment.getTitle())
                .type(attachment.getType())
                .url(attachment.getUrl())
                .filePath(attachment.getFilePath())
                .addedIn(attachment.getAddedIn())
                .build();
    }
}