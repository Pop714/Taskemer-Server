package net.pop.taskemerserver.data.model.responses;

import lombok.Builder;
import lombok.Data;
import net.pop.taskemerserver.data.model.enums.AttachmentType;

@Data
@Builder
public class AttachmentResponse {
    private Long id;
    private Long wordId;
    private String attachedUrl;
    private String filePath;
    private String title;
    private AttachmentType type;
}
