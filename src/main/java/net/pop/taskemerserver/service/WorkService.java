package net.pop.taskemerserver.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import net.pop.taskemerserver.data.model.entities.Work;
import net.pop.taskemerserver.data.model.responses.TaskResponse;
import net.pop.taskemerserver.data.model.responses.WorkResponse;
import net.pop.taskemerserver.data.repos.WorkRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkService {

    private final WorkRepo workRepo;

    @Autowired
    public WorkService(WorkRepo workRepo) {
        this.workRepo = workRepo;
    }

    public WorkResponse addWork(Work work) {
        if (work.getId() != null && workRepo.existsById(work.getId())) {
            throw new EntityExistsException("Work found with ID: " + work.getId());
        }
        Work addedWork = workRepo.save(work);
        return WorkResponse.builder()
                .id(addedWork.getId())
                .description(addedWork.getDescription())
                .voicePath(addedWork.getVoicePath())
                .taskId(addedWork.getTask().getId())
                .createdAt(addedWork.getCreatedAt())
                .build();
    }

    public void deleteWork(Long id) {
        if (!workRepo.existsById(id)) {
            throw new EntityNotFoundException("Work not found with ID: " + id);
        }
        workRepo.deleteById(id);
    }

    public List<WorkResponse> findTaskWorks(Long id) {
        List<Work> works = workRepo.findByTaskId(id);
        return works.stream().map(work ->
                WorkResponse.builder()
                        .id(work.getId())
                        .description(work.getDescription())
                        .voicePath(work.getVoicePath())
                        .taskId(work.getTask().getId())
                        .createdAt(work.getCreatedAt())
                        .build()
        ).collect(Collectors.toList());

    }

}
