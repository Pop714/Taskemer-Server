package net.pop.taskemer.app.data.dto;

import lombok.*;
import net.pop.taskemer.app.data.model.enums.ProjectPriority;
import net.pop.taskemer.app.data.model.enums.ProjectStatus;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectRequest {
    private String title;
    private String description;
    private ProjectPriority priority;
    private ProjectStatus status;
    private LocalDateTime lastUpdated;
    private LocalDateTime deadline;
}
