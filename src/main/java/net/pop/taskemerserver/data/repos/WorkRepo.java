package net.pop.taskemerserver.data.repos;

import net.pop.taskemerserver.data.model.Work;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkRepo extends JpaRepository<Work,Long> { }
