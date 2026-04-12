package net.pop.taskemerserver.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import net.pop.taskemerserver.data.model.entities.Work;
import net.pop.taskemerserver.data.repos.WorkRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkService {

    private final WorkRepo workRepo;

    @Autowired
    public WorkService(WorkRepo workRepo) {
        this.workRepo = workRepo;
    }

    public Work addWork(Work work) {
        if (work.getId() != null && workRepo.existsById(work.getId())) {
            throw new EntityExistsException("Work found with ID: " + work.getId());
        }
        return workRepo.save(work);
    }

    public void deleteWork(Long id) {
        if (!workRepo.existsById(id)) {
            throw new EntityNotFoundException("Work not found with ID: " + id);
        }
        workRepo.deleteById(id);
    }

    public List<Work> findTaskWorks(Long id){
        return workRepo.findByTaskId(id);
    }

}
