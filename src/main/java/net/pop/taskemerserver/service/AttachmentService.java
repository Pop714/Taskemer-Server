package net.pop.taskemerserver.service;

import net.pop.taskemerserver.data.repos.AttachmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttachmentService {

    private final AttachmentRepo attachmentRepo;

    @Autowired
    AttachmentService(AttachmentRepo attachmentRepo) {
        this.attachmentRepo = attachmentRepo;
    }

}
