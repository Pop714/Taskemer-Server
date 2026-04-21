package net.pop.taskemer.app.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.pop.taskemer.app.data.model.enums.TaskStatus;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponse {
    private Long id;
    private String title;
    private String voicePath;
    private TaskStatus status;
    private LocalDateTime createdAt;
}
