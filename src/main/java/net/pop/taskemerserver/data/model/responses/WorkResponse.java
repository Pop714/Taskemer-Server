package net.pop.taskemerserver.data.model.responses;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class WorkResponse {
    private Long id;
    private Long taskId;
    private String voicePath;
    private String description;
    private LocalDateTime createdAt;
}
