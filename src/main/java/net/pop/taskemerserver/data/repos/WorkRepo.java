package net.pop.taskemerserver.data.repos;

import net.pop.taskemerserver.data.model.entities.Work;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkRepo extends JpaRepository<Work,Long> {
    List<Work> findByTaskId(Long id);
}
