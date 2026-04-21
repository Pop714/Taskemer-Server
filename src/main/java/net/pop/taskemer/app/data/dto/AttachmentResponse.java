package net.pop.taskemer.app.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.pop.taskemer.app.data.model.enums.AttachmentType;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentResponse {
    private Long id;
    private String title;
    private AttachmentType type;
    private String url;
    private String filePath;
    private LocalDateTime addedIn;
}
