package net.pop.taskemerserver.service;

import net.pop.taskemerserver.data.repos.WorkRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkService {

    private final WorkRepo workRepo;

    @Autowired
    public WorkService(WorkRepo workRepo) {
        this.workRepo = workRepo;
    }

}
