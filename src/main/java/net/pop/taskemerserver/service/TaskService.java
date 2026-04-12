package net.pop.taskemerserver.service;

import net.pop.taskemerserver.data.repos.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskRepo taskRepo;

    @Autowired
    TaskService(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }

}
