package net.pop.taskemer.app.data.repo;

import net.pop.taskemer.app.data.model.entities.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    List<Attachment> findByTaskId(Long taskId);
}
