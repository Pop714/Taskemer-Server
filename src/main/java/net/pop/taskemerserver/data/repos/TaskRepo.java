package net.pop.taskemerserver.data.repos;

import net.pop.taskemerserver.data.model.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepo extends JpaRepository<Task,Long> {
    List<Task> findByUserId(Long userId);
}
