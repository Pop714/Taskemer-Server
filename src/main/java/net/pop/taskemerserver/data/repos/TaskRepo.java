package net.pop.taskemerserver.data.repos;

import net.pop.taskemerserver.data.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepo extends JpaRepository<Task,Long> { }
