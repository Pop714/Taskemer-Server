package net.pop.taskemer.app.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.pop.taskemer.app.data.model.enums.AttachmentType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentRequest {
    private String title;
    private AttachmentType type;
    private String url;
}
