package net.pop.taskemerserver.data.repos;

import net.pop.taskemerserver.data.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepo extends JpaRepository<Attachment, Long> { }
