package net.pop.taskemerserver.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import net.pop.taskemerserver.data.model.entities.Attachment;
import net.pop.taskemerserver.data.model.responses.AttachmentResponse;
import net.pop.taskemerserver.data.repos.AttachmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttachmentService {

    private final AttachmentRepo attachmentRepo;

    @Autowired
    AttachmentService(AttachmentRepo attachmentRepo) {
        this.attachmentRepo = attachmentRepo;
    }

    public AttachmentResponse addAttachment(Attachment attachment) {
        if (attachment.getId() != null && attachmentRepo.existsById(attachment.getId())) {
            throw new EntityExistsException("Attachment found with ID: " + attachment.getId());
        }
        Attachment addedAttachment = attachmentRepo.save(attachment);
        return AttachmentResponse.builder()
                .id(addedAttachment.getId())
                .wordId(addedAttachment.getWork().getId())
                .title(addedAttachment.getTitle())
                .type(addedAttachment.getType())
                .attachedUrl(addedAttachment.getAttachmentUrl())
                .filePath(addedAttachment.getFilePath())
                .build();
    }

    public void deleteAttachment(Long id) {
        if (!attachmentRepo.existsById(id)) {
            throw new EntityNotFoundException("Attachment not found with ID: " + id);
        }
        attachmentRepo.deleteById(id);
    }

    public List<AttachmentResponse> getWorkAttachments(Long workId) {
        List<Attachment> attachments = attachmentRepo.findAllByWorkId(workId);
        return attachments.stream().map(attachment ->
                AttachmentResponse.builder()
                        .id(attachment.getId())
                        .wordId(attachment.getWork().getId())
                        .title(attachment.getTitle())
                        .type(attachment.getType())
                        .attachedUrl(attachment.getAttachmentUrl())
                        .filePath(attachment.getFilePath())
                        .build()
        ).collect(Collectors.toList());
    }

}
