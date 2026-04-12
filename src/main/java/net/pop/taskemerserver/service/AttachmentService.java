package net.pop.taskemerserver.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import net.pop.taskemerserver.data.model.entities.Attachment;
import net.pop.taskemerserver.data.repos.AttachmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttachmentService {

    private final AttachmentRepo attachmentRepo;

    @Autowired
    AttachmentService(AttachmentRepo attachmentRepo) {
        this.attachmentRepo = attachmentRepo;
    }

    public Attachment addAttachment(Attachment attachment) {
        if (attachment.getId() != null && attachmentRepo.existsById(attachment.getId())) {
            throw new EntityExistsException("Attachment found with ID: " + attachment.getId());
        }
        return attachmentRepo.save(attachment);
    }

    public void deleteAttachment(Long id) {
        if (!attachmentRepo.existsById(id)) {
            throw new EntityNotFoundException("Attachment not found with ID: " + id);
        }
        attachmentRepo.deleteById(id);
    }

    public List<Attachment> getWorkAttachments(Long workId) {
        return attachmentRepo.findAllByWorkId(workId);
    }

}
