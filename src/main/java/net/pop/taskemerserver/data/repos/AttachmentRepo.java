package net.pop.taskemerserver.data.repos;

import net.pop.taskemerserver.data.model.entities.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttachmentRepo extends JpaRepository<Attachment, Long> {
    List<Attachment> findAllByWorkId(Long id);
}
