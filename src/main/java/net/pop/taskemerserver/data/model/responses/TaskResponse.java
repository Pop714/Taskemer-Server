package net.pop.taskemerserver.data.model.responses;

import lombok.Builder;
import lombok.Data;
import net.pop.taskemerserver.data.model.enums.PriorityType;
import net.pop.taskemerserver.data.model.enums.StatusType;

import java.time.LocalDateTime;

@Data
@Builder
public class TaskResponse {
    private Long id;
    private Long userId;
    private String title;
    private String description;
    private PriorityType priority;
    private StatusType status;
    private LocalDateTime createdAt;
    private LocalDateTime endedIn;
    private LocalDateTime deadline;
}
